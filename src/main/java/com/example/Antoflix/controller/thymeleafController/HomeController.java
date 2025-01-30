package com.example.Antoflix.controller.thymeleafController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String showHomePage(){
        return "index";  // "index" represents the name of the html file within the project, name of the Thymeleaf template
    }
}
