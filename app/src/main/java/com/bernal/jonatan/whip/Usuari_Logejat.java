package com.bernal.jonatan.whip;

public class Usuari_Logejat {

    private String api_key;
    private static Usuari_Logejat instancia;

    public  static Usuari_Logejat getUsuariLogejat(String api_key) {

        if (instancia==null) {

            instancia=new Usuari_Logejat(api_key);
        }
        return instancia;
    }

    private Usuari_Logejat(String api_key){

        this.api_key=api_key;

    }

    public String getAPI_KEY() {
        return api_key;
    }

    public void setAPI_KEY(String api_key) {
        this.api_key = api_key;
    }
}
