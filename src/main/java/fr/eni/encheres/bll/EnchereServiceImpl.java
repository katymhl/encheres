package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    EnchereDAO enchereDAO;
    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
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



}
