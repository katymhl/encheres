package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/{page}")
    public String viewPage(@PathVariable String page) {
        return page;
    }
}
