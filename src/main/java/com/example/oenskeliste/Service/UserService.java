package com.example.oenskeliste.Service;


import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public boolean register(User user){
        return userRepo.register(user);
    }

    public boolean login(User user){
        return userRepo.login(user);
    }





}
