package com.example.hcwtest.dto.Tm;

import com.jfoenix.controls.JFXButton;

import java.time.LocalDate;

public class TransactionTm {
    private String transactionId;
    private String book_name;
    private String username;
    private LocalDate borrowed_date;
    private LocalDate return_date;
    private boolean status;

    private JFXButton mod;

    public TransactionTm() {

    }

    public TransactionTm(String transactionId, String book_name, String username, LocalDate borrowed_date, LocalDate return_date, boolean status, JFXButton mod) {
        this.transactionId = transactionId;
        this.book_name = book_name;
        this.username = username;
        this.borrowed_date = borrowed_date;
        this.return_date = return_date;
        this.status = status;
        this.mod = mod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBorrowed_date() {
        return borrowed_date;
    }

    public void setBorrowed_date(LocalDate borrowed_date) {
        this.borrowed_date = borrowed_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public JFXButton getMod() {
        return mod;
    }

    public void setMod(JFXButton mod) {
        this.mod = mod;
    }

    @Override
    public String toString() {
        return "TransactionTm{" +
                "transactionId='" + transactionId + '\'' +
                ", book_name='" + book_name + '\'' +
                ", username='" + username + '\'' +
                ", borrowed_date=" + borrowed_date +
                ", return_date=" + return_date +
                ", status=" + status +
                ", mod=" + mod +
                '}';
    }
}
