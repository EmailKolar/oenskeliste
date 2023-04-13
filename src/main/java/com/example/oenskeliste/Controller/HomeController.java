package com.example.oenskeliste.Controller;

import com.example.oenskeliste.Model.WList;
import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.WListService;
import com.example.oenskeliste.Service.UserService;
import com.example.oenskeliste.Service.WishService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    WListService WListService;
    @Autowired
    WishService wishService;

    static User loggedInUser;
    static WList currentWList;

    @GetMapping("/")
    public String index(HttpSession session){
        System.out.println(session.getAttribute("username"));
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
        return "home/loggedIn";
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
    public String login(@ModelAttribute User user, HttpSession httpSession){
        if(userService.login(user)){

            user = userService.setLoggedInUser(user);
            httpSession.setAttribute("user",user);
//            User seshUser = (User) httpSession.getAttribute("user");
//            System.out.println(seshUser.getUser_id());
            return "home/loggedIn";
        }else {
            System.out.println("DEBUG: login credentials not correct");
            return "home/errorPage";
        }
    }

    @PostMapping("/listInitPage")
    public String listInitPage(){
        return "home/createList";
    }

    @PostMapping("/createList")
    public String createList(@ModelAttribute WList WList){
        WListService.createList(loggedInUser, WList);
        currentWList = WListService.setCurrentList(WList,loggedInUser);
        return "home/editList";
    }

    @PostMapping("/addWish")
    public String addWish(@ModelAttribute Wish wish, Model model){
        wish.setUser_id(loggedInUser.getUser_id());
        wish.setList_id(currentWList.getList_id());
        wishService.addWish(wish);

        List<Wish> wishes = wishService.fetchList(currentWList);
        model.addAttribute("wishes",wishes);

        return "home/editList";
    }

}
