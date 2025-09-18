package fr.eni.encheres.controller;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {

    UtilisateurService utilisateurService;
    AdresseService adresseService;

    public UtilisateurController(UtilisateurService utilisateurService, AdresseService adresseService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
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


    @GetMapping("/monProfil/update")
    public String afficherFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adresse", new Adresse());
        return "update-profil-form";
    }

    @PostMapping("/monProfil/update")
    public String modifierMonProfil(
            @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult resultUtilisateur,
            @Valid @ModelAttribute("adresse") Adresse adresse,
            BindingResult resultAdresse,
            Model model) {

        if (resultUtilisateur.hasErrors() || resultAdresse.hasErrors()) {
            model.addAttribute("adresse", adresse);
            model.addAttribute("utilisateur", utilisateur);
            return "update-profil-form";
        }

        int numeroadresse = adresseService.getOrCreateAdresse(
                adresse.getRue(),
                adresse.getCode_postal(),
                adresse.getVille()
        );

        utilisateur.setNo_adresse(numeroadresse);
        System.out.println(utilisateur);

        Utilisateur existingUser = utilisateurService.findById(utilisateur.getPseudo());
        Utilisateur existingUseremail = utilisateurService.findByUserEmail(utilisateur.getEmail());

        if (existingUser != null) {
            resultUtilisateur.rejectValue("pseudo", "error.utilisateur",
                    "Le pseudo '" + utilisateur.getPseudo() + "' existe déjà !");
        }

        if (existingUseremail != null) {
            resultUtilisateur.rejectValue("email", "error.utilisateur",
                    "L'email '" + utilisateur.getEmail() + "' existe déjà !");
        }

        if (resultUtilisateur.hasErrors()) {
            model.addAttribute("adresse", adresse);
            model.addAttribute("utilisateur", utilisateur);
            return "update-profil-form";
        }

        utilisateurService.creat(utilisateur);
        return "redirect:/monProfil";
    }
}
