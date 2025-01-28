package com.example.Antoflix.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    public String showHomePage(){
        return "index";  // "index" represents the name of the html file within the project, name of the Thymeleaf template
    }
}
