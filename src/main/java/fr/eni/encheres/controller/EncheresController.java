package fr.eni.encheres.controller;

import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.*;
import jakarta.servlet.http.HttpSession;
import fr.eni.encheres.bo.enumeration.StatutEnchere;
import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.ArrayList;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes({ "categoriesEnSession" })
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
    public String getDetail(Model model, Principal principal) {
        List<ArticleAVendre> listActiveEnchere = articleAVendreService.findActiveEnchere();
        model.addAttribute("encheres", listActiveEnchere);

        // Vérifie si un utilisateur est connecté
        if (principal != null) {
            String pseudo = principal.getName();
            Utilisateur utilisateur = utilisateurService.findById(pseudo);
            model.addAttribute("utilisateur", utilisateur); // si tu veux afficher son nom ou ses crédits
            return "indexconnecter";
        }

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
    public String afficherDetailsArticle(@PathVariable("id") int id, Model model, Principal principal) {
        ArticleAVendre article = articleAVendreService.findById(id);
        Categorie categorie = categorieService.read(article.getNo_categorie());
        Adresse adresse = adresseService.findById(article.getNo_adresse_retrait());
        String pseudo = principal.getName();

        boolean estVendeur = article.getId_utilisateur().equals(pseudo);
        boolean enchereCommencee = (article.getPrix_vente() != null) && (article.getPrix_vente() > article.getPrix_initial());
      //  boolean enchereCommencee = article.getPrix_vente() > article.getPrix_initial();
//        boolean enchereTerminee = article.getDate_fin_encheres().isBefore(LocalDateTime.now());

        LocalDate dateFin = article.getDate_fin_encheres();
        LocalDateTime dateFinLocal = dateFin.atStartOfDay();

        boolean enchereTerminee = dateFinLocal.isBefore(LocalDateTime.now());
        boolean enchereModifiable = estVendeur
                && !enchereCommencee
                && article.getStatut_enchere() == 0;

        model.addAttribute("enchereModifiable", enchereModifiable);

        model.addAttribute("enchere", article);
        model.addAttribute("categorie", categorie);
        model.addAttribute("adresse", adresse);
        model.addAttribute("estVendeur", estVendeur);
        model.addAttribute("enchereCommencee", enchereCommencee);
        model.addAttribute("enchereTerminee", enchereTerminee);

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
        model.addAttribute("search", search);
        model.addAttribute("categorie", categorie);
        model.addAttribute("mode", mode);
        model.addAttribute("filtreAchats", filtreAchats);
        model.addAttribute("filtreVentes", filtreVentes);

        return "indexConnecter";
    }





//    private int calculerStatut(LocalDate dateDebut, LocalDate dateFin) {
//        LocalDate today = LocalDate.now();
//
//        if (today.isBefore(dateDebut)) {
//            return 0; // NON_COMMENCEE
//        } else if (!today.isAfter(dateFin)) {
//            return 1; // EN_COURS
//        } else {
//            return 2; // CLOTUREE
//        }
//    }


        @Transactional
        @PostMapping("/encherir/{id}")
        public String faireUneOffre(@PathVariable("id") int id,
        @RequestParam("montant") int montant,
        Principal principal,
        RedirectAttributes redirectAttributes) {

            ArticleAVendre article = articleAVendreService.findById(id);
            String pseudo = principal.getName();

            // Vérification : pas le vendeur
            if (article.getId_utilisateur().equals(pseudo)) {
                redirectAttributes.addFlashAttribute("erreur", "Vous ne pouvez pas enchérir sur votre propre article.");
                return "redirect:/details/" + id;
            }

            // Vérification : enchère terminée (par date ou statut)
            LocalDate dateFin = article.getDate_fin_encheres();
            LocalDateTime dateFinLocal = dateFin.atStartOfDay();

            if (dateFinLocal.isBefore(LocalDateTime.now()) || article.getStatut_enchere() == 100) {
                redirectAttributes.addFlashAttribute("erreur", "L'enchère est terminée.");
                return "redirect:/details/" + id;
            }

            // Vérification : montant supérieur à l'offre actuelle
            Integer prixActuel = article.getPrix_vente() != null ? article.getPrix_vente() : article.getPrix_initial();
            if (montant <= prixActuel) {
                redirectAttributes.addFlashAttribute("erreur", "Votre offre doit être supérieure à la meilleure offre actuelle.");
                return "redirect:/details/" + id;
            }

            Utilisateur enchérisseur = utilisateurService.findById(pseudo);

            // Vérification : points suffisants
            if (enchérisseur.getCredit() < montant) {
                redirectAttributes.addFlashAttribute("erreur", "Vous n'avez pas assez de points pour cette offre.");
                return "redirect:/details/" + id;
            }

            // Re-créditer l'ancien meilleur enchérisseur (si existant)

            List<Enchere> listeEnchereNoArticle = enchereService.findListByNoArticle(article.getNo_article());
            Optional<Enchere> meilleureEnchere = listeEnchereNoArticle.stream()
                    .filter(e -> e.getNo_article() == article.getNo_article())
                    .max(Comparator.comparingInt(Enchere::getMontant_enchere));



            if (meilleureEnchere.isPresent()) {
                String meilleurPseudo = meilleureEnchere.get().getId_utilisateur();
                Utilisateur ancienUtilisateur = utilisateurService.findById(meilleurPseudo);
                ancienUtilisateur.setCredit(ancienUtilisateur.getCredit() + meilleureEnchere.get().getMontant_enchere());
                utilisateurService.update(ancienUtilisateur);
            }



            // Débiter le nouvel enchérisseur
            int nouveaucredit=enchérisseur.getCredit() - montant;
            enchérisseur.setCredit(nouveaucredit);
            utilisateurService.update(enchérisseur);

            // Mettre à jour l’article
            article.setPrix_vente(montant);
//        article.setMeilleurEncherisseur(pseudo);
            articleAVendreService.update(article);

            // Enregistrer la nouvelle enchère
            Enchere nouvelleEnchere = new Enchere();
            nouvelleEnchere.setId_utilisateur(pseudo);
            nouvelleEnchere.setNo_article(article.getNo_article());
            nouvelleEnchere.setMontant_enchere(montant);
            nouvelleEnchere.setDate_enchere(  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            enchereService.create(nouvelleEnchere);

            redirectAttributes.addFlashAttribute("success", "Votre offre a été enregistrée !");
            return "redirect:/details/" + id;
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
                                  Principal principal,
                                  Model model) {

        // Récupération de l'utilisateur connecté
        String pseudo = principal.getName();
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", utilisateur);
        articleAVendre.setId_utilisateur(utilisateur.getPseudo()); // ou id selon ton modèle

        articleAVendre.setStatut_enchere(calculerStatut(articleAVendre.getDate_debut_encheres(),
                articleAVendre.getDate_fin_encheres()));


        // Sauvegarde de l'article
        articleAVendreService.save(articleAVendre);

        // Redirection vers la page de liste des articles
        return "redirect:/";
    }

    private int calculerStatut(LocalDate debut, LocalDate fin) {
        LocalDate now = LocalDate.now();

        if (debut == null || fin == null) {
            return 0; // ou un statut "non défini"
        }

        if (now.isBefore(debut)) {
            return StatutEnchere.NON_COMMENCEE.getValue();
        } else if (!now.isAfter(fin)) {
            return StatutEnchere.EN_COURS.getValue();
        } else {
            return StatutEnchere.CLOTUREE.getValue();
        }
    }


//    private int calculerStatut(LocalDate dateDebut, LocalDate dateFin) {
//        LocalDate today = LocalDate.now();
//
//        if (today.isBefore(dateDebut)) {
//            return 0; // NON_COMMENCEE
//        } else if (!today.isAfter(dateFin)) {
//            return 1; // EN_COURS
//        } else {
//            return 2; // CLOTUREE
//        }
//    }

    @GetMapping("/vendre/update")
    public String afficherModifierUneEnchere(@RequestParam("no_article") int no_article,
                                             Model model,
                                             Principal principal) {

        ArticleAVendre articleAVendre = articleAVendreService.findById(no_article);
        model.addAttribute("articleAVendre", articleAVendre);

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

        return "update-sale"; // nom de ton template Thymeleaf
    }


    @PostMapping("/vendre/update")
    public String modifierUneEnchere(@ModelAttribute("articleAVendre") ArticleAVendre articleAVendre,
                                     Principal principal,
                                     Model model) {

        // Récupération de l'utilisateur connecté
        String pseudo = principal.getName();
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", utilisateur);
        articleAVendre.setId_utilisateur(utilisateur.getPseudo()); // ou id selon ton modèle

        ArticleAVendre ancien = articleAVendreService.findById(articleAVendre.getNo_article());

        // Si les dates sont null dans le formulaire → garder les anciennes
        if (articleAVendre.getDate_debut_encheres() == null) {
            articleAVendre.setDate_debut_encheres(ancien.getDate_debut_encheres());
        }
        if (articleAVendre.getDate_fin_encheres() == null) {
            articleAVendre.setDate_fin_encheres(ancien.getDate_fin_encheres());
        }

        articleAVendre.setStatut_enchere(calculerStatut(articleAVendre.getDate_debut_encheres(),
                articleAVendre.getDate_fin_encheres()));


        // Sauvegarde de l'article
        articleAVendreService.update(articleAVendre);

        return "redirect:/details/" + articleAVendre.getNo_article();
    }


    @PostMapping("/vendre/annuler")
    public String annulerVente(@RequestParam("no_article") Integer noArticle) {
        ArticleAVendre article = articleAVendreService.findById(noArticle);
        article.setStatut_enchere(100);
        articleAVendreService.update(article);
        return "redirect:/";
    }



}
