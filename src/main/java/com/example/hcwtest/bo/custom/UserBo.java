package com.example.hcwtest.bo.custom;

import com.example.hcwtest.bo.SuperBo;
import com.example.hcwtest.dto.UserDto;

public interface UserBo extends SuperBo {
    boolean checkCredential(UserDto userDto);

    void saveUser(UserDto userDto);

    boolean updateNewCredential(String username, String password, UserDto userDto);

    String generateId();

    String getUserId(String uname,String password);

}
