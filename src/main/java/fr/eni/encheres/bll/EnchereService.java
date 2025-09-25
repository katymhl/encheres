package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.DTO.GagnantDTO;
import fr.eni.encheres.bo.Enchere;

import java.util.List;
import java.util.Optional;

public interface EnchereService {

    void create(Enchere enchere);

    List<Enchere> findListByNoArticle(int no_article);

    List<ArticleAVendre> getEncheresOuvertesSansParticipation(String pseudo, String search, Integer categorie);

    List<ArticleAVendre> getEncheresEnCoursParUtilisateur(String pseudo, String search, Integer categorie);

    List<ArticleAVendre> getEncheresTermineesParUtilisateur(String pseudo, String search, Integer categorie);

    Optional<GagnantDTO> getWinner(int no_artcle, int statut);

}
