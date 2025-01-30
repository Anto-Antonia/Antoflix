package com.example.Antoflix.controller.thymeleafController;

import com.example.Antoflix.dto.request.user.RegisterRequest;
import com.example.Antoflix.dto.request.user.SignInRequest;
import com.example.Antoflix.service.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebAuthController {
    private AuthService authService;

    public WebAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signIn")
    public String signInPage(Model model){
        model.addAttribute("signInRequest", new SignInRequest()); // Bind DTO to the form
        return "signIn";
    }

    // Processing the sign in form submission
    @PostMapping("/signIn")
    public String processSignIn(@Valid @ModelAttribute SignInRequest signInRequest, Model model){
        System.out.println("Received email: " + signInRequest.getEmail());
        System.out.println("Received password: " + signInRequest.getPassword());

        try {
            authService.signIn(signInRequest); // Authenticate the user
            System.out.println("User authenticated successfully! Redirecting...");
            return "redirect:/dashboard"; // Make sure this endpoint exists!!!!
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid email or password");
            return "signIn";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Authentication failed. Please try again");
            return "signIn";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("register")
    public String registerProcess(@ModelAttribute("registerRequest") RegisterRequest registerRequest, RedirectAttributes redirectAttributes){
        try {
            authService.registerUser(registerRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/signIn"; // Redirect to sign-in page after successful registration
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/register"; // Reload registration page with error message
        }
    }
}
