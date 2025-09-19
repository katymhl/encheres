package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface AdresseService {
     int getOrCreateAdresse(String rue, String codePostal, String ville);
     List<Adresse> findByall();
     Adresse getAdresseByPseudo(String pseudo);
}
