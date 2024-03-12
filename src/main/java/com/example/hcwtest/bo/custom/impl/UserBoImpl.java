package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.UserDao;
import com.example.hcwtest.dto.UserDto;
import com.example.hcwtest.entity.User;

import java.util.HashMap;

public class UserBoImpl implements UserBo {

    UserDao userDao = (UserDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean checkCredential(UserDto userDto){
        HashMap<String,String> allUsers = userDao.checker(new User(userDto.getUsername(),userDto.getPassword()));
        if (allUsers.containsKey(userDto.getUsername())) {
            return allUsers.get(userDto.getUsername()).equals(userDto.getPassword());
        }else {
            return false;
        }
    }

    @Override
    public void saveUser(UserDto userDto) {
        userDao.save(new User(userDto.getUsername(),userDto.getEmail(),userDto.getPassword()));
    }
}
