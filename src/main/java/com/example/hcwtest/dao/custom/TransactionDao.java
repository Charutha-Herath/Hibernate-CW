package com.example.hcwtest.dao.custom;

import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.entity.Transaction;

import java.util.List;

public interface TransactionDao extends SuperDao {

    String getNextTId();

    void save(Transaction newLog);

    List<Transaction> getAll(String text);

    List<Transaction> getAllTrans();

    List<Transaction> getOverDues();
}
