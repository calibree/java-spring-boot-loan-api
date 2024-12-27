package com.example.loanapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/customers/**","/loans/**", "/installments/**").hasAnyRole("ADMIN", "CUSTOMER")
                        //.requestMatchers("/admin/**").hasRole("ADMIN") //role based access control
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Use a custom login page (optional)
                        .permitAll() // Allow everyone to access the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define a logout URL (optional)
                        .permitAll() // Allow everyone to access the logout page
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .httpBasic(Customizer.withDefaults()); // Enable basic authentication
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        //users which will reach endpoints:
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails customer = User.withUsername("customer")
                .password(passwordEncoder().encode("123"))
                .roles("CUSTOMER")
                .build();
        UserDetails customer1 = User.withUsername("alice")
                .password(passwordEncoder().encode("alice"))
                .roles("CUSTOMER")
                .build();
        UserDetails customer2 = User.withUsername("john")
                .password(passwordEncoder().encode("john"))
                .roles("CUSTOMER")
                .build();
        UserDetails customer3 = User.withUsername("bob")
                .password(passwordEncoder().encode("bob"))
                .roles("CUSTOMER")
                .build();
        return new InMemoryUserDetailsManager(admin, customer, customer1, customer2, customer3);
    }
}
