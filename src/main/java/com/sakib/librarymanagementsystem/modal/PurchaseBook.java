package com.sakib.librarymanagementsystem.modal;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBook {
    private int bookId;
    private int vendorId;
    private LocalDate purchaseDate;
    private int  quantity;
    private double pricePerBook;
    private String comment;
    private int id;
    private String book_name;
    private String vendor_name;

    public PurchaseBook(int bookId, int vendorId, LocalDate purchaseDate, Integer quantity,
                        Double pricePerBook, String comment) {
            this.bookId = bookId;
            this.vendorId = vendorId;
            this.purchaseDate = purchaseDate;
            this.quantity = quantity;
            this.pricePerBook = pricePerBook;
            this.comment = comment;

    }
}
