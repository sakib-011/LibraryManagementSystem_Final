package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Vendor;
import com.sakib.librarymanagementsystem.serviceInterface.VendorServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class VendorService implements VendorServiceInterface {

    @Override
    public boolean addNewVendor(Vendor vendor) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO vendors (name, company_name, phone_number, email, address) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, vendor.getName());
            statement.setString(2, vendor.getCompanyName());
            statement.setString(3, vendor.getPhone());
            statement.setString(4, vendor.getEmail());
            statement.setString(5, vendor.getAddress());

            int rowAffected = statement.executeUpdate();
            statement.close();

            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateVendor(Vendor vendor) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE vendors SET name = ?, company_name = ?, phone_number = ?, email = ?, address = ? WHERE vendor_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, vendor.getName());
            statement.setString(2, vendor.getCompanyName());
            statement.setString(3, vendor.getPhone());
            statement.setString(4, vendor.getEmail());
            statement.setString(5, vendor.getAddress());
            statement.setInt(6, vendor.getVendorId());

            int rowAffected = statement.executeUpdate();
            statement.close();

            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteVendor(int vendorId) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM vendors WHERE vendor_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, vendorId);

            int rowAffected = statement.executeUpdate();
            statement.close();

            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Vendor getVendor(int vendorId) {
        Vendor vendor = new Vendor();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM vendors WHERE vendor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, vendorId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                vendor.setVendorId(rs.getInt("vendor_id"));
                vendor.setName(rs.getString("name"));
                vendor.setCompanyName(rs.getString("company_name"));
                vendor.setPhone(rs.getString("phone_number"));
                vendor.setEmail(rs.getString("email"));
                vendor.setAddress(rs.getString("address"));
                vendor.setAddDate(rs.getDate("add_date").toLocalDate());
                return vendor;
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Vendor getVendor(String name) {
        Vendor vendor = new Vendor();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM vendors WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                vendor.setVendorId(rs.getInt("vendor_id"));
                vendor.setName(rs.getString("name"));
                vendor.setCompanyName(rs.getString("company_name"));
                vendor.setPhone(rs.getString("phone_number"));
                vendor.setEmail(rs.getString("email"));
                vendor.setAddress(rs.getString("address"));
                vendor.setAddDate(rs.getDate("add_date").toLocalDate());
                return vendor;
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM vendors";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setVendorId(rs.getInt("vendor_id"));
                vendor.setName(rs.getString("name"));
                vendor.setCompanyName(rs.getString("company_name"));
                vendor.setPhone(rs.getString("phone_number"));
                vendor.setEmail(rs.getString("email"));
                vendor.setAddress(rs.getString("address"));
                vendor.setAddDate(rs.getDate("add_date").toLocalDate());

                vendors.add(vendor);
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }
}
