package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SignInController {
    @GetMapping("/signIn")
    public String getSignInPage(@RequestParam(name = "error", required = false) String error, ModelMap map) {
        if(error != null)  map.put("error", error);
        return "sign_in";
    }
}
