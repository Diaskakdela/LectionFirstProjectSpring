package org.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.step.model.User;
import org.step.security.Role;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/home")
public class MainController {

    private final UserService<User> userService;
    private final AuthoritiesService<User> authoritiesService;

    @Autowired
    public MainController(UserService<User> userService,
                          AuthoritiesService<User> authoritiesService) {
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/submit")
    public ModelAndView getRegistrationPage() {
        ModelAndView registration = new ModelAndView("submit");

        registration.addObject("value", "Hello, friend");

        return registration;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("value", "Hello, friend");

        return "login";
    }

    @PostMapping("/submit")
    public String registration(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "role", required = false, defaultValue = "user") String role
    ) {
        User user = new User(username, password);

        boolean isAdmin = false;

        if (role.equals("admin")) {
            isAdmin = true;
        }
        userService.save(user, isAdmin);

        return "index";
    }

}
