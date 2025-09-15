package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    Utilisateur read(String pseudo);

    List<Utilisateur> findAll();

    List<Utilisateur> findByemail(String emailUtilisateur);

    void insertUtilisateur(String pseudo, String nomUtilisateur, String prenomUtilisateur,String email, Adresse adresse,String telephone , String passwordUtilisateur,int credit,boolean admin );

    boolean validateListOfUtilisateurds(List<Utilisateur> lstUlisateur);
}
