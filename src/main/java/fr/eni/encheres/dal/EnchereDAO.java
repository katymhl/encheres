package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.DTO.GagnantDTO;
import fr.eni.encheres.bo.Enchere;

import java.util.List;
import java.util.Optional;

public interface EnchereDAO {

    Enchere read(String id_utilisateur,int no_article,int montant_enchere);

    List<Enchere> findAll();

    void create(Enchere enchere);

    List<Enchere> findByNoArticle(int no_article);

    void update(Enchere enchere);

    void delete(String id_utilisateur,int no_article,int montant_enchere);

     List<ArticleAVendre> findEncheresOuvertesSansParticipation(String pseudo, String search, Integer categorie);

     List<ArticleAVendre> findEncheresEnCoursByUtilisateur(String pseudo, String search, Integer categorie);

     List<ArticleAVendre> findEncheresTermineesByUtilisateur(String pseudo, String search, Integer categorie);

    Optional<GagnantDTO> findWinnerByNoArticle(int no_article);
}
