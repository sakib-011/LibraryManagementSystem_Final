package com.sakib.librarymanagementsystem.serviceInterface;


import com.sakib.librarymanagementsystem.modal.PurchaseBook;
import java.util.ArrayList;


public interface PurchaseBookServiceInterface {
    boolean addPurchaseBook(PurchaseBook purchaseBook);
    boolean updatePurchaseBook(PurchaseBook purchaseBook, int id);
    boolean deletePurchaseBook(int id);
    PurchaseBook getPurchaseBook(int id);
    ArrayList<PurchaseBook> getAllPurchaseBooks();
}