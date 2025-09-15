package fr.eni.encheres;

import org.springframework.web.bind.annotation.GetMapping;

public class controller {


    @GetMapping
    public String getDetail() {


        return "index.html";
    }
}
