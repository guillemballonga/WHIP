package com.bernal.jonatan.whip;

public class UserLoggedIn {

    private String api_key;
    private String correo_user;
    private static UserLoggedIn instancia;

    public  static UserLoggedIn getUsuariLogejat(String api_key, String correo) {

        if (instancia==null) {

            instancia=new UserLoggedIn(api_key,correo);
        }
        return instancia;
    }

    private UserLoggedIn(String api_key, String correo){

        this.api_key=api_key;
        this.correo_user=correo;

    }

    public String getAPI_KEY() {
        return api_key;
    }

    public void setAPI_KEY(String api_key) {
        this.api_key = api_key;
    }

    public String getCorreo_user() {
        return correo_user;
    }

    public void setCorreo_user(String correo_user) {
        this.correo_user = correo_user;
    }
}
