package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.TransactionBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.TransactionDao;
import com.example.hcwtest.dto.TransactionDto;
import com.example.hcwtest.entity.Transaction;

import java.util.ArrayList;
import java.util.List;



public class TransactionBoImpl implements TransactionBo {

    TransactionDao transactionDao = (TransactionDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);

    @Override
    public List<TransactionDto> getAllTransactions() {

        List<Transaction> allFor = transactionDao.getAllTrans();
        List<TransactionDto> loglist = new ArrayList<>();
        for(Transaction entity : allFor){
            System.out.println("userId getAllTrans : "+entity.getUser().getUserId());
            loglist.add(
                    new TransactionDto(
                            entity.getTransactionId(),
                            entity.getBook().getBookId(),
                            entity.getUser().getUserId(),
                            entity.getBorrowed_date(),
                            entity.getReturn_date(),
                            entity.isStatus()
                    )
            );
        }
        return loglist;
    }

    @Override
    public List<TransactionDto> getOverDueUsers() {
        List<Transaction> allFor = transactionDao.getOverDues();
        List<TransactionDto> loglist = new ArrayList<>();
        for(Transaction entity : allFor){
            System.out.println("userId getAllTrans : "+entity.getUser().getUserId());
            loglist.add(
                    new TransactionDto(
                            entity.getTransactionId(),
                            entity.getBook().getBookId(),
                            entity.getUser().getUserId(),
                            entity.getBorrowed_date(),
                            entity.getReturn_date(),
                            entity.isStatus()
                    )
            );
        }
        return loglist;

    }
}
