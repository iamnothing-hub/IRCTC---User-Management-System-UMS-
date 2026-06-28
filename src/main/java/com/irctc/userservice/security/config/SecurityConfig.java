package com.irctc.userservice.security.config;


import com.irctc.userservice.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration    //todo: Why @Configuration? -> Because this class creates Spring Beans. without @Bean SecurityFilterChain
@EnableWebSecurity  //todo: Why @EnableWebSecurity? -> "Enable Spring Security and use my custom configuration." @EnableWebSecurity is often optional
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Argon2PasswordEncoder(
                16,
                32,
                1,
                65536,
                3
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * HttpSecurity is a Builder Pattern so it is no need to create Object new HttpSecurity()
     * It already knows:
     * registered filters
     * shared security objects
     * default configuration
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        HttpSecurity security = httpSecurity.authorizeHttpRequests(auth ->
                auth.requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return security.build();
    }
}
