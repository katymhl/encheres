package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.Optional;

public interface UtilisateurService {

    Utilisateur findById(String pseudo);

    void  creat(Utilisateur utilisateur);

    void  updatePWD(String mot_de_passe, String pseudo);

    Utilisateur findByUserEmail(String email);

    void update(Utilisateur utilisateur);

}
