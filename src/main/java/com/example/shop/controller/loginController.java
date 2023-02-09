package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.service.RoleService;
import com.example.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class loginController {

    private final UserService userService;
    private final RoleService roleService;

    public loginController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Validated @ModelAttribute("user") User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "registration";
        }

        user.setRole(roleService.readById(2));
        userService.create(user);

        return "redirect:/login";
    }

}
