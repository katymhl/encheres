package fr.eni.encheres;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {


    @GetMapping
    public String getDetail() {


        return "index.html";
    }
}
