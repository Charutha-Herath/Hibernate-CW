package com.example.hcwtest.dao.custom;

import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.entity.Transaction;

public interface TransactionDao extends SuperDao {

    String getNextTId();

    void save(Transaction newLog);
}
