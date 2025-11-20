package com.sakib.librarymanagementsystem.modal;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private String bookName;
    private String bookTitle;
    private String authorName;
    private String bookPublisher;
    private String bookDescription;
    private String imageName;
    private int availableQuantity;
    private int bookId;
    private MultipartFile image;

    public Book(String bookName, String bookTitle , String authorName, String bookPublisher, String bookDescription,    int availableQuantity) {
        this.bookName = bookName;
        this.availableQuantity = availableQuantity;
        this.bookDescription = bookDescription;
        this.bookPublisher = bookPublisher;
        this.authorName = authorName;
        this.bookTitle = bookTitle;
    }

    public Book( int bookId , String bookName, String bookTitle, String authorName, String bookPublisher,int availableQuantity, String imageName ,String bookDescription) {
        this.bookName = bookName;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookPublisher = bookPublisher;
        this.bookDescription = bookDescription;
        this.imageName = imageName;
        this.availableQuantity = availableQuantity;
        this.bookId = bookId;
    }
}
