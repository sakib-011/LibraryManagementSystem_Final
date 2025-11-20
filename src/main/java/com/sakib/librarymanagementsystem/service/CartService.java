package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Cart;
import com.sakib.librarymanagementsystem.serviceInterface.CartServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CartService implements CartServiceInterface {

    @Override
    public boolean addCart(Cart cart) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO carts (book_id, quantity, amount, book_title, submission_type, subscription_type, date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cart.getBookId());
            statement.setInt(2, cart.getQuantity());
            statement.setDouble(3, cart.getAmount());
            statement.setString(4, cart.getBookTitle());
            statement.setString(5, cart.getSubmissionType());
            statement.setString(6, cart.getSubscriptionType());
            statement.setDate(7, Date.valueOf(cart.getDate()));

            int rows = statement.executeUpdate();
            statement.close();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCart(Cart cart) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE carts SET quantity = ?, amount = ?, submission_type = ?, subscription_type = ?, date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, cart.getQuantity());
            statement.setDouble(2, cart.getAmount());
            statement.setString(3, cart.getSubmissionType());
            statement.setString(4, cart.getSubscriptionType());
            statement.setDate(5, Date.valueOf(cart.getDate()));
            statement.setInt(6, cart.getId());

            int rows = statement.executeUpdate();
            statement.close();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCart(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM carts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rows = statement.executeUpdate();
            statement.close();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cart getCart(int id) {
        Cart cart = new Cart();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM carts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                cart.setId(rs.getInt("id"));
                cart.setBookId(rs.getInt("book_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setAmount(rs.getDouble("amount"));
                cart.setBookTitle(rs.getString("book_title"));
                cart.setSubmissionType(rs.getString("submission_type"));
                cart.setSubscriptionType(rs.getString("subscription_type"));
                cart.setDate(rs.getDate("date").toLocalDate());

                return cart;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Cart> getAllCarts() {
        ArrayList<Cart> carts = new ArrayList<>();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM carts";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();

                cart.setId(rs.getInt("id"));
                cart.setBookId(rs.getInt("book_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setAmount(rs.getDouble("amount"));
                cart.setBookTitle(rs.getString("book_title"));
                cart.setSubmissionType(rs.getString("submission_type"));
                cart.setSubscriptionType(rs.getString("subscription_type"));
                cart.setDate(rs.getDate("date").toLocalDate());

                carts.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carts;
    }
}
