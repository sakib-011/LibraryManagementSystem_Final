package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.PurchaseBook;

import java.time.LocalDate;

public record PurchaseBookDto(Integer bookId, Integer  vendorId ,
                              LocalDate purchaseDate , Integer quantity ,
                              Double pricePerBook , String comment ) {

    public PurchaseBook toPurchaseBook(){
        return new PurchaseBook(bookId , vendorId , purchaseDate , quantity , pricePerBook , comment);
    }
}
