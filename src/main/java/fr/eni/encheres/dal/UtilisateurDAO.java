package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {

    Utilisateur read(String pseudo);

    Optional<Utilisateur> readPseudo(String pseudoUtilisateur);

    Optional<Utilisateur> findByEmail(String emailUtilisateur);

    Utilisateur findByemail(String emailUtilisateur);

    void create(Utilisateur utilisateur) ;

    void update(Utilisateur utilisateur);

    void updatePWD(String mot_de_passe, String pseudo);

    void delete(String pseudo);
}
