package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieService {

    //Categorie read(int no_categorie);
    Categorie read(Long no_categorie);

    List<Categorie> getAllCategories();

    Categorie consulterCategorieParId(Long id);



}
