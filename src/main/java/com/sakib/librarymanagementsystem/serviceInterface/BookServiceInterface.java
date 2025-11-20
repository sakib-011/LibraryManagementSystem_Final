package com.sakib.librarymanagementsystem.serviceInterface;


import com.sakib.librarymanagementsystem.modal.Book;

import java.util.ArrayList;

public interface BookServiceInterface {
    boolean addNewBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(int id);
    Book getBook(Long id);
    Book getBook(String bookName);
    ArrayList<Book> getAllBook();
}
