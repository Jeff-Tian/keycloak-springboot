package com.example.keycloakspringboot.models;

public class KeycloakUserModel {
    public String id;
    public long createdTimestamp;
    public String username;
    public boolean enabled;
    public boolean totp;
    public boolean emailVerified;
    public String firstName;
    public String lastName;
    public String email;
    public int notBefore;
}
