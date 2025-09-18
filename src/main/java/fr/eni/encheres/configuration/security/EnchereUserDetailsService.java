package fr.eni.encheres.configuration.security;

import fr.eni.encheres.bo.Role;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Utilisateur utilisateur = utilisateurOpt.get();
        System.out.println("Email = " + utilisateurOpt.get().getEmail());
        System.out.println("utilisateurOpt MDP = " + utilisateurOpt.get().getMotDePasse());
        System.out.println("Password encoder : " + passwordEncoder.encode("Pa$$w0rd"));

        Set<String> roles = new HashSet<>();
        if (utilisateur.isAdmin()){
            roles.add("ADMIN");
        }
        roles.add("USER");

        UserDetails user = User.withUsername(username)
                .password(utilisateurOpt.get().getMotDePasse())
                .roles(roles.toArray(new String[0]))
                .build();

        return user;
    }
}
