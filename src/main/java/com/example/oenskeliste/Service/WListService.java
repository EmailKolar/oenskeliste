package com.example.oenskeliste.Service;


import com.example.oenskeliste.Model.WList;
import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Repository.WListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WListService {


    @Autowired
    WListRepo WListRepo;


    public void createList(User user, WList WList){
        WListRepo.createList(user, WList);
    }

    public WList setCurrentList(WList WList, User user){
        return WListRepo.setCurrentList(WList,user);
    }

}
