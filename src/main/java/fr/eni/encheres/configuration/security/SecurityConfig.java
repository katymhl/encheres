package fr.eni.encheres.configuration.security;

import org.apache.commons.logging.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    protected final Log logger = LogFactory.getLog(getClass());

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("toto")
                .password("tata")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }
}
