package com.helloiftekhar.authdemo.config;


import com.helloiftekhar.authdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form.loginPage("/login").permitAll())
                .authorizeHttpRequests(
                        (auth)  -> auth.requestMatchers("/register", "/images/*","/css/*", "/js/*").permitAll()
                                .anyRequest().authenticated()
                ).userDetailsService(userService);

        return http.build();
    }
}
