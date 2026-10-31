package com.example.projet1.config;

import com.example.projet1.Service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_admin"));


                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            } else {
                                response.sendRedirect("/employee/dashboard");
                            }
                        })
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/webjars/**", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/employee/**").hasRole("employee")
                        .requestMatchers("/article/**").hasAnyRole("admin", "employee")
                        .requestMatchers("/magasin/**").hasAnyRole("admin", "employee")
                        .requestMatchers("/user/**").hasRole("admin")
                        .requestMatchers(" gestionmouvement").hasRole("admin")
                        .requestMatchers(" historiquemouvement").hasRole("admin")
                        .requestMatchers("/entreeStock").hasAnyRole("admin", "employee")
                        .requestMatchers("/sortiestock").hasAnyRole("admin", "employee")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/pasautorise")
                )
                .userDetailsService(userDetailsServiceImpl);



        return httpSecurity.build();
    }
}