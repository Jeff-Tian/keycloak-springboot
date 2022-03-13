package com.example.keycloakspringboot;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class KeycloakHelperTest {
    
    void getAdminAccessToken() throws IOException {
        var sut = new KeycloakHelper();
        var res = sut.getAdminAccessToken();
        assertEquals("1234", res.access_token);
    }
}