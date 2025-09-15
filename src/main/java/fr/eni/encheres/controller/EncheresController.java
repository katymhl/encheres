package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class EncheresController {
UtilisateurService utilisateurService;
public EncheresController(UtilisateurService utilisateurService){
    this.utilisateurService = utilisateurService;
}

    @GetMapping
    public String getDetail() {

        System.out.println(utilisateurService.findById("coach_admin"));
        return "index.html";
    }
}
