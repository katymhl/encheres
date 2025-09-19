package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreDAO {

    ArticleAVendre read(int no_article);
    List<ArticleAVendre> findAll();

    void create(ArticleAVendre articleAVendre) ;

    void update(ArticleAVendre articleAVendre);
    void delete(int no_article);
    List<ArticleAVendre> findActiveEnchere();
     List<ArticleAVendre> filtrerArticles(String search, Integer categorie);
     List<ArticleAVendre> findMesVentesEnCours(String pseudo, String search, Integer categorie);
     List<ArticleAVendre> findMesVentesNonDebutees(String pseudo, String search, Integer categorie);
     List<ArticleAVendre> findMesVentesTerminees(String pseudo, String search, Integer categorie);
}
