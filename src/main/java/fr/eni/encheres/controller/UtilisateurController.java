package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisateurController {
    UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/monProfil")
    public String getDetail() {
        System.out.println(utilisateurService.findById("coach_admin"));
        return "profil.html";
    }
}
