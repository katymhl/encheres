package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.DTO.GagnantDTO;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnchereServiceImpl implements EnchereService {

    EnchereDAO enchereDAO;
    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }

    @Override
    public void create(Enchere enchere) {
        enchereDAO.create(enchere);
    }

    @Override
    public List<Enchere> findListByNoArticle(int no_article) {
        return enchereDAO.findByNoArticle(no_article);
    }

    @Override
    public List<ArticleAVendre> getEncheresOuvertesSansParticipation(String pseudo, String search, Integer categorie) {
        return enchereDAO.findEncheresOuvertesSansParticipation(pseudo, search, categorie);
    }

    @Override
    public List<ArticleAVendre> getEncheresEnCoursParUtilisateur(String pseudo, String search, Integer categorie) {
        return enchereDAO.findEncheresEnCoursByUtilisateur(pseudo, search, categorie);
    }

    @Override
    public List<ArticleAVendre> getEncheresTermineesParUtilisateur(String pseudo, String search, Integer categorie) {
        return enchereDAO.findEncheresTermineesByUtilisateur(pseudo, search, categorie);
    }

    public Optional<GagnantDTO> getWinner(int no_article, int statut_enchere) {
        if (statut_enchere == 2 || statut_enchere == 3) {
            return enchereDAO.findWinnerByNoArticle(no_article);
        }
        return Optional.empty();
    }
}
