package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import com.sakib.librarymanagementsystem.serviceInterface.BookAllotmentHistoryServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class BookAllotmentHistoryService implements BookAllotmentHistoryServiceInterface {

    @Override
    public boolean addBookAllotment(BookAllotmentHistory allotment) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO book_allotment_history (book_id, quantity, amount, book_title, submission_type, subscription_type, date, email, studentname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, allotment.getBookId());
            statement.setInt(2, allotment.getQuantity());
            statement.setDouble(3, allotment.getAmount());
            statement.setString(4, allotment.getTitle());
            statement.setString(5, allotment.getSubmissionType());
            statement.setString(6, allotment.getSubscriptionType());
            statement.setDate(7, Date.valueOf(allotment.getDate()));
            statement.setString(8, allotment.getEmail());
            statement.setString(9, allotment.getStudentName());

            int rowAffected = statement.executeUpdate();
            statement.close();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBookAllotment(BookAllotmentHistory allotment) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE book_allotment_history SET book_id=?, quantity=?, amount=?, book_title=?, submission_type=?, subscription_type=?, date=?, email=?, studentname=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, allotment.getBookId());
            statement.setInt(2, allotment.getQuantity());
            statement.setDouble(3, allotment.getAmount());
            statement.setString(4, allotment.getTitle());
            statement.setString(5, allotment.getSubmissionType());
            statement.setString(6, allotment.getSubscriptionType());
            statement.setDate(7, Date.valueOf(allotment.getDate()));
            statement.setString(8, allotment.getEmail());
            statement.setString(9, allotment.getStudentName());
            statement.setInt(10, allotment.getId());

            int rowAffected = statement.executeUpdate();
            statement.close();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBookAllotment(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM book_allotment_history WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowAffected = statement.executeUpdate();
            statement.close();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BookAllotmentHistory getBookAllotment(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM book_allotment_history WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                BookAllotmentHistory allotment = new BookAllotmentHistory();
                allotment.setId(rs.getInt("id"));
                allotment.setBookId(rs.getInt("book_id"));
                allotment.setQuantity(rs.getInt("quantity"));
                allotment.setAmount(rs.getDouble("amount"));
                allotment.setTitle(rs.getString("book_title"));
                allotment.setSubmissionType(rs.getString("submission_type"));
                allotment.setSubscriptionType(rs.getString("subscription_type"));
                allotment.setDate(rs.getDate("date").toLocalDate());
                allotment.setEmail(rs.getString("email"));
                allotment.setStudentName(rs.getString("studentname"));

                rs.close();
                statement.close();
                return allotment;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<BookAllotmentHistory> getAllBookAllotments() {
        ArrayList<BookAllotmentHistory> list = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM book_allotment_history";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookAllotmentHistory allotment = new BookAllotmentHistory();
                allotment.setId(rs.getInt("id"));
                allotment.setBookId(rs.getInt("book_id"));
                allotment.setQuantity(rs.getInt("quantity"));
                allotment.setAmount(rs.getDouble("amount"));
                allotment.setTitle(rs.getString("book_title"));
                allotment.setSubmissionType(rs.getString("submission_type"));
                allotment.setSubscriptionType(rs.getString("subscription_type"));
                allotment.setDate(rs.getDate("date").toLocalDate());
                allotment.setEmail(rs.getString("email"));
                allotment.setStudentName(rs.getString("studentname"));

                list.add(allotment);
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
