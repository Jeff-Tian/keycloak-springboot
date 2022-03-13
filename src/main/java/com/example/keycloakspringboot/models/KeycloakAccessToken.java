package com.example.keycloakspringboot.models;

public class KeycloakAccessToken {
    public String access_token;
    public int expires_in;
    public int refresh_expires_in;
    public String refresh_token;
    public String token_type;

    public String session_state;
    public String scope;
}
