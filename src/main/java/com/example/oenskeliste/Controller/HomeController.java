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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String register(@ModelAttribute User user, HttpSession session){
        if(userService.register(user)){
            user = userService.setLoggedInUser(user); //Hvis registreringen er vellykket, sætter den brugeren som
            session.setAttribute("user",user);     //den aktive bruger ved hjælp af userService.setLoggedInUser(user).
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
    public String createList(@ModelAttribute WList WList, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        //opretter ønskelisten i databasen:
        WListService.createList(user, WList);
        currentWList = WListService.setCurrentList(WList,user);

        //henter navnet på ønskelisten fra DB så det kan displayes for brugeren:
        WList wListForName = WListService.fetchListForName(currentWList.getList_id());
        model.addAttribute("list_name",wListForName.getList_name());

        return "home/editList";
    }

    @PostMapping("/addWish")
    public String addWish(@ModelAttribute Wish wish, Model model, HttpSession session){
        //henter user_id fra sessionen og tilføjer det til ønsket:
        User user = (User) session.getAttribute("user");
        wish.setUser_id(user.getUser_id());

        //setter list_id på ønsket:
        wish.setList_id(currentWList.getList_id());

        //tilføjer ønsket til DB
        wishService.addWish(wish);

        //tilføjer ønskerne fra DB til model så det kan displayes på html'en
        List<Wish> wishes = wishService.fetchList(currentWList.getList_id());
        model.addAttribute("wishes",wishes);

        //henter navnet fra listen på DB så det kan displayes
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
    public String editWishlist(@PathVariable("list_id")int list_id, Model model, HttpSession session){

        session.getAttribute("user");



        WList wList = WListService.fetchListForName(list_id);
        model.addAttribute("list_name",wList.getList_name());

        List<Wish> wishes = wishService.fetchList(list_id);
        model.addAttribute("wishes",wishes);

        currentWList = wList;

        return "home/editList";
    }



    @PostMapping("/logOut")
    public String logOut(HttpSession session){
        session.invalidate();
        return "home/index";
    }

}
