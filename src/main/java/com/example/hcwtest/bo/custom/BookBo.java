package com.example.hcwtest.bo.custom;

import com.example.hcwtest.bo.SuperBo;
import com.example.hcwtest.dto.BookDto;

import java.util.List;

public interface BookBo extends SuperBo {

    void addBook(BookDto bookDto);

    List<BookDto> getAllBooks();

    String generateId();

    boolean bookUpdate(BookDto bookDto);

    boolean deleteBook(String bookId);
}
