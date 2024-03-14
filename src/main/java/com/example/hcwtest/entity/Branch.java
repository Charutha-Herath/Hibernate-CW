package com.example.hcwtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Branch {
@Id
    private String branchId;

    private String name;

    private String manager;

    private String book_total;

    public Branch() {

    }

    public Branch(String branchId, String name, String manager, String book_total) {
        this.branchId = branchId;
        this.name = name;
        this.manager = manager;
        this.book_total = book_total;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getBook_total() {
        return book_total;
    }

    public void setBook_total(String book_total) {
        this.book_total = book_total;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId='" + branchId + '\'' +
                ", name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", book_total='" + book_total + '\'' +
                '}';
    }
}
