package fr.eni.encheres.controller;

import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.bo.DTO.GagnantDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import fr.eni.encheres.bo.enumeration.StatutEnchere;
import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EncheresController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

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


    //Vue de la page d'accueil
    @GetMapping("/")
    public String getDetail(Model model, Principal principal) {
        List<ArticleAVendre> listActiveEnchere = articleAVendreService.findActiveEnchere();
        listActiveEnchere.sort(Comparator.comparing(ArticleAVendre::getDate_fin_encheres));
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

        resultats.sort(Comparator.comparing(ArticleAVendre::getDate_fin_encheres));

        model.addAttribute("encheres", resultats);
        model.addAttribute("search", search);
        model.addAttribute("categorie", categorie);
        model.addAttribute("mode", mode);
        model.addAttribute("filtreAchats", filtreAchats);
        model.addAttribute("filtreVentes", filtreVentes);

        return "indexConnecter";
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

    //Vue de la page admin (suelement visible pour les personnes au rôle admin)
    @GetMapping("/admin")
    public String getAdmin() {
        System.out.println(utilisateurService.findById("coach_admin"));
        return "admin.html";
    }

    //Vue de la page de création d'un nouvel utilisateur
    @GetMapping("/inscription")
    public String afficherFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adresse", new Adresse());
        return "new-profil-form";
    }

    @PostMapping("/inscription")
    public String creerUtilisateur(
            @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult resultUtilisateur,
            @ModelAttribute("adresse") Adresse adresse,
            BindingResult resultAdresse,
            Model model) {

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

    //Vue du détail d'une vente
    @GetMapping("/details/{id}")
    public String afficherDetailsArticle(@PathVariable("id") int id, Model model, Principal principal) {
        ArticleAVendre article = articleAVendreService.findById(id);
        Categorie categorie = categorieService.read(article.getNo_categorie());
        Adresse adresse = adresseService.findById(article.getNo_adresse_retrait());
        String pseudo = principal.getName();

        boolean estVendeur = article.getId_utilisateur().equals(pseudo);
        boolean enchereCommencee = (article.getPrix_vente() != null) && (article.getPrix_vente() > article.getPrix_initial());

        LocalDate dateFin = article.getDate_fin_encheres();
        LocalDateTime dateFinLocal = dateFin.atStartOfDay();

        boolean enchereTerminee = dateFinLocal.isBefore(LocalDateTime.now())
                || article.getStatut_enchere() == 2
                || article.getStatut_enchere() == 3;

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

        if (enchereTerminee) {
            Optional<GagnantDTO> gagnant = enchereService.getWinner(article.getNo_article(), article.getStatut_enchere());
            gagnant.ifPresent(g -> model.addAttribute("gagnant", g));
        }

        return "sale-details";
    }


    //Formulaire pour enchérir sur la page de détail
    @Transactional
    @PostMapping("/encherir/{id}")
    public String faireUneOffre(@PathVariable("id") int id,
                                @RequestParam("montant") int montant,
                                Principal principal,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        Locale locale = localeResolver.resolveLocale(request);

        ArticleAVendre article = articleAVendreService.findById(id);
        String pseudo = principal.getName();

        // Vérification : pas le vendeur
        if (article.getId_utilisateur().equals(pseudo)) {
            String msg = messageSource.getMessage("enchere.error.self", null, locale);
            redirectAttributes.addFlashAttribute("erreur", msg);
            return "redirect:/details/" + id;
        }

        // Vérification : enchère terminée
        LocalDateTime dateFin = article.getDate_fin_encheres().atStartOfDay();
        if (dateFin.isBefore(LocalDateTime.now()) || article.getStatut_enchere() == 100) {
            String msg = messageSource.getMessage("enchere.error.ended", null, locale);
            redirectAttributes.addFlashAttribute("erreur", msg);
            return "redirect:/details/" + id;
        }

        // Vérification : montant supérieur à l'offre actuelle
        int prixActuel = article.getPrix_vente() != null ? article.getPrix_vente() : article.getPrix_initial();
        if (montant <= prixActuel) {
            String msg = messageSource.getMessage("enchere.error.low", null, locale);
            redirectAttributes.addFlashAttribute("erreur", msg);
            return "redirect:/details/" + id;
        }

        Utilisateur enchérisseur = utilisateurService.findById(pseudo);

        // Vérification : points suffisants
        if (enchérisseur.getCredit() < montant) {
            String msg = messageSource.getMessage("enchere.error.credit", null, locale);
            redirectAttributes.addFlashAttribute("erreur", msg);
            return "redirect:/details/" + id;
        }

        // Re-créditer l'ancien meilleur enchérisseur
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
        enchérisseur.setCredit(enchérisseur.getCredit() - montant);
        utilisateurService.update(enchérisseur);

        // Mettre à jour l’article
        article.setPrix_vente(montant);
        articleAVendreService.update(article);

        // Enregistrer la nouvelle enchère
        Enchere nouvelleEnchere = new Enchere();
        nouvelleEnchere.setId_utilisateur(pseudo);
        nouvelleEnchere.setNo_article(article.getNo_article());
        nouvelleEnchere.setMontant_enchere(montant);
        nouvelleEnchere.setDate_enchere(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        enchereService.create(nouvelleEnchere);

        String successMsg = messageSource.getMessage("enchere.success", null, locale);
        redirectAttributes.addFlashAttribute("success", successMsg);
        return "redirect:/details/" + id;
    }

    //Formulaire pour valider la remise d'une vente
    @Transactional
    @PostMapping("/details/{id}/terminer")
    public String terminerVente(@PathVariable("id") int no_article, Principal principal) {
        // 1. Récupérer l'article
        ArticleAVendre article = articleAVendreService.findById(no_article);

        if (article != null && article.getStatut_enchere() == 2) {
            // 2. Passer le statut à "terminée/livrée"
            article.setStatut_enchere(3);
            articleAVendreService.update(article);

            // 3. Récupérer le gagnant de l'enchère
            Optional<GagnantDTO> gagnantOpt = enchereService.getWinner(article.getNo_article(), article.getStatut_enchere());
            if (gagnantOpt.isPresent()) {
                GagnantDTO gagnant = gagnantOpt.get();

                // 4. Récupérer le vendeur
                Utilisateur vendeur = utilisateurService.findById(article.getId_utilisateur());

                // 5. Ajouter le montant de la meilleure enchère au crédit du vendeur
                vendeur.setCredit(vendeur.getCredit() + gagnant.montant_enchere());
                utilisateurService.update(vendeur);
            }
        }

        // Rediriger vers la page de détail
        return "redirect:/details/" + no_article;
    }



    //Vue de la page de profil d'un utilisateur
    @GetMapping("/profil/{pseudo}")
    public String afficherProfil(@PathVariable String pseudo, Model model) {
        Utilisateur user = utilisateurService.findById(pseudo);
        model.addAttribute("utilisateur", user);
        return "profil";
    }

    //Vue de la page pour créer un nouvel article à vendre
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
                                  @RequestParam("image") MultipartFile image,
                                  Principal principal,
                                  Model model) throws IOException {

        // Récupération de l'utilisateur connecté
        String pseudo = principal.getName();
        Utilisateur utilisateur = utilisateurService.findById(pseudo);
        articleAVendre.setId_utilisateur(utilisateur.getPseudo());
        model.addAttribute("utilisateur", utilisateur);

        // Calcul du statut de l'enchère
        articleAVendre.setStatut_enchere(calculerStatut(
                articleAVendre.getDate_debut_encheres(),
                articleAVendre.getDate_fin_encheres()
        ));

        // Traitement de la photo
        if (image != null && !image.isEmpty()) {
            articleAVendre.setPhoto(image.getBytes());
        }

        // Sauvegarde
        articleAVendreService.save(articleAVendre);

        return "redirect:/"; // ou vers une page de confirmation
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

    //Vue du formulaire pour choisir l'image de l'article à vendre
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> afficherImage(@PathVariable int id) {
        ArticleAVendre article = articleAVendreService.findById(id);
        if (article == null || article.getPhoto() == null) {
            return ResponseEntity.notFound().build(); // ou renvoyer une image par défaut
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // adapte selon ton format
                .body(article.getPhoto());
    }

    //Vue pour la modification d'un article déjà en vente mais dont l'enchère n'a pas débuté
    @GetMapping("/vendre/update")
    public String afficherModifierUneEnchere(@RequestParam("no_article") int no_article,
                                             Model model,
                                             Principal principal) {

        ArticleAVendre articleAVendre = articleAVendreService.findById(no_article);
        model.addAttribute("articleAVendre", articleAVendre);

        //  Charge toutes les catégories pour le menu déroulant
        model.addAttribute("categories", categorieService.getAllCategories());

        // Charge toutes les adresses depuis la base
        List<Adresse> adresses = adresseService.findByall(); // Assure-toi que findAll() renvoie List<Adresse>
        model.addAttribute("adresses", adresses);

        //  Si un utilisateur est connecté, récupère ses informations
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
                                     @RequestParam("image") MultipartFile image,Principal principal,
                                     Model model) throws IOException  {

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

        // Traitement de la photo
        if (image != null && !image.isEmpty()) {
            articleAVendre.setPhoto(image.getBytes());
        }
        // Sauvegarde de l'article
        articleAVendreService.update(articleAVendre);

        return "redirect:/details/" + articleAVendre.getNo_article();
    }


    //Formulaire d'annulation de la vente d'un article tant que son enchère n'a pas débuté
    @PostMapping("/vendre/annuler")
    public String annulerVente(@RequestParam("no_article") Integer noArticle) {
        ArticleAVendre article = articleAVendreService.findById(noArticle);
        article.setStatut_enchere(100);
        articleAVendreService.update(article);
        return "redirect:/";
    }

    //Fonctionnalité de choix de la langue
    @GetMapping("/change-lang")
    public String changerLangue(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        // Stocker la langue dans la session
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        // Rediriger vers la page précédente
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");

    }

}
