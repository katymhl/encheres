package fr.eni.encheres.controller.connexion;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/connexion")
@SessionAttributes({"utilisateurEnSession"})
public class ConnexionController {

    private UtilisateurService service;

    public ConnexionController(UtilisateurService service) {
        this.service = service;
    }

    @ModelAttribute("utilisateurEnSession")
    public Utilisateur utilisateurEnSession() {
        return new Utilisateur();
    }

//    @GetMapping("/session")
//    public String connexion(@ModelAttribute("utilisateurEnSession")Utilisateur utilisateurEnSession
//                            String pseudo) {
//        Utilisateur aCharger = service.findById(pseudo);
//        if (aCharger != null) {
//            utilisateurEnSession.setId(aCharger.getId());
//            utilisateurEnSession.setNom(aCharger.getNom());
//            utilisateurEnSession.setPrenom(aCharger.getPrenom());
//            utilisateurEnSession.setPseudo(aCharger.getPseudo());
//            utilisateurEnSession.setAdmin(aCharger.isAdmin());
//
//        } else {
//            utilisateurEnSession.setId(0);
//            utilisateurEnSession.setNom(null);
//            utilisateurEnSession.setPrenom(null);
//            utilisateurEnSession.setPseudo(null);
//            utilisateurEnSession.setAdmin(false);
//
//        }
//        System.out.println(utilisateurEnSession);
//        // Evidemment on évite de mettre un mot de passe en session
//        // (surtout non chiffré)
//
//        return "redirect:/enchere";
//    }

}
