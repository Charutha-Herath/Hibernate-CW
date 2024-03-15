package com.example.hcwtest.dto.Tm;

import com.jfoenix.controls.JFXButton;

public class BookTm {
    private String bookId;

    private String title;
    private String author;
    private String genre;
    private String status;

    private String branch;

    private JFXButton button;

    public BookTm() {

    }

    public BookTm(String bookId, String title, String author, String genre, String status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
    }

    public BookTm(String bookId, String title, String author, String genre, String status, String branch) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.branch = branch;
    }

    public BookTm(String bookId, String title, String author, String genre, String status, String branch, JFXButton button) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.branch = branch;
        this.button = button;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "BookTm{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", status='" + status + '\'' +
                ", branch='" + branch + '\'' +
                ", button=" + button +
                '}';
    }
}
