package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.BookBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.BookDao;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookBoImpl implements BookBo {

    BookDao bookDao = (BookDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);


    @Override
    public void addBook(BookDto dto) {
        bookDao.save(new Book(dto.getBookId(), dto.getTitle(), dto.getAuthor(), dto.getGenre(), dto.getStatus()));
    }

    @Override
    public List<BookDto> getAllBooks() {
        ArrayList<Book> books = bookDao.getAll();

        ArrayList<BookDto> dtoList = new ArrayList<>();

        for (Book book: books) {
            dtoList.add(new BookDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getStatus()));
        }

        return dtoList;
    }

    @Override
    public String generateId() {
        return bookDao.getNextId();
    }

    @Override
    public boolean bookUpdate(BookDto dto) {
        return bookDao.update(new Book(dto.getBookId(), dto.getTitle(), dto.getAuthor(), dto.getGenre(), dto.getStatus()));
    }

    @Override
    public boolean deleteBook(String bookId) {
        return bookDao.delete(bookId);
    }
}
