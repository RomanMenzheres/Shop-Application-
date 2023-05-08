package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.OrderService;
import com.example.shop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService= orderService;
    }

    @GetMapping
    public String profile(Model model, @AuthenticationPrincipal LoginDetails user) {

        model.addAttribute("user", userService.readById(user.getUser().getId()));
        model.addAttribute("orders", orderService.findNotOpenOrderByUser(user.getUser()));

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
        model.addAttribute("formTitle", "Введіть номер телефону");

        if (user.getPhone() != null) {
            model.addAttribute("pageTitle", "Needler's - Оновлення номера телефону");
            model.addAttribute("placeholder", user.getPhone());
        } else {
            model.addAttribute("pageTitle", "Needler's - Додавання номера телефону");
            model.addAttribute("placeholder", "Номер телефону");
        }
        model.addAttribute("object", "PHONE");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @GetMapping("/info/update/email")
    public String updateEmail(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        model.addAttribute("pageTitle", "Needler's - Оновлення email\'а");
        model.addAttribute("formTitle", "Введіть email");
        model.addAttribute("placeholder", loginDetails.getUser().getEmail());
        model.addAttribute("object", "EMAIL");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @GetMapping("/info/update/address")
    public String updateAddress(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        User user = loginDetails.getUser();
        model.addAttribute("formTitle", "Введіть адресу");

        if (user.getAddress() != null) {
            model.addAttribute("pageTitle", "Needler's - Оновлення адреси");
            model.addAttribute("placeholder", user.getAddress());
        } else {
            model.addAttribute("pageTitle", "Needler's - Додавання адреси");
            model.addAttribute("placeholder", "Адреса");
        }
        model.addAttribute("object", "ADDRESS");
        model.addAttribute("user", new User());

        return "update-info";
    }

    @PostMapping("/info/update")
    public String updatePhone(@AuthenticationPrincipal LoginDetails loginDetails,
                              @ModelAttribute("user") User info) {

        User user = loginDetails.getUser();

        if (info.getPhone() != null && !info.getPhone().equals("")) {
            user.setPhone(info.getPhone());
        } else if (info.getEmail() != null && !info.getEmail().equals("")) {
            user.setEmail(info.getEmail());
        } else if (info.getAddress() != null && !info.getAddress().equals("")){
            user.setAddress(info.getAddress());
        }

        userService.update(user, user.getPassword());

        return "redirect:/profile";
    }

}
