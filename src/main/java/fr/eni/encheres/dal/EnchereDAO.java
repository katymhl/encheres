package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {

    Enchere read(String id_utilisateur,int no_article,int montant_enchere);
    List<Enchere> findAll();

    void create(Enchere enchere) ;

    void update(Enchere enchere);
    void delete(String id_utilisateur,int no_article,int montant_enchere);

}
