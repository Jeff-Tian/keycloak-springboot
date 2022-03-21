package com.example.keycloakspringboot;

import okhttp3.*;
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


class MockCall implements Call {

    @java.lang.Override
    public Request request() {
        return null;
    }

    @java.lang.Override
    public Response execute() throws IOException {
        return new Response.Builder().code(200).message("success").build();
    }

    @java.lang.Override
    public void enqueue(Callback responseCallback) {

    }

    @java.lang.Override
    public void cancel() {

    }

    @java.lang.Override
    public boolean isExecuted() {
        return false;
    }

    @java.lang.Override
    public boolean isCanceled() {
        return false;
    }

    @java.lang.Override
    public Timeout timeout() {
        return null;
    }

    @java.lang.Override
    public Call clone() {
        return null;
    }
}

class MockHttpClient extends OkHttpClient {
    @java.lang.Override
    public Call newCall(Request request) {
        return new MockCall();
    }
}


class KeycloakHelperTest {

    OkHttpClient httpClient;

    @BeforeEach
    void init() throws IOException {
//        var interceptor = new MockInterceptor();
//
//        interceptor.addRule()
//                .get().or().post().or().put()
//                .url("https://keycloak.jiwai.win/auth/realms/master/protocol/openid-connect/token")
//                .respond(200)
//                .body("");
//
//        httpClient = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .build();
    }


    void getAdminAccessToken() throws IOException {
        var httpClient = new MockHttpClient();

        var sut = new KeycloakHelper(httpClient);
        var res = sut.getAdminAccessToken();
        assertEquals("1234", res.access_token);
    }
}