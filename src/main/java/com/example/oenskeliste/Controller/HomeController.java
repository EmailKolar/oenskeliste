package com.example.oenskeliste.Controller;

import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;


    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    @PostMapping("/registerbutton")
    public String goToRegisterPage(){
        return "home/register";
    }
    @PostMapping("/loginbutton")
    public String goToLoginPage(){
        return "home/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){//TODO find ud af hvordan vi gemmer dette user obj
        userService.register(user);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user){
        if(userService.login(user)){
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }


}
