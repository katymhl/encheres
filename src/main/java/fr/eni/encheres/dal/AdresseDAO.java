package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface AdresseDAO {

    Adresse read(int no_adresse);
    List<Adresse> findAll();

    void create(Adresse adresse) ;

    void update(Adresse adresse);
    void delete(int no_adresse);

}
