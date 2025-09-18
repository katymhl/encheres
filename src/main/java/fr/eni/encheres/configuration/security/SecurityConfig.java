package fr.eni.encheres.configuration.security;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {

            auth

                    // permettre à tout le monde d'accéder à l'URL racine
                    .requestMatchers(HttpMethod.GET, "/inscription").permitAll()
                    .requestMatchers(HttpMethod.POST, "/inscription").permitAll()
                    .requestMatchers(HttpMethod.GET, "/encheres").permitAll()
                    .requestMatchers(HttpMethod.POST, "/encheres").permitAll()
                    .requestMatchers(HttpMethod.GET, "/vendre").authenticated()
                    .requestMatchers(HttpMethod.POST, "/vendre").authenticated()
                    .requestMatchers(HttpMethod.GET, "/monProfil").authenticated()
                    .requestMatchers(HttpMethod.POST, "/monProfil").authenticated()
                    .requestMatchers(HttpMethod.GET, "/monProfil/update").authenticated()
                    .requestMatchers(HttpMethod.POST, "/monProfil/update").authenticated()
                    .requestMatchers(HttpMethod.GET, "/profil").authenticated()
                    .requestMatchers(HttpMethod.POST, "/profil").authenticated()
                    .requestMatchers(HttpMethod.GET, "/logout-form").authenticated()
                    .requestMatchers(HttpMethod.POST, "/logout-form").authenticated()

                    .requestMatchers("/admin/**").hasRole("ADMIN")

                    // Permettre à tous les utilisateurs d'afficher correctement les images, la css et le js
                    .requestMatchers("/js/*").permitAll()
                    .requestMatchers("/css/*").permitAll()
                    .requestMatchers("/images/*").permitAll()

                    .requestMatchers("/**").permitAll()

                    // Toutes autres url et méthodes HTTP ne sont pas permises
                    .anyRequest().denyAll();
        });

        // Customiser le formulaire de login
        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/");
        });

        // Déconnexion
        http.logout(logout ->
                logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .permitAll()
        );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
