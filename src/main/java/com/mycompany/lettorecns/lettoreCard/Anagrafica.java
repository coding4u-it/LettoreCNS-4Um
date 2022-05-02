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
package com.mycompany.lettorecns.lettoreCard;

/**
 *
 * @author france
 */
public class Anagrafica {

    private String dataRil;
    private String dataSca;
    private String nome;
    private String cognome;
    private String datanascita;
    private String cf;
    private String sesso;
    private String comNasc;
    private String comRes;
    private String indirizzo;

    public Anagrafica(){}
    
    public Anagrafica(String[]info){
        this.dataRil=info[0];
        this.dataSca=info[1];
        this.cognome=info[2]; 
        this.nome=info[3]; 
        this.datanascita=info[4];
        this.sesso=info[5];
        this.cf=info[6];
        this.comNasc=info[7]; 
        this.comRes=info[8];
    }

    /**
     * @return the dataRil
     */
    public String getDataRil() {
        return dataRil;
    }

    /**
     * @param dataRil the dataRil to set
     */
    public void setDataRil(String dataRil) {
        this.dataRil = dataRil;
    }

    /**
     * @return the dataSca
     */
    public String getDataSca() {
        return dataSca;
    }

    /**
     * @param dataSca the dataSca to set
     */
    public void setDataSca(String dataSca) {
        this.dataSca = dataSca;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the datanascita
     */
    public String getDatanascita() {
        return datanascita;
    }

    /**
     * @param datanascita the datanascita to set
     */
    public void setDatanascita(String datanascita) {
        this.datanascita = datanascita;
    }

    /**
     * @return the cf
     */
    public String getCf() {
        return cf;
    }

    /**
     * @param cf the cf to set
     */
    public void setCf(String cf) {
        this.cf = cf;
    }

    /**
     * @return the sesso
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * @param sesso the sesso to set
     */
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    /**
     * @return the comNasc
     */
    public String getComNasc() {
        return comNasc;
    }

    /**
     * @param comNasc the comNasc to set
     */
    public void setComNasc(String comNasc) {
        this.comNasc = comNasc;
    }

    /**
     * @return the comRes
     */
    public String getComRes() {
        return comRes;
    }

    /**
     * @param comRes the comRes to set
     */
    public void setComRes(String comRes) {
        this.comRes = comRes;
    }

    /**
     * @return the indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * @param indirizzo the indirizzo to set
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
     
   
    
}