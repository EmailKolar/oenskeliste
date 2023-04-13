package com.example.oenskeliste.Repository;

import com.example.oenskeliste.Model.WList;
import com.example.oenskeliste.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class WListRepo {


    @Autowired
    JdbcTemplate template;



    public void createList(User user, WList WList){
        String sql = "INSERT INTO wishlist.list (list_id, user_id, list_name) VALUES (?, ?, ?)";
        System.out.println(user.getUser_id());
        template.update(sql, WList.getList_id(),user.getUser_id(), WList.getList_name());
    }

    public WList setCurrentList(WList WList, User user){
        String sql = "SELECT * FROM wishlist.list WHERE list_name = ? AND user_id = ?";

        RowMapper<WList> rm = new BeanPropertyRowMapper<>(WList.class);
        WList currentWList = template.queryForObject(sql,rm, WList.getList_name(),user.getUser_id());

        return currentWList;

    }


}
