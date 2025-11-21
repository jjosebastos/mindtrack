package br.com.fiap.mindtrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {


    @GetMapping({"/index", "/", "/home"})
    public String showIndex(){
        return "layout/base";
    }
}
