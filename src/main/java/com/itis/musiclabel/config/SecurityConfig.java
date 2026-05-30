package com.itis.musiclabel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/logout",
                                "/css/**",
                                "/js/**",
                                "/uploads/**",
                                "/assets/**",
                                "/favicon.ico",
                                "/",
                                "/error",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(
                                "/artist/**",
                                "/submission/create",
                                "/submission/my"
                        ).hasAuthority("ARTIST")
                        .requestMatchers(
                                "/label/**",
                                "/service/manage",
                                "/service/create",
                                "/service/edit/**",
                                "/service/delete/**",
                                "/service/search",
                                "/submission/pending",
                                "/submission/history",
                                "/song/review/**"
                        ).hasAuthority("LABEL")
                        .requestMatchers(
                                "/",
                                "/profile",
                                "/profile/**",
                                "/user/**",
                                "/service/browse",
                                "/dashboard"
                        ).authenticated()
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}