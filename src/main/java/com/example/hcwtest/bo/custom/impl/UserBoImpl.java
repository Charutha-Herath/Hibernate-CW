package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.BookDao;
import com.example.hcwtest.dao.custom.TransactionDao;
import com.example.hcwtest.dao.custom.UserDao;
import com.example.hcwtest.dto.BranchDto;
import com.example.hcwtest.dto.TransactionDto;
import com.example.hcwtest.dto.UserDto;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.Branch;
import com.example.hcwtest.entity.Transaction;
import com.example.hcwtest.entity.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        LocalDate expirationDate = currentDate.plusDays(14);

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

    @Override
    public List<UserDto> getAllUsers() {
        ArrayList<User> users = userDao.getAll();

        ArrayList<UserDto> dtoList = new ArrayList<>();

        for (User user: users) {
            dtoList.add(new UserDto(user.getUserId(),user.getUsername(),user.getEmail(),10));
        }

        return dtoList;
    }

    @Override
    public List<TransactionDto> getAllTransactionsForThisUser(String text) {
        List<Transaction> allFor = transactionDao.getAll(text);
        List<TransactionDto> loglist = new ArrayList<>();
        for(Transaction dto : allFor){
            loglist.add(
                    new TransactionDto(
                            dto.getTransactionId(),
                            dto.getBook().getBookId(),
                            dto.getBorrowed_date(),
                            dto.getReturn_date(),
                            dto.isStatus()
                    )
            );
        }
        return loglist;

    }

    @Override
    public String generateAdminId() {
        String nextId = userDao.getNextAdminId();
        return nextId;

    }

    @Override
    public void saveAdmin(UserDto userDto) {
        userDao.saveAdmin(new User(userDto.getUserId(),userDto.getUsername(),userDto.getEmail(),userDto.getPassword()));

    }

    /*@Override
    public String getNewUsername(String id) {
        return null;
    }*/
}
