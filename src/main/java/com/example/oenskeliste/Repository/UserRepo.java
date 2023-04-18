package com.example.oenskeliste.Repository;


import com.example.oenskeliste.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    @Autowired
    JdbcTemplate template;
    public boolean register(User user) {
        if(!UserExist(user)){
            String sql = "INSERT INTO wishlist.user (user_id, user_name, password) " +
                    "VALUES (?, ?, ?)";
            template.update(sql, user.getUser_id(), user.getUser_name(), user.getPassword());
            return true;
        }else {
            return false;
        }

    }

    public boolean UserExist(User user){
        String sql = "SELECT * FROM wishlist.user WHERE user_name = ?";
        RowMapper<User> rm = new BeanPropertyRowMapper<>(User.class);
        try{
            User u = template.queryForObject(sql, rm, user.getUser_name());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean login(User user) {
        String sql = "SELECT * FROM wishlist.user WHERE user_name = ? AND password = ?";
        RowMapper<User> rm = new BeanPropertyRowMapper<>(User.class);
        try{
            User u = template.queryForObject(sql, rm, user.getUser_name(), user.getPassword());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public User setLoggedInUser(User user){
        String sql = "SELECT * FROM wishlist.user WHERE user_name = ? AND password =?";
        RowMapper<User> rm = new BeanPropertyRowMapper<>(User.class);
        User loggedInUser = template.queryForObject(sql,rm,user.getUser_name(),user.getPassword());
        return loggedInUser;
    }

}
