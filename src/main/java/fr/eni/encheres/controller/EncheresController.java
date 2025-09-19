package fr.eni.encheres.controller;

import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EncheresController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleAVendreService articleAVendreService;
    private final CategorieService categorieService;
    private final EnchereService enchereService ;

    public EncheresController(UtilisateurService utilisateurService,
                              AdresseService adresseService,
                              ArticleAVendreService articleAVendreService,CategorieService categorieService, EnchereService enchereService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.articleAVendreService = articleAVendreService;
        this.categorieService = categorieService;
        this.enchereService = enchereService;
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

    @GetMapping("/details/{id}")
    public String afficherDetailsArticle(@PathVariable("id") int id, Model model) {
        ArticleAVendre article = articleAVendreService.findById(id);
        Categorie categorie = categorieService.read(article.getNo_categorie());
        Adresse adresse = adresseService.findById(article.getNo_adresse_retrait());

        model.addAttribute("enchere", article);
        model.addAttribute("categorie", categorie);
        model.addAttribute("adresse", adresse);

        return "sale-details";
    }



    @GetMapping("/profil/{pseudo}")
    public String afficherProfil(@PathVariable String pseudo, Model model) {
        Utilisateur user = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", user);
        return "profil";
    }





//    @GetMapping("/accueil/connecter")
//    public String getDetailConnecter(Model model) {
//        List<ArticleAVendre> listActiveEnchere = articleAVendreService.findActiveEnchere();
//        System.out.println("listActiveEnchere: " + listActiveEnchere.size());
//        model.addAttribute("encheres", listActiveEnchere);
//
//        System.out.println(utilisateurService.findById("coach_admin"));
//        return "indexConnecter";
//    }


    @GetMapping("/accueil/connecter")
    public String filtrerEncheres(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categorie,
            @RequestParam(required = false) String mode,
            @RequestParam(required = false) String filtreAchats,
            @RequestParam(required = false) String filtreVentes,
           Principal principal,
            Model model) {

        String pseudo = principal.getName();
//        String pseudo = (String) session.getAttribute("pseudo");
        List<ArticleAVendre> resultats = new ArrayList<>();

        if ("achats".equals(mode)) {
            if (filtreAchats != null) {
                switch (filtreAchats) {
                    case "ouvertes" -> resultats = enchereService.getEncheresOuvertesSansParticipation(pseudo, search, categorie);
                    case "encours" -> resultats = enchereService.getEncheresEnCoursParUtilisateur(pseudo, search, categorie);
                    case "remportees" -> resultats = enchereService.getEncheresTermineesParUtilisateur(pseudo, search, categorie);
                    default -> System.out.println("Filtre achats inconnu : " + filtreAchats);
                }
            } else {
                System.out.println("Aucun filtreAchats fourni.");
            }
        } else if ("ventes".equals(mode)) {
            if (filtreVentes != null) {
                switch (filtreVentes) {
                    case "encours" -> resultats = articleAVendreService.getMesVentesEnCours(pseudo, search, categorie);
                    case "nonDebutees" -> resultats = articleAVendreService.getMesVentesNonDebutees(pseudo, search, categorie);
                    case "terminees" -> resultats = articleAVendreService.getMesVentesTerminees(pseudo, search, categorie);
                    default -> System.out.println("Filtre ventes inconnu : " + filtreVentes);
                }
            } else {
                System.out.println("Aucun filtreVentes fourni.");
            }
        } else {
            System.out.println("Mode inconnu ou non fourni : " + mode);
        }

        model.addAttribute("encheres", resultats);
        return "indexConnecter";
    }









}
