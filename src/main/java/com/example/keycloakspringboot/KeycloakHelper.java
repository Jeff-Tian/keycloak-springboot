package com.example.keycloakspringboot;

import com.example.keycloakspringboot.models.KeycloakAccessTokenPayload;
import com.example.keycloakspringboot.models.UserPayload;
import okhttp3.*;
import org.bouncycastle.cert.ocsp.Req;

import java.io.IOException;
import java.util.Objects;

public class KeycloakHelper {
    private final OkHttpClient client;

    public KeycloakHelper(OkHttpClient httpClient) {
        this.client = httpClient;
    }

    public Response jsonRequest(String url, String payload) throws IOException {
        var mediaType = MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, payload);
        var request = new Request.Builder().url(url).method("POST", body).addHeader("Content-Type", "application/json").addHeader("Authorization", getAdminAccessToken().access_token).build();
        var res = client.newCall(request).execute();

        System.out.println(java.lang.String.format("jsonRequest response = %s", res.toString()));

        return res;
    }

    public String createUser(UserPayload user) throws IOException {
        var url = "https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users";
        var payload = java.lang.String.format("{\"firstName\":\"Sergey\",\"lastName\":\"Kargopolov\", \"email\":\"%s\", \"enabled\":\"true\", \"username\":\"%s\", \"credentials\":[{\"type\":\"password\",\"value\":\"%s\",\"temporary\":false}]}", user.getEmail(), user.getUsername(), user.getPassword());
        return this.jsonRequest(url, payload).toString();
    }

    public String assignRole(String userId) throws IOException {
        var clientId = "98ea8f07-a7f2-4607-ab56-b5208a90eaa1";
        var url = java.lang.String.format("https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users/%s/role-mappings/clients/%s", userId, clientId);
        var payload = java.lang.String.format("[{\"id\": \"bef4bf69-371b-460a-8a0c-b2943da1983b\",\"name\":\"visitor\",\"description\":\"add roles programatically\",\"composite\":false,\"clientRole\":true,\"containerId\":\"%s\"}]", clientId);

        System.out.println(java.lang.String.format("payload = %s", payload));

        return this.jsonRequest(url, payload).toString();
    }

    public KeycloakAccessTokenPayload getAdminAccessToken() throws IOException {
        var username = System.getenv("KC_ADMIN");
        var password = System.getenv("KC_PASSWORD");

        System.out.println(java.lang.String.format("username = %s; password = %s", username, password));

        var mediaType = MediaType.parse("application/x-www-form-urlencoded");
        var body = RequestBody.create(mediaType, java.lang.String.format("username=%s&password=%s&grant_type=password&client_id=admin-cli", username, password));
        var request = new Request.Builder()
                .url("https://keycloak.jiwai.win/auth/realms/master/protocol/openid-connect/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        var response = client.newCall(request).execute();

        var s = Objects.requireNonNull(response.body()).string();

        System.out.println(java.lang.String.format("token response = %s", s));

        return JsonHelper.parseFrom(s);
    }
}