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

//    static User loggedInUser;
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
    public String register(@ModelAttribute User user, HttpSession session){//TODO måske ikke login ved register
        if(userService.register(user)){
            user = userService.setLoggedInUser(user);
            session.setAttribute("user",user);
            return "home/loggedIn";
        }else {
            return "home/errorPage";
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
    public String createList(@ModelAttribute WList WList, HttpSession session){
        User user = (User) session.getAttribute("user");

        WListService.createList(user, WList);
        currentWList = WListService.setCurrentList(WList,user);
        return "home/editList";
    }

    @PostMapping("/addWish")
    public String addWish(@ModelAttribute Wish wish, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        wish.setUser_id(user.getUser_id());

        wish.setList_id(currentWList.getList_id());
        wishService.addWish(wish);

        List<Wish> wishes = wishService.fetchList(currentWList.getList_id());
        model.addAttribute("wishes",wishes);

        WList wList = WListService.fetchListForName(currentWList.getList_id());
        model.addAttribute("list_name",wList.getList_name());

        return "home/editList";
    }
    @PostMapping("/inputListId")
    public String inputListId(@ModelAttribute WList list, Model model){


        List<Wish> wishes = wishService.fetchList(list.getList_id());
        model.addAttribute("wishes",wishes);
        return "home/viewList";
    }

    @PostMapping("/showList")
    public String showListPage( ){

        return "home/viewList";
    }

    @PostMapping("/viewMyWishlists")
    public String viewMyWishlists(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        List<WList> lists = WListService.fetchLists(user);
        model.addAttribute("lists", lists);
        return "home/showMyLists";
    }
    @GetMapping("/editWishlist/{list_id}")
    public String editWishlist(@PathVariable("list_id")int list_id, HttpSession session, Model model){
        System.out.println(list_id);

        WList wList = WListService.fetchListForName(list_id);
        model.addAttribute("list_name",wList.getList_name());

        List<Wish> wishes = wishService.fetchList(list_id);
        model.addAttribute("wishes",wishes);
        return "home/editList";
    }


}
