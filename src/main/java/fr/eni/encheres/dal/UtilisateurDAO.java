package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {

    Utilisateur read(String pseudo);

    //List<Utilisateur> findAll();

    Optional<Utilisateur> findByemail(String emailUtilisateur);


     void create(Utilisateur utilisateur) ;

     void update(Utilisateur utilisateur);
     void delete(String pseudo);
}
