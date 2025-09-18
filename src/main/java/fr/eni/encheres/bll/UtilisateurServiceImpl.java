package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UtilisateurDAO utilisateurDAO;

            public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {

                this.utilisateurDAO =  utilisateurDAO;
            };

    @Override
    public Utilisateur findById(String pseudo) {
//        System.out.println(utilisateurDAO.read("coach_admin"));
        return utilisateurDAO.read(pseudo);
    }

    @Override
    public void creat(Utilisateur utilisateur) {
        String motDePasseEncode = passwordEncoder.encode(utilisateur.getMot_de_passe());
        utilisateur.setMot_de_passe(motDePasseEncode);

        utilisateurDAO.create(utilisateur);
    }

    @Override
    public Utilisateur findByUserEmail(String email) {

        return utilisateurDAO.findByemail(email);
    }

}
