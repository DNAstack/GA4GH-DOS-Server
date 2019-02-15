package com.dnastack.dos.server.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableConfigurationProperties
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Data
    public static class User {
        private String username;
        private String password;
        private List<String> roles = new ArrayList<>();
    }

    @ConfigurationProperties(prefix = "dos-server.admin")
    @Bean
    User adminUser() {
        return new User();
    }

    @ConfigurationProperties(prefix = "dos-server.user")
    @Bean
    User regularUser() {
        return new User();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic().realmName("dos-server").and()
                .authorizeRequests()
                .antMatchers(GET, "/actuator/info").permitAll()
                .antMatchers(GET, "/").permitAll()
                .antMatchers(GET, "/**").hasAnyRole("admin", "user")
                .antMatchers(POST).hasRole("admin");
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth.inMemoryAuthentication();

        Stream.of(adminUser(), regularUser()).forEach(user -> {
            String username = user.username;
            String password = user.password;
            String[] roles = user.getRoles().toArray(new String[]{});
            log.info("Configuring user {} with roles {}", username, roles);
            requireNonNull(username);
            requireNonNull(password);
            configurer
                    .withUser(username)
                    .password(password)
                    .roles(roles);
        });
    }
}