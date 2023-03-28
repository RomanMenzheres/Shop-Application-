package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, @AuthenticationPrincipal LoginDetails user) {

        model.addAttribute("user", userService.readById(user.getUser().getId()));

        return "profile";

    }

    @GetMapping("/update")
    public String update(@AuthenticationPrincipal LoginDetails user, Model model) {

        model.addAttribute("user", userService.readById(user.getUser().getId()));

        return "update-user";
    }

    @PostMapping("/update")
    public String update(@AuthenticationPrincipal LoginDetails loginDetails,
                         @Validated @ModelAttribute("user") User user,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "update-user";
        }

        User newUser = loginDetails.getUser();

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        String newPassword = newUser.getPassword();

        if (!user.getPassword().equals("")) {
            newPassword = user.getPassword();
        }

        userService.update(newUser, newPassword);

        return "redirect:/profile";
    }

    @GetMapping("/info/update/phone")
    public String updatePhone(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        User user = loginDetails.getUser();

        if (user.getPhone() != null) {
            model.addAttribute("pageTitle", "Needlers - Update Phone");
            model.addAttribute("formTitle", "Update Phone");
            model.addAttribute("placeholder", user.getPhone());
        } else {
            model.addAttribute("pageTitle", "Needlers - Add Phone");
            model.addAttribute("formTitle", "Add New Phone");
            model.addAttribute("placeholder", "Phone Number");
        }
        model.addAttribute("object", "PHONE");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @GetMapping("/info/update/email")
    public String updateEmail(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        model.addAttribute("pageTitle", "Needlers - Update Email");
        model.addAttribute("formTitle", "Update Email");
        model.addAttribute("placeholder", loginDetails.getUser().getEmail());
        model.addAttribute("object", "EMAIL");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @GetMapping("/info/update/address")
    public String updateAddress(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        User user = loginDetails.getUser();

        if (user.getAddress() != null) {
            model.addAttribute("pageTitle", "Needlers - Update Address");
            model.addAttribute("formTitle", "Update Address");
            model.addAttribute("placeholder", user.getAddress());
        } else {
            model.addAttribute("pageTitle", "Needlers - Add Address");
            model.addAttribute("formTitle", "Add New Address");
            model.addAttribute("placeholder", "Address");
        }
        model.addAttribute("object", "ADDRESS");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @PostMapping("/info/update")
    public String updatePhone(@AuthenticationPrincipal LoginDetails loginDetails,
                              @ModelAttribute("user") User info) {

        User user = loginDetails.getUser();

        if (info.getPhone() != null) {
            user.setPhone(info.getPhone());
        } else if (info.getEmail() != null) {
            user.setEmail(info.getEmail());
        } else {
            user.setAddress(info.getAddress());
        }

        userService.update(user, user.getPassword());

        return "redirect:/profile";
    }

}
