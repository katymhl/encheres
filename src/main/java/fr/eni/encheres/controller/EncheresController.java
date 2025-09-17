package fr.eni.encheres.controller;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.AdresseServiceImpl;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EncheresController {
UtilisateurService utilisateurService;
    AdresseService adresseService;



public EncheresController(UtilisateurService utilisateurService , AdresseService adresseService ) {
    this.utilisateurService = utilisateurService;
    this.adresseService = adresseService;
}

    @GetMapping("/")
    public String getDetail() {

        System.out.println(utilisateurService.findById("coach_admin"));
        return "index.html";
    }

    @GetMapping("/{page}")
    public String viewPage(@PathVariable String page) {
        return page;
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

        if ( resultUtilisateur.hasErrors() || resultAdresse.hasErrors()) {

            model.addAttribute("adresse", adresse);
            model.addAttribute("utilisateur", utilisateur);
            return "new-profil-form";
        }

  List<Adresse> tout =adresseService.findByall();


            int numeroadresse=adresseService.getOrCreateAdresse(adresse.getRue() , adresse.getCode_postal(),adresse.getVille());

              utilisateur.setNo_adresse(numeroadresse);
        System.out.println(utilisateur);
        Utilisateur existingUser = utilisateurService.findById(utilisateur.getPseudo());
        Utilisateur existingUseremail = utilisateurService.findByUserEmail(utilisateur.getEmail());

        if (existingUser != null) {
            resultUtilisateur.rejectValue("pseudo", "error.utilisateur",
                    "Le pseudo '" + utilisateur.getPseudo() + "' existe déjà !");
            if (existingUseremail != null) {
                resultUtilisateur.rejectValue("email", "error.utilisateur",
                        "L'email '" + utilisateur.getEmail() + "' existe déjà !");}

            model.addAttribute("adresse", adresse);
            model.addAttribute("utilisateur", utilisateur);
            return "new-profil-form";
        }
        utilisateurService.creat(utilisateur);
        // Exemple de sauvegarde
        //adresseService.save(adresse);
       // utilisateurService.save(utilisateur);

        return "redirect:/";
    }

}
