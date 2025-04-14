package com.example.Antoflix.controller.thymeleafController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String showHomePage(){
        return "index";  // "index" represents the name of the html file within the project, name of the Thymeleaf template
    }

//    @GetMapping("/home")
//    public String hopePage(Authentication authentication){
//        String role = authentication.getAuthorities().iterator().next().getAuthority();
//        if("admin".equals(role)){
//            return "redirect: /admin-dashboard";
//        } else{
//            return "redirect: /dashboard";
//        }
//    }

    @GetMapping("/watchlist")
    public String getWatchlistPage(){
        return "watchlist";
    }
}
