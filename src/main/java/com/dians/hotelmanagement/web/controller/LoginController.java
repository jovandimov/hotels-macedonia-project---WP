package com.dians.hotelmanagement.web.controller;

import com.dians.hotelmanagement.model.User;
import com.dians.hotelmanagement.model.exceptions.InvalidUserCredentialsException;
import com.dians.hotelmanagement.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("bodyContent", "login");
        model.addAttribute("hasError", true);
        model.addAttribute("error", error);
        return "master-template";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = this.authService.login(request.getParameter("email"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("bodyContent", "login");
            return "master-template";
        }
    }

}