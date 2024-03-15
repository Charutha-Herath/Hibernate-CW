package com.example.hcwtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
@Entity
public class Transaction {
    @Id
    private String transactionId;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;

    private LocalDate borrowed_date;
    private LocalDate return_date;
    private boolean status;

    public Transaction() {

    }

    public Transaction(String transactionId, Book book, User user, LocalDate borrowed_date, LocalDate return_date, boolean status) {
        this.transactionId = transactionId;
        this.book = book;
        this.user = user;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", book=" + book +
                ", user=" + user +
                ", borrowed_date=" + borrowed_date +
                ", return_date=" + return_date +
                ", status=" + status +
                '}';
    }
}
