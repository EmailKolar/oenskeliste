package com.example.oenskeliste.Repository;

import java.util.List;

import com.example.oenskeliste.Model.WList;
import com.example.oenskeliste.Model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepo {

    @Autowired
    JdbcTemplate template;

    public void addWish(Wish w){
        String sql = "INSERT INTO wishlist.wish (wish_id, user_id, list_id, wish_name, wish_desc, wish_link, " +
                "wish_price, reserved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,w.getWish_id(),w.getUser_id(),w.getList_id(),w.getWish_name(),w.getWish_desc(),
        w.getWish_link(),w.getWish_price(),w.isReserved());

    }





    public void editWish(){

    }
    public void deleteWish(){

    }

    public List<Wish> fetchList(WList list){
        String sql = "SELECT * FROM wishlist.wish WHERE list_id = ?";
        RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);
        return template.query(sql,rowMapper,list.getList_id());


    }



}
