package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Book;
import org.springframework.web.multipart.MultipartFile;

public record BookDto(Integer bookId , String bookName , String bookTitle , String authorName , String bookPublisher , String bookDescription , Integer availableQuantity , MultipartFile image) {

//    String bookName, String bookTitle , String authorName, String bookPublisher, String bookDescription,  String imageName,  int availableQuantity

    public Book toBook(){
        return new Book(bookName , bookTitle, authorName, bookPublisher, bookDescription, availableQuantity);
    }
}
