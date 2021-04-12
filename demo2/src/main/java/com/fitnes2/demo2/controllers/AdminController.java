package com.fitnes2.demo2.controllers;

import com.fitnes2.demo2.model.Model_stat;
import com.fitnes2.demo2.model.User;
import com.fitnes2.demo2.repositoryes.UserRepository;
import com.fitnes2.demo2.service.AdminService;
import com.fitnes2.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService ad_userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        System.out.println(userId);
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    @GetMapping("/admin/{userId}/edit")
    public String userEdit(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("detectUser", userService.findUserById(userId));
        return "admin_edit";
    }

    @PostMapping("/admin/{userId}/edit")
    public String editUser(@RequestParam(required = true, defaultValue = "") Long userId,
                           @RequestParam(required = true, defaultValue = "") String action,
                           @RequestParam String userName, @RequestParam String userPassword,
                           @RequestParam String userRole, Model model) {

        if (action.equals("edit")) {
            User user = userRepository.findById(userId).orElseThrow(ArithmeticException::new);
            user.setPassword(userPassword);
            user.setUsername(userName);
            System.out.println(userRole);
            ad_userService.ad_saveUser(user, userRole);
        }
        return "redirect:/admin";
    }
}

