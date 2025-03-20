package com.example.Antoflix.controller.thymeleafController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(){
        return "admin-dashboard";
    }

    @GetMapping("/admin-panel")
    public String adminPanel() {
        return "admin-panel";
    }
}
