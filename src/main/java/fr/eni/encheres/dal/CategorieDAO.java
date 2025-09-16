package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {

    Categorie read(int no_categorie);
    List<Categorie> findAll();

    void create(Categorie categorie) ;

    void update(Categorie categorie);
    void delete(int no_categorie);
}
