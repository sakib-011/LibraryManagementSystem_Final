package com.sakib.librarymanagementsystem.modal;


import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    private int id ;
    private int bookId ;
    private int quantity ;
    private double amount ;
    private String bookTitle ;
    private String submissionType ;
    private String subscriptionType ;
    private LocalDate date;

    public Cart(int bookId, int quantity, double amount, String bookName, String submissionType, String subscriptionType , LocalDate date) {
        this.bookId = bookId;
        this.quantity =quantity;
        this.amount =amount;
        this.bookTitle = bookName;
        this.submissionType =submissionType;
        this.subscriptionType =subscriptionType;
        this.date = date;
    }
}
