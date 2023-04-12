package com.example.oenskeliste.Service;


import com.example.oenskeliste.Model.List;
import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {


    @Autowired
    ListRepo listRepo;


    public void createList(User user, List list){
        listRepo.createList(user,list);
    }

    public List setCurrentList(List list, User user){
        return listRepo.setCurrentList(list,user);
    }

}
