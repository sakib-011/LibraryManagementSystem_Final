package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Subscription;
import com.sakib.librarymanagementsystem.serviceInterface.SubscriptionInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class SubscriptionService implements SubscriptionInterface {


    @Override
    public boolean addNewSubscription(Subscription subscription) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "INSERT INTO subscription (title, amount, day) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, subscription.getTitle());
            ps.setDouble(2, subscription.getAmount());
            ps.setInt(3, subscription.getDays());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSubscription(Subscription subscription) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "UPDATE subscription SET title=?, amount=?, day=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, subscription.getTitle());
            ps.setDouble(2, subscription.getAmount());
            ps.setInt(3, subscription.getDays());
            ps.setInt(4, subscription.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSubscription(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "DELETE FROM subscription WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Subscription getSubscription(int id) {
        Subscription s = null;
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM subscription WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Subscription(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getInt("day")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Subscription getSubscription(String title) {
        Subscription s = null;
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM subscription WHERE title=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Subscription(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getInt("day")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }




    @Override
    public ArrayList<Subscription> getAllSubscription() {
        ArrayList<Subscription> list = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM subscription";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Subscription(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getInt("day")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
