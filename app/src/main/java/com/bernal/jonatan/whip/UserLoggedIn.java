package com.bernal.jonatan.whip;

public class UserLoggedIn {

    private String api_key;
    private static UserLoggedIn instancia;

    public  static UserLoggedIn getUsuariLogejat(String api_key) {

        if (instancia==null) {

            instancia=new UserLoggedIn(api_key);
        }
        return instancia;
    }

    private UserLoggedIn(String api_key){

        this.api_key=api_key;

    }

    public String getAPI_KEY() {
        return api_key;
    }

    public void setAPI_KEY(String api_key) {
        this.api_key = api_key;
    }
}
