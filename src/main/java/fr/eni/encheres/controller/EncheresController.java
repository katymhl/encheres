package fr.eni.encheres.controller;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EncheresController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleAVendreService articleAVendreService;

    public EncheresController(UtilisateurService utilisateurService,
                              AdresseService adresseService,
                              ArticleAVendreService articleAVendreService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.articleAVendreService = articleAVendreService;
    }

    @GetMapping("/")
    public String getDetail(Model model) {
        List<ArticleAVendre> listActiveEnchere = articleAVendreService.findActiveEnchere();
        System.out.println("listActiveEnchere: " + listActiveEnchere.size());
        model.addAttribute("encheres", listActiveEnchere);

        System.out.println(utilisateurService.findById("coach_admin"));
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        System.out.println(utilisateurService.findById("coach_admin"));
        return "admin.html";
    }

    @GetMapping("/profil/creer")
    public String afficherFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adresse", new Adresse());
        return "new-profil-form";
    }

    @PostMapping("/profil/creer")
    public String creerUtilisateur(
            @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult resultUtilisateur,
            @Valid @ModelAttribute("adresse") Adresse adresse,
            BindingResult resultAdresse,
            Model model) {

        if (resultUtilisateur.hasErrors() || resultAdresse.hasErrors()) {
            model.addAttribute("adresse", adresse);
            model.addAttribute("utilisateur", utilisateur);
            return "new-profil-form";
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
            return "new-profil-form";
        }

        utilisateurService.creat(utilisateur);
        return "redirect:/login";
    }

    @GetMapping("/encheres")
    public String filtrerEncheres(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categorie,
            Model model
    ) {
        List<ArticleAVendre> resultats = articleAVendreService.filtrerArticles(search, categorie);
        model.addAttribute("encheres", resultats);
        return "index";
    }
}
