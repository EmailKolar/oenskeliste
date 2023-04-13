package com.example.oenskeliste.Service;

import com.example.oenskeliste.Model.WList;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    WishRepo wishRepo;

    public void addWish(Wish w){
        wishRepo.addWish(w);
    }


    public List<Wish> fetchList(WList wList){
        return wishRepo.fetchList(wList);
    }

}
