package com.example.loanapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String redirectToApiDocs() {
        //default page of the api returns the swagger page
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
