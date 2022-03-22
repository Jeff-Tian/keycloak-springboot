package com.example.keycloakspringboot;

import okhttp3.*;
import okhttp3.mock.MockInterceptor;
import okio.Timeout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class KeycloakHelperTest {

    OkHttpClient httpClient;

    @BeforeEach
    void init() throws IOException {
        var interceptor = new MockInterceptor();

        interceptor.addRule()
                .get().or().post().or().put()
                .url("https://keycloak.jiwai.win/auth/realms/master/protocol/openid-connect/token")
                .respond(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "{\"access_token\":\"1234\"}"));

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Test
    void getAdminAccessToken() throws IOException {
        var sut = new KeycloakHelper(httpClient);
        var res = sut.getAdminAccessToken();
        assertEquals("1234", res.access_token);
    }
}