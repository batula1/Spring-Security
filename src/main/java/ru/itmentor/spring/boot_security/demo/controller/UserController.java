package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserInfo(ModelMap model, Principal principal) {
        String login = principal.getName();
        List<User> users = userService.findByLogin(login);

        if (users.isEmpty()) {
            // Обработка ситуации, когда пользователя с таким логином нет
            return "error";
        }

        // Ваша логика обработки списка пользователей, например, выбор первого пользователя
        User user = users.get(0);

        model.addAttribute("user", user);
        return "user";
    }
}

