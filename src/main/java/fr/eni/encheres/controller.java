package fr.eni.encheres;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class controller {


    @GetMapping
    public String getDetail() {
        return "index.html";
    }

    @GetMapping("/{page}")
    public String viewPage(@PathVariable String page) {
        return page;
    }
}
