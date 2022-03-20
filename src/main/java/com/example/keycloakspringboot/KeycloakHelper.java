package com.example.keycloakspringboot;

import com.example.keycloakspringboot.models.KeycloakAccessTokenPayload;
import com.example.keycloakspringboot.models.UserPayload;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class KeycloakHelper {
    private final OkHttpClient client;

    public KeycloakHelper() {
        this.client = new OkHttpClient().newBuilder()
                .build();
    }

    public String createUser(UserPayload user) throws IOException {
        var accessToken = this.getAdminAccessToken().access_token;

        var mediaType = MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, "{\"firstName\":\"Sergey\",\"lastName\":\"Kargopolov\", \"email\":\"" + user.getEmail() + "\", \"enabled\":\"true\", \"username\":\"" + user.getUsername() + "\", \"credentials\":[{\"type\":\"password\",\"value\":\"" + user.getPassword() + "\",\"temporary\":false}]}");
        var request = new Request.Builder()
                .url("https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        var createUserResponse = client.newCall(request).execute();
        return createUserResponse.toString();
    }

    public String assignRole(String userId) {
        var accessToken = this.getAdminAccessToken().access_token;
        var mediaType = MediaType.parse("application/json");

        var clientId = "98ea8f07-a7f2-4607-ab56-b5208a90eaa1";

        var body = RequestBody.create(mediaType, "[{\"id\": \"bef4bf69-371b-460a-8a0c-b2943da1983b\"," +
                "\"name\":\"visitor\",\"description\":\"add roles programatically\",\"composite\":false," +
                "\"clientRole\":true,\"containerId\":\"" + clientId + "\"}]");

        var request =
                new Request.Buidler().url("https://keycloak.jiwai.win/auth/admin/realms/UniHeart/users/" + userId +
                                "/role-mappings/clients/" + clientId).method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();
        return (client.newCall(request).execute()).toString();
    }

    public KeycloakAccessTokenPayload getAdminAccessToken() throws IOException {
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