package fr.eni.encheres.controller;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UtilisateurController {

    private final PasswordEncoder passwordEncoder;
    UtilisateurService utilisateurService;
    AdresseService adresseService;

    public UtilisateurController(UtilisateurService utilisateurService, AdresseService adresseService, PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.passwordEncoder = passwordEncoder;
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
    public String afficherFormulaire(@RequestParam(name= "pseudo", required = true)String pseudo, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        Adresse adresse = adresseService.getAdresseByPseudo(pseudo);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("adresse", adresse);
        return "update-profil-form";
    }

    @PostMapping("/monProfil/update")
    public String modifierMonProfil(
            @ModelAttribute("utilisateur") Utilisateur utilisateur,
            @ModelAttribute("adresse") Adresse adresse,
            Model model) {

        model.addAttribute("adresse", adresse);
        model.addAttribute("utilisateur", utilisateur);

        int numeroadresse = adresseService.getOrCreateAdresse(
                adresse.getRue(),
                adresse.getCode_postal(),
                adresse.getVille()
        );

        utilisateur.setNo_adresse(numeroadresse);
        System.out.println("Utilisateur : " + utilisateur);
        utilisateurService.update(utilisateur);
        return "redirect:/monProfil?pseudo=" + utilisateur.getPseudo();
    }

    @GetMapping("/monProfil/update-pwd")
    public String afficherFormulaireMotDePasse(@RequestParam(name= "pseudo", required = true)String pseudo, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", utilisateur);
        return "update-password-form";
    }

    @PostMapping("/monProfil/update-pwd")
    public String modifierMotDePasse(
            @RequestParam("ancien_mot_de_passe") String ancien,
            @RequestParam("mot_de_passe") String nouveau,
            @RequestParam("confirmPassword") String confirm,
            Principal principal,
            Model model) {

        String pseudo = principal.getName(); // utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", utilisateur);


        if (!passwordEncoder.matches(ancien, utilisateur.getMot_de_passe())) {
            model.addAttribute("erreurAncienPwd", "Le mot de passe actuel est incorrect.");
            return "update-password-form";
        }

        //monProfil/update-pwd?pseudo=" + pseudo
        // Vérification confirmation
        if (!nouveau.equals(confirm)) {
            model.addAttribute("erreurConfirm", "Les mots de passe ne correspondent pas.");
            return "update-password-form";
        }

        // Mise à jour du mot de passe ()
        utilisateurService.updatePWD(pseudo, nouveau);

        return "redirect:/monProfil?pseudo=" + pseudo;
    }
}
