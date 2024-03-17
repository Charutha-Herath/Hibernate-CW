package com.example.hcwtest.dto;

import java.time.LocalDate;

public class TransactionDto {

    private String transactionId;
    private String book_name;
    private String username;

    private String userId;
    private LocalDate borrowed_date;
    private LocalDate return_date;
    private boolean status;

    public TransactionDto() {
    }

    public TransactionDto(String transactionId, String book_name, String userId, LocalDate borrowed_date, LocalDate return_date, boolean status) {
        this.transactionId = transactionId;
        this.book_name = book_name;
        this.userId = userId;
        this.borrowed_date = borrowed_date;
        this.return_date = return_date;
        this.status = status;
    }
    public TransactionDto(String transactionId, String book_name, LocalDate borrowed_date, LocalDate return_date, boolean status) {
        this.transactionId = transactionId;
        this.book_name = book_name;
        this.borrowed_date = borrowed_date;
        this.return_date = return_date;
        this.status = status;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "TransactionDto{" +
                "transactionId='" + transactionId + '\'' +
                ", book_name='" + book_name + '\'' +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", borrowed_date=" + borrowed_date +
                ", return_date=" + return_date +
                ", status=" + status +
                '}';
    }
}
