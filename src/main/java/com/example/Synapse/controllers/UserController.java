package com.example.Synapse.controllers;

import com.example.Synapse.models.User;
import com.example.Synapse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registration() {
        return "registration";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model) {
        if(!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Username reserved");
            return "registration";
        }
        return "redirect:/login";
    }



}
