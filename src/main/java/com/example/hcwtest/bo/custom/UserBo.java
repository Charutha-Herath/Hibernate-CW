package com.example.hcwtest.bo.custom;

import com.example.hcwtest.bo.SuperBo;
import com.example.hcwtest.dto.TransactionDto;
import com.example.hcwtest.dto.UserDto;

import java.util.List;

public interface UserBo extends SuperBo {
    boolean checkCredential(UserDto userDto);

    void saveUser(UserDto userDto);

    boolean updateNewCredential(String username, String password, UserDto userDto);

    String generateId();

    String getUserId(String uname,String password);

    boolean addTransaction(String bookId, String username);

    List<UserDto> getAllUsers();

    List<TransactionDto> getAllTransactionsForThisUser(String text);

    String generateAdminId();

    void saveAdmin(UserDto userDto);

    /*String getNewUsername(String id);*/
}
