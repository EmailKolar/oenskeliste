package com.example.oenskeliste.Controller;

import com.example.oenskeliste.Model.List;
import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.ListService;
import com.example.oenskeliste.Service.UserService;
import com.example.oenskeliste.Service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    ListService listService;
    @Autowired
    WishService wishService;

    static User loggedInUser;
    static List currentList;

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

    @PostMapping("/loadGuest")
    public String goToGuest(){
        return "home/guest";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){//TODO find ud af hvordan vi gemmer dette user obj
        if(userService.register(user)){
            loggedInUser = userService.setLoggedInUser(user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user){
        if(userService.login(user)){
            loggedInUser = userService.setLoggedInUser(user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

    @PostMapping("/listInitPage")
    public String listInitPage(){
        return "home/createList";
    }

    @PostMapping("/createList")
    public String createList(@ModelAttribute List list){
        listService.createList(loggedInUser,list);
        currentList = listService.setCurrentList(list,loggedInUser);
        return "home/editList";
    }

    @PostMapping("/addWish")
    public String addWish(@ModelAttribute Wish wish){
        wish.setUser_id(loggedInUser.getUser_id());
        wish.setList_id(currentList.getList_id());
        wishService.addWish(wish);
        return "home/editList";
    }

}
