package com.bernal.jonatan.whip.Views;

public class UserLoggedIn {

    private static UserLoggedIn instancia;
    private String api_key;
    private String correo_user;


    private String token;

    private UserLoggedIn(String api_key, String correo, String token) {

        this.api_key = api_key;
        this.correo_user = correo;
        this.token = token;

    }

    public static UserLoggedIn getUsuariLogejat(String api_key, String correo, String token) {

        if (instancia == null) {
            instancia = new UserLoggedIn(api_key, correo, token);

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
