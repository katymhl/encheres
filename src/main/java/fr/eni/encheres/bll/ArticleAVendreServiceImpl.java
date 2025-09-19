package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService {

    private ArticleAVendreDAO articleAVendreDAO;

    public ArticleAVendreServiceImpl(ArticleAVendreDAO articleAVendreDAO) {
        this.articleAVendreDAO = articleAVendreDAO;
    }

    @Override
    public List<ArticleAVendre> findAll() {
        return articleAVendreDAO.findAll();
    }

    @Override
    public List<ArticleAVendre> findActiveEnchere() {
        return articleAVendreDAO.findActiveEnchere();
    }

    public List<ArticleAVendre> filtrerArticles(String search, Integer categorie) {
        return articleAVendreDAO.filtrerArticles(search, categorie);

    }

    @Override
    public ArticleAVendre findById(int no_article) {
        return articleAVendreDAO.read(no_article);
    }
@Override
    public List<ArticleAVendre> getMesVentesEnCours(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesEnCours(pseudo, search, categorie);
    }

@Override
    public List<ArticleAVendre> getMesVentesNonDebutees(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesNonDebutees(pseudo, search, categorie);
    }

    public List<ArticleAVendre> getMesVentesTerminees(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesTerminees(pseudo, search, categorie);
    }



}