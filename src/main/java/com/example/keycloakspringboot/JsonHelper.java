package com.example.keycloakspringboot;

import com.example.keycloakspringboot.models.KeycloakAccessTokenPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonHelper {
    private static final Gson gson =
            new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setPrettyPrinting().create();

    public static String stringify(Object anything) {
        return gson.toJson(anything);
    }

    public static String stringify(Object anything, Type type) {
        return gson.toJson(anything, type);
    }

    public static Object parse(String s) {
        return gson.fromJson(s, Object.class);
    }

    public static KeycloakAccessTokenPayload parseFrom(String s) {
        return gson.fromJson(s, KeycloakAccessTokenPayload.class);
    }
}