package com.example.keycloakspringboot;

import com.example.keycloakspringboot.models.AssigningRolePayload;
import com.example.keycloakspringboot.models.UserPayload;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.io.IOException;

@Controller
@SpringBootApplication
public class DemoApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello Keycloak!";
    }

    @RequestMapping(value = "/visitor", method = RequestMethod.GET)
    @ResponseBody
    public String getVisitorPath() {
        return "嗨，你好！当你看到这些文字，说明你成功登录了！";
    }

    @PostMapping(value = "/create-user", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String createVisitor(@RequestBody UserPayload newUser) throws IOException {
        return "visitor created result = " + new KeycloakHelper(new OkHttpClient().newBuilder()
                .build()).createUser(newUser);
    }

    @PostMapping(value = "/assign-role", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String assignRole(@RequestBody AssigningRolePayload payload) throws IOException {
        return java.lang.String.format("assigned %s = %s", payload.getUserId(),
                new KeycloakHelper(new OkHttpClient().newBuilder()
                        .build()).assignRole(payload.getUserId()));
    }

    @PostMapping(value = "/assign-realm-role", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String assignRealmRole(@RequestBody AssigningRolePayload payload) throws IOException {
        return java.lang.String.format("assigned %s = %s", payload.getUserId(),
                new KeycloakHelper(new OkHttpClient().newBuilder().build()).assignRealmRole(payload.getUserId()));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
