package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.BookDao;
import com.example.hcwtest.dao.custom.TransactionDao;
import com.example.hcwtest.dao.custom.UserDao;
import com.example.hcwtest.dto.UserDto;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.Transaction;
import com.example.hcwtest.entity.User;


import java.time.LocalDate;
import java.util.HashMap;

public class UserBoImpl implements UserBo {

    UserDao userDao = (UserDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    BookDao bookDao = (BookDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);


    TransactionDao transactionDao = (TransactionDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);
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
        userDao.save(new User(userDto.getUserId(),userDto.getUsername(),userDto.getEmail(),userDto.getPassword()));
    }

    @Override
    public boolean updateNewCredential(String id, String password, UserDto dto) {
        System.out.println("dto.getUsername() : " +dto.getUsername());
        System.out.println("dto.getPassword() : "+ dto.getPassword());
        boolean flag = userDao.update(id,password,new User(dto.getUsername(), dto.getPassword()));
        System.out.println("flag : "+flag);
        return flag;
    }

    @Override
    public String generateId() {

        String nextId = userDao.getNextId();
        return nextId;

    }

    @Override
    public String getUserId(String uname, String password) {
        return userDao.getUserId(uname,password);
    }
    @Override
    public boolean addTransaction(String bookId, String username) {

        String tId = transactionDao.getNextTId();

        Book search = bookDao.search(bookId);

        User searched = userDao.search(username);
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusDays(7);

        Transaction newLog = new Transaction(tId, search, searched,
                currentDate,
                expirationDate,
                false);
        try {
            transactionDao.save(newLog);
            bookDao.updateStatusNo(search);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
