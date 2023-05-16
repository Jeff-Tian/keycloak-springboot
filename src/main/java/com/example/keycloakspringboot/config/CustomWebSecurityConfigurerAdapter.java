package com.example.keycloakspringboot.config;

import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;

@Configuration
public class CustomWebSecurityConfigurerAdapter extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected org.springframework.security.web.authentication.session.SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/api/**").authenticated().anyRequest().permitAll();
    }
}
