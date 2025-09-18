package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VenteController {
    UtilisateurService utilisateurService;
    public VenteController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/vendre")
    public String getDetail() {
        System.out.println(utilisateurService.findById("coach_admin"));
        return "sale-form.html";
    }
}
