
package com.mycompany.lettorecns.lettoreCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.ATR;


/*
 * Copyright 2020 france.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Carta {
   
    private String err; 
    private String path;
    private CardTerminal terminal;
    private Card card;
    private CardChannel channel;
    private ResponseAPDU answer;
    private ATR atr;
    public final byte[] READ_BINARY_APDU = {(byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x00}; 
    public final byte[] DATI_PERS = {(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x00, (byte) 0x04, (byte) 0x11, (byte) 0x00, (byte) 0x11, (byte) 0x02, (byte) 0x00};
    public final byte[] DATI_PERS_AGG = {(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x00, (byte) 0x06, (byte) 0xd0, (byte) 0x00, (byte) 0xd1, (byte) 0x00,(byte) 0xd1,(byte) 0x01, (byte)0xFF};
    
    public Carta(){}

    public String getErr()  {    return err;    }//errori

    public String getPath() {   return path;    }//nome porta usb
    
    /**
     * metodo per inizializzazione della scheda CNS opzionale non usato in swing
     * @return 
     */
    public String inizializzaDriver(){
        String cheOS = System.getProperty("os.name").toLowerCase();
        String tmp=""; 
        if(cheOS.equalsIgnoreCase("linux"))
            try {
                String comm[] = { "find", "/usr", "/lib", "-name", "libpcsclite.so.1" };
                String line;
                Process p = Runtime.getRuntime().exec(comm);
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = reader.readLine()) != null && !line.equals(""))
                    if (line.contains("libpcsclite.so.1")) {
                        System.setProperty("sun.security.smartcardio.library",line);
                        break;
                    }
                tmp="driver ok";
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                tmp="driver non trovato";
                Logger.getLogger(Carta.class.getName()).log(Level.SEVERE, null, e);
            }    
        return tmp;
    }
    
    /**
     * metodo di controllo se il lettore di schede è collegato
     * @return 
     * @throws javax.smartcardio.CardException 
     */
    public String iniCardReader() throws CardException {
        TerminalFactory factory= TerminalFactory.getDefault();
        List<CardTerminal> terminals=null;
        try {
            terminals = factory.terminals().list();
        } catch (CardException ex) {
            Logger.getLogger(Carta.class.getName()).log(Level.SEVERE, null, ex);
        }
        terminal = terminals.get(0);// Use the first terminal
        card = terminal.connect("*");
        channel = card.getBasicChannel(); 
        atr = card.getATR();  //GET ATR
        byte[] ATR = atr.getBytes();      
        return bytesToHex(ATR);
    }
    
    /**
     * metodo per la lettura della stringa estratta dalla CNS
     * @param selezione
     * @param sel true dati personali altrimenti dati aggiuntivi
     * @return 
     * @throws javax.smartcardio.CardException 
     */
    public String leggi(String selezione,boolean sel) throws CardException {
        String tmp="";
        if (sel){
            int num=0,val=0;      
            for (int i=0;i<9;i++){
                num+=val;
                switch (i){
                    case 0:val=8;num+=2;break;
                    case 1:val=8;num+=2;break;
                    case 2:
                    case 3: String ch="";
                            do{
                                ch+=selezione.charAt(num)+"";
                                num++;
                            }while(ch.matches("^\\d{1,}$"));
                            ch=ch.substring(0, ch.length()-1);
                            num--;
                            val=Integer.parseInt(ch);
                            break;
                    case 4:val=8;num+=2;break;
                    case 5:val=1;num+=2;break;
                    case 6:val=16;num+=4;break;
                    case 7:val=4;num+=4;break;
                    case 8:val=4;num+=6;break;
                }
                tmp+=selezione.substring(num,num+val)+",";
            }
        }else{
            tmp=selezione;tmp=tmp.replaceAll("[^A-Z0-9\\s]", "");
         tmp=tmp.substring(0,38);
        }
    return tmp;
    }
    
    /**
     * metodo per il conteggio dei caratteri
     * @param str
     * @param ini
     * @return 
     */
    public int conta(String str, int ini){
        int conta=ini;String chk="";
        do{
            chk=str.charAt(conta)+"";
            conta++;
        }while(Pattern.matches("[0-9]",chk));
        conta=Integer.parseInt(str.substring(ini,conta));
        return conta;
    }
    
    /**
     * metodo per la conversione da array di byte a Stringa esadecimale
     * @param bytes
     * @return 
     */
    public String bytesToHex (byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte ab : bytes) 
            sb.append(String.format("%02x", ab));
        return sb.toString();
    }
       
    /**
     * metodo che consente di estrarre o l'esito della trasmissione e/o i dati personali
     * @param dati
     * @param read
     * @param dp 
     * @return
     * @throws CardException 
     */
    public String richiedi(byte[] dati,byte[] read,int dp) throws CardException { 
        answer = channel.transmit(new CommandAPDU(dati));// Send Select command
        answer = channel.transmit(new CommandAPDU(read));// Send test command
        byte r[] = answer.getData();
        String test = "";
        for (int i = dp; i < r.length; i++) 
            test+= (char)r[i];
        return test;
    }
    /**
     * disconnette la card
     * @throws CardException 
     */
    
    public void off() throws CardException{
        card.disconnect(false); 
    }
}