package org.example.quizmaster.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/quiz")
    @PreAuthorize("hasRole('STUDENT')")
    public String quiz() {
        return "quiz";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('TEACHER')")
    public String admin() {
        return "admin";
    }
}
