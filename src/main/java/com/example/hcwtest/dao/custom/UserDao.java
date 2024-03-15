package com.example.hcwtest.dao.custom;

import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.entity.User;

import java.util.HashMap;

public interface UserDao extends SuperDao {

    HashMap<String, String> checker(User user);

    void save(User user);

    boolean update(String username, String password, User user);

    String getNextId();

    String getUserId(String uname, String password);

    User search(String username);
}
