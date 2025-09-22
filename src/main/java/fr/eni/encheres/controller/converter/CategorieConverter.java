package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategorieConverter implements Converter<String, Categorie> {

    // Injection des Services
    private final CategorieService categorieService;

    public CategorieConverter(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public Categorie convert(String id) {
        long parsedId = Long.parseLong(id);
        return categorieService.consulterCategorieParId(parsedId);
    }
}