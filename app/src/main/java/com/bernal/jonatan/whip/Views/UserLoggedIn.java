package com.bernal.jonatan.whip.Views;

public class UserLoggedIn {

    private static UserLoggedIn instancia;
    private String api_key;
    private String correo_user;

    private UserLoggedIn(String api_key, String correo) {

        this.api_key = api_key;
        this.correo_user = correo;

    }

    public static UserLoggedIn getUsuariLogejat(String api_key, String correo) {

        if (instancia == null) {

            instancia = new UserLoggedIn(api_key, correo);
        }
        return instancia;
    }

    public String getAPI_KEY() {
        return api_key;
    }

    void setAPI_KEY(String api_key) {
        this.api_key = api_key;
    }

    public String getCorreo_user() {
        return correo_user;
    }

    void setCorreo_user(String correo_user) {
        this.correo_user = correo_user;
    }
}
