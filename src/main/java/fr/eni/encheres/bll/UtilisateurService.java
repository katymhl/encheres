package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
    Utilisateur findById(String pseudo);
}
