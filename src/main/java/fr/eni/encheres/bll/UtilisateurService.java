package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

import java.util.Optional;

public interface UtilisateurService {
    Utilisateur findById(String pseudo);
   void  creat(Utilisateur utilisateur);
    Utilisateur findByUserEmail(String email);
    void update(Utilisateur utilisateur);

}
