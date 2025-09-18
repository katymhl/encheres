package fr.eni.encheres.configuration.security;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dal.UtilisateurDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnchereUserDetailsService implements UserDetailsService {

    private UtilisateurDAOImpl utilisateurDAOImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EnchereUserDetailsService(UtilisateurDAOImpl utilisateurDAOImpl) {
        this.utilisateurDAOImpl = utilisateurDAOImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // On cherche l'utilisateur en BDD
        Optional<Utilisateur> utilisateurOpt = utilisateurDAOImpl.findByemail(username);
        System.out.println("Email = " + username);

        if (utilisateurOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        System.out.println("Email = " + utilisateurOpt.get().getEmail());
        System.out.println("utilisateurOpt MDP = " + utilisateurOpt.get().getMotDePasse());
        System.out.println("Password encoder : " + passwordEncoder.encode("Pa$$w0rd"));
        UserDetails user = User.withUsername(username)
                .password(utilisateurOpt.get().getMotDePasse())
                .build();

        return user;
    }
}
