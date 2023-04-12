package com.example.oenskeliste.Repository;

import com.example.oenskeliste.Model.List;
import com.example.oenskeliste.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ListRepo {


    @Autowired
    JdbcTemplate template;



    public void createList(User user, List list){
        String sql = "INSERT INTO wishlist.list (list_id, user_id, list_name) VALUES (?, ?, ?)";
        System.out.println(user.getUser_id());
        template.update(sql, list.getList_id(),user.getUser_id(),list.getList_name());
    }

    public List setCurrentList(List list, User user){
        String sql = "SELECT * FROM wishlist.list WHERE list_name = ? AND user_id = ?";

        RowMapper<List> rm = new BeanPropertyRowMapper<>(List.class);
        List currentList = template.queryForObject(sql,rm,list.getList_name(),user.getUser_id());

        return currentList;

    }


}
