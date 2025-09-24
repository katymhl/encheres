package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.CategorieDAOImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    CategorieDAO categorieDAO ;

    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    @Override
    public Categorie read(Long no_categorie) {
        return categorieDAO.read(no_categorie);
    }

    @Override
    public List<Categorie> getAllCategories() {
        return categorieDAO.findAll();
    }

    @Override
    public Categorie consulterCategorieParId(Long id) {
        return categorieDAO.read(id);
    }
}
