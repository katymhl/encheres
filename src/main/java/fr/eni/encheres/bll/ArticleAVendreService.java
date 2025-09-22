package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreService {


    List<ArticleAVendre> findAll();
    List<ArticleAVendre> findActiveEnchere();
    List<ArticleAVendre> filtrerArticles(String search, Integer categorie);
    ArticleAVendre findById(int no_article);

    List<ArticleAVendre> getMesVentesEnCours(String pseudo, String search, Integer categorie);
     List<ArticleAVendre> getMesVentesNonDebutees(String pseudo, String search, Integer categorie);
     List<ArticleAVendre> getMesVentesTerminees(String pseudo, String search, Integer categorie);
    void save(ArticleAVendre article);
    void update(ArticleAVendre article);
}
