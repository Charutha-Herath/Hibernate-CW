package com.example.hcwtest.dao.custom;

import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.entity.Book;

import java.util.ArrayList;
import java.util.List;

public interface BookDao extends SuperDao {

    void save(Book book);

    ArrayList<Book> getAll();

    String getNextId();

    boolean update(Book book);

    boolean delete(String bookId);

    Book search(String bookId);

    void updateStatusNo(Book search);
}
