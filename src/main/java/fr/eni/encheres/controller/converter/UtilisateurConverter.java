package fr.eni.encheres.controller.converter;


import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UtilisateurConverter implements Converter<String, Utilisateur> {

    private final UtilisateurService utilisateurService;

    public UtilisateurConverter(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public Utilisateur convert(String pseudo) {
        // Appel au service pour obtenir l'utilisateur en fonction du pseudo (de type String)
        return utilisateurService.findById(pseudo);
    }
}
