package com.example.keycloakspringboot;

import com.example.keycloakspringboot.models.KeycloakAccessToken;
import com.example.keycloakspringboot.models.User;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class KeycloakHelper {
    private final OkHttpClient client;

    public KeycloakHelper() {
        this.client = new OkHttpClient().newBuilder()
                .build();
    }

    public Object

    createUser(User user) throws IOException {
        var accessToken = this.getAdminAccessToken().access_token;

        var mediaType = MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, "{\"firstName\":\"Sergey\",\"lastName\":\"Kargopolov\", \"enabled\":\"true\", \"username\":\"" + user.getUsername() + "\", \"credentials\":[{\"type\":\"password\",\"value\":\"" + user.getPassword() + "\",\"temporary\":false}]}");
        var request = new Request.Builder()
                .url("https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        var response = client.newCall(request).execute();
        return JsonHelper.parse(response.toString());
    }

    public KeycloakAccessToken getAdminAccessToken() throws IOException {
        var mediaType = MediaType.parse("application/x-www-form-urlencoded");
        var body = RequestBody.create(mediaType, "username=" + System.getenv("KC_ADMIN") + "&password=" + System.getenv("KC_PASSWORD") + "&grant_type=password&client_id=admin-cli");
        var request = new Request.Builder()
                .url("https://keycloak.jiwai.win/auth/realms/master/protocol/openid-connect/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        var response = client.newCall(request).execute();

        var s = Objects.requireNonNull(response.body()).string();

        return JsonHelper.parseFrom(s);
    }
}