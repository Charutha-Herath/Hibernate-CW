package com.example.hcwtest.bo.custom;

import com.example.hcwtest.bo.SuperBo;
import com.example.hcwtest.dto.TransactionDto;

import java.util.List;

public interface TransactionBo extends SuperBo {

    List<TransactionDto> getAllTransactions();

    List<TransactionDto> getOverDueUsers();
}
