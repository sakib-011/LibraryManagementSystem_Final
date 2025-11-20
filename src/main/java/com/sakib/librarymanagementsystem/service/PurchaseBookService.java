package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.PurchaseBook;
import com.sakib.librarymanagementsystem.serviceInterface.PurchaseBookServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class PurchaseBookService implements PurchaseBookServiceInterface {

    private BookService bookService;
    private VendorService  vendorService;

    public PurchaseBookService(BookService bookService , VendorService vendorService){
        this.bookService =bookService;
        this.vendorService =vendorService;
    }

    @Override
    public boolean addPurchaseBook(PurchaseBook purchaseBook) {
        try {

            String bookName = bookService.getBook((long)purchaseBook.getBookId()).getBookName();
            String vendorName =  vendorService.getVendor(purchaseBook.getVendorId()).getName();
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO purchase_book (book_id, vendor_id, purchase_date, quantity, price_per_book, comment , book_name , vendor_name) VALUES (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, purchaseBook.getBookId());
            statement.setInt(2, purchaseBook.getVendorId());
            statement.setDate(3, Date.valueOf(purchaseBook.getPurchaseDate()));
            statement.setInt(4, purchaseBook.getQuantity());
            statement.setDouble(5, purchaseBook.getPricePerBook());
            statement.setString(6, purchaseBook.getComment());
            statement.setString(7, bookName);
            statement.setString(8, vendorName);

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePurchaseBook(PurchaseBook purchaseBook, int id) {
        try {

            String bookName = bookService.getBook((long)purchaseBook.getBookId()).getBookName();
            String vendorName =  vendorService.getVendor(purchaseBook.getVendorId()).getName();

            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE purchase_book SET book_id=?, vendor_id=?, purchase_date=?, quantity=?, price_per_book=?, comment=? , book_name=? , vendor_name=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, purchaseBook.getBookId());
            statement.setInt(2, purchaseBook.getVendorId());
            statement.setDate(3, Date.valueOf(purchaseBook.getPurchaseDate()));
            statement.setInt(4, purchaseBook.getQuantity());
            statement.setDouble(5, purchaseBook.getPricePerBook());
            statement.setString(6, purchaseBook.getComment());
            statement.setString(7, bookName);
            statement.setString(8, vendorName);
            statement.setInt(9, id);

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePurchaseBook(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM purchase_book WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PurchaseBook getPurchaseBook(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM purchase_book WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new PurchaseBook(
                        result.getInt("book_id"),
                        result.getInt("vendor_id"),
                        result.getDate("purchase_date").toLocalDate(),
                        result.getInt("quantity"),
                        result.getDouble("price_per_book"),
                        result.getString("comment"),
                        result.getInt("id"),
                        result.getString("book_name"),
                        result.getString("vendor_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<PurchaseBook> getAllPurchaseBooks() {
        ArrayList<PurchaseBook> list = new ArrayList<>();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM purchase_book";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet file = statement.executeQuery();

            while (file.next()) {
                list.add(new PurchaseBook(
                        file.getInt("book_id"),
                        file.getInt("vendor_id"),
                        file.getDate("purchase_date").toLocalDate(),
                        file.getInt("quantity"),
                        file.getDouble("price_per_book"),
                        file.getString("comment"),
                        file.getInt("id"),
                        file.getString("book_name"),
                        file.getString("vendor_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
