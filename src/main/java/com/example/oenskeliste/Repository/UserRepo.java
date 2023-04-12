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
    public void register(User user) {
        //TODO TJEK OM BRUGER ER OPRETTET I FORVEJEN!!!
        String sql = "INSERT INTO wishlist.user (user_id, user_name, password) " +
                "VALUES (?, ?, ?)";
        template.update(sql, user.getUser_id(), user.getUser_name(), user.getPassword());
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

}
