package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Adresse;

import java.util.List;

public interface AdresseService {
     int getOrCreateAdresse(String rue, String codePostal, String ville);
     List<Adresse> findByall();
}
