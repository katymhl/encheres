package fr.eni.encheres.controller;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes({ "categoriesEnSession" })
public class EncheresController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleAVendreService articleAVendreService;
    private final CategorieService categorieService;

    public EncheresController(UtilisateurService utilisateurService,
                              AdresseService adresseService,
                              ArticleAVendreService articleAVendreService,CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.articleAVendreService = articleAVendreService;
        this.categorieService = categorieService;
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

    @GetMapping("/inscription")
    public String afficherFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adresse", new Adresse());
        return "new-profil-form";
    }

    @PostMapping("/inscription")
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





    @GetMapping("/accueil/connecter")
    public String getDetailConnecter(Model model) {
        List<ArticleAVendre> listActiveEnchere = articleAVendreService.findActiveEnchere();
        System.out.println("listActiveEnchere: " + listActiveEnchere.size());
        model.addAttribute("encheres", listActiveEnchere);

        System.out.println(utilisateurService.findById("coach_admin"));
        return "indexConnecter";
    }


    @GetMapping("/vendre")
    public String afficherCreerUneEnchere(Model model, Principal principal) {

        // 1️⃣ Crée un objet vide pour le formulaire
        model.addAttribute("articleAVendre", new ArticleAVendre());

        // 2️⃣ Charge toutes les catégories pour le menu déroulant
        model.addAttribute("categories", categorieService.getAllCategories());

        // 3️⃣ Charge toutes les adresses depuis la base
        List<Adresse> adresses = adresseService.findByall(); // Assure-toi que findAll() renvoie List<Adresse>
        model.addAttribute("adresses", adresses);

        // 4️⃣ Si un utilisateur est connecté, récupère ses informations
        if (principal != null) {
            String pseudo = principal.getName();
            Utilisateur utilisateur = utilisateurService.findById(pseudo);
            model.addAttribute("utilisateur", utilisateur);

            // Récupère l'objet Adresse complet à partir de l'ID
            Integer noAdresse = utilisateur.getNo_adresse(); // l'ID de l'adresse
            Adresse adresseParDefaut = adresseService.findById(noAdresse);
            model.addAttribute("adresse", adresseParDefaut);
        }

        return "sale-form"; // nom de ton template Thymeleaf
    }


    @PostMapping("/vendre")
    public String creerUneEnchere(@ModelAttribute("articleAVendre") ArticleAVendre articleAVendre,
                                  BindingResult result,
                                  Principal principal,
                                  Model model) {
//
//        if (result.hasErrors()) {
//            return "sale-form";
//        }

        // Récupération de l'utilisateur connecté
        String pseudo = principal.getName();
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", utilisateur);
        articleAVendre.setId_utilisateur(utilisateur.getPseudo()); // ou id selon ton modèle



        // Sauvegarde de l'article
        articleAVendreService.save(articleAVendre);

        // Redirection vers la page de liste des articles
        return "redirect:/";
    }

//    private void injectUserAddresses(String pseudo, Model model) {
//        Utilisateur user = utilisateurService.findById(pseudo);
//        List<Adresse> adresses = adresseService.findByall();
//        if (adresses != null && !adresses.isEmpty()) {
//            model.addAttribute("adressesDisponibles", adresses);
//        }
//    }


}
