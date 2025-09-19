package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreService {

    List<ArticleAVendre> findAll();
    List<ArticleAVendre> findActiveEnchere();
    List<ArticleAVendre> filtrerArticles(String search, Integer categorie);
    ArticleAVendre findById(int no_article);
}
