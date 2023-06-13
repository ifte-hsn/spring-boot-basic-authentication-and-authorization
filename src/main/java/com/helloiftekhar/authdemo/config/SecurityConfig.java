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

    UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .authorizeHttpRequests(
                        auth  -> auth.requestMatchers("/login",
                                        "/register",
                                        "/images/*",
                                        "/css/*",
                                        "/js/*").permitAll()
                                .requestMatchers("admin").hasAuthority("admin")
                                .anyRequest().authenticated()
                ).exceptionHandling(exp -> exp.accessDeniedPage("/accessDenied"))
                .userDetailsService(userService)
                .logout(logout->logout.logoutSuccessUrl("/login?logout=true")
                        .deleteCookies("JSESSIONID"))
                .rememberMe(rem->rem.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400));

        return http.build();
    }
}
