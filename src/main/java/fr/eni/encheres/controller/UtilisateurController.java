package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {
    UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/monProfil")
    public String afficherMonProfil(@RequestParam(name= "pseudo", required = true)String pseudo, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("user", utilisateur);

        // Vérifier si l'utilisateur connecté est le même que celui consulté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwnProfile = pseudo.equals(authentication.getName());

        // Ajouter à l'attribut modèle isOwnProfile pour le contrôle dans Thymeleaf
        model.addAttribute("isOwnProfile", isOwnProfile);

        return "profil";
    }

    @GetMapping("/profil")
    public String afficherUnProfil(@RequestParam(name= "id_utilisateur", required = true)String id_utilisateur, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(id_utilisateur);
        if (utilisateur == null) {
            return "index";
        }
        model.addAttribute("user", utilisateur);

        // Vérifier si l'utilisateur connecté est le même que celui consulté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwnProfile = id_utilisateur.equals(authentication.getName());

        // Ajouter à l'attribut modèle isOwnProfile pour le contrôle dans Thymeleaf
        model.addAttribute("isOwnProfile", isOwnProfile);


        return "profil";
    }
}
