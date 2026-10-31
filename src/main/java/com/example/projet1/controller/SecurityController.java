package com.example.projet1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/pasautorise")
    public String pasautorisé()
      {
        return "pasautorise";
    }
    @GetMapping("/login")
    public String login(){
        return "login";}

}
