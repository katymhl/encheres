package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    //Cron qui tourne toutes les minutes
    @Scheduled(fixedRate = 3600000) // toutes les heures
    public void updateEncheresStatus() {
        articleAVendreDAO.cloturerEncheresExpirees();
    }

    //Vérification au moment d’un accès
    public ArticleAVendre read(int id) {
        ArticleAVendre article = articleAVendreDAO.read(id);

        // Vérification si l'enchère est EN_COURS mais la date de fin est dépassée
        if(article.getStatut_enchere() == 1 &&
                article.getDate_fin_encheres().isBefore(LocalDate.now())) {

            // Mise à jour de l'état en base
            articleAVendreDAO.updateEtat(article.getNo_article(), 2); // 2 = CLOTUREE
            article.setStatut_enchere(2);
        }

        return article;
    }

    @Override
    public ArticleAVendre findById(int no_article) {
        return articleAVendreDAO.read(no_article);
    }

    @Override
    public void save(ArticleAVendre article) {
        articleAVendreDAO.create(article);
    }

    @Override
    public void update(ArticleAVendre article) {
        articleAVendreDAO.update(article);
    }
@Override
    public List<ArticleAVendre> getMesVentesEnCours(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesEnCours(pseudo, search, categorie);
    }

    @Override
    public List<ArticleAVendre> getMesVentesNonDebutees(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesNonDebutees(pseudo, search, categorie);
    }

    @Override
    public List<ArticleAVendre> getMesVentesTerminees(String pseudo, String search, Integer categorie) {
        return articleAVendreDAO.findMesVentesTerminees(pseudo, search, categorie);
    }




}