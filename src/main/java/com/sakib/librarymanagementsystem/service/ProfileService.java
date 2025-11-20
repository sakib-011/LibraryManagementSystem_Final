package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Profile;
import com.sakib.librarymanagementsystem.serviceInterface.ProfileInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class ProfileService implements ProfileInterface {
    @Override
    public boolean addNewProfile(Profile profile) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "INSERT INTO profile (name, email, phone_number) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getPhoneNumber());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProfile(Profile profile) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "UPDATE profile SET name=?, email=?, phone_number=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getPhoneNumber());
            ps.setInt(4, profile.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProfile(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "DELETE FROM profile WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Profile getProfile(int id) {
        Profile profile = null;
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM profile WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile = new Profile(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getDate("created_at").toLocalDate()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    @Override
    public ArrayList<Profile> getAllProfiles() {
        ArrayList<Profile> list = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM profile";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Profile(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
