package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.CategorieDAOImpl;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements CategorieService {

    CategorieDAO categorieDAO ;

    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }


    @Override
    public Categorie read(int no_categorie) {
        return categorieDAO.read(no_categorie);
    }
}
