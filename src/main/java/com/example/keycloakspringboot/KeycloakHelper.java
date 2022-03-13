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

    public String

    createUser(User user) throws IOException {
        var accessToken = this.getAdminAccessToken().access_token;
        System.out.println("Access Token = " + accessToken);

        var mediaType = MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, "{\"firstName\":\"Sergey\",\"lastName\":\"Kargopolov\", \"email\":\"" + user.getEmail() + "\", \"enabled\":\"true\", \"username\":\"" + user.getUsername() + "\", \"credentials\":[{\"type\":\"password\",\"value\":\"" + user.getPassword() + "\",\"temporary\":false}]}");
        var request = new Request.Builder()
                .url("https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        var response = client.newCall(request).execute();
        return response.toString();
    }

    public KeycloakAccessToken getAdminAccessToken() throws IOException {
        var username = System.getenv("KC_ADMIN");
        var password = System.getenv("KC_PASSWORD");

        System.out.println("username = " + username + "; password = " + password);

        var mediaType = MediaType.parse("application/x-www-form-urlencoded");
        var body = RequestBody.create(mediaType, "username=" + username + "&password=" + password + "&grant_type=password&client_id=admin-cli");
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