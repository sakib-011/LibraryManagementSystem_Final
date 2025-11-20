package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.serviceInterface.BookServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class BookService  implements BookServiceInterface {

    private final ArrayList<Book> books = new ArrayList<>();


    @Override
    public boolean addNewBook(Book book) {
//        book.setBookId(books.size()+1);
//        books.add(book);
//        return  true;

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO books (book_name, book_title, author_name, publisher, available_quantity, book_image, book_description) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1 , book.getBookName());
            statement.setString(2 , book.getBookTitle());
            statement.setString(3 , book.getAuthorName());
            statement.setString(4 , book.getBookPublisher());
            statement.setInt(5 , book.getAvailableQuantity());
            statement.setString(6 , book.getImageName());
            statement.setString(7 , book.getBookDescription());

            int rowAffected = statement.executeUpdate();

            return rowAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean updateBook(Book book) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE books SET book_name=?, book_title=?, author_name=?, publisher=?, available_quantity=?, book_image=?, book_description=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, book.getBookName());
            statement.setString(2, book.getBookTitle());
            statement.setString(3, book.getAuthorName());
            statement.setString(4, book.getBookPublisher());
            statement.setInt(5, book.getAvailableQuantity());
            statement.setString(6, book.getImageName());
            statement.setString(7, book.getBookDescription());
            statement.setInt(8, book.getBookId());

            int rowAffected = statement.executeUpdate();
            statement.close();

            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBook(int id) {
        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM books  WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowAffected = statement.executeUpdate();

            return rowAffected>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public Book getBook(Long id) {

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM books  WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id.intValue());
            ResultSet file = statement.executeQuery();

            if(file.next()){
                String bookName = file.getString("book_name");
                String bookTitle = file.getString("book_title");
                String authorName = file.getString("author_name");
                String bookPublisher = file.getString("publisher");
                int availableQuantity = file.getInt("available_quantity");
                String imageName = file.getString("book_image");
                String bookDescription = file.getString("book_description");

                return  new Book( id.intValue() , bookName, bookTitle, authorName, bookPublisher, availableQuantity, imageName , bookDescription);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book getBook(String bookName) {
        return books.stream().filter(b-> b.getBookName().equals(bookName)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Book> getAllBook() {

        ArrayList<Book> books = new ArrayList<>();
//        String query = "INSERT INTO books (book_name, book_title,
//        author_name, publisher, available_quantity, book_image,
//        book_description) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet file = statement.executeQuery();

            while(file.next()){
                int bookId = file.getInt("id");
                String bookName = file.getString("book_name");
                String bookTitle = file.getString("book_title");
                String authorName = file.getString("author_name");
                String bookPublisher = file.getString("publisher");
                int availableQuantity = file.getInt("available_quantity");
                String imageName = file.getString("book_image");
                String bookDescription = file.getString("book_description");

                books.add(new Book( bookId , bookName, bookTitle, authorName, bookPublisher, availableQuantity, imageName , bookDescription));
            }



        } catch (SQLException e) {
            e.printStackTrace();

        }

        return books;
    }
}
