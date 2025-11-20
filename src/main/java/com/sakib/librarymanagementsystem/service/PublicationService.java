package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Publication;
import com.sakib.librarymanagementsystem.serviceInterface.PublicationServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PublicationService implements PublicationServiceInterface {

    @Override
    public boolean addNewPublication(Publication publication) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO publication (name, address, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, publication.getName());
            statement.setString(2, publication.getAddress());
            statement.setString(3, publication.getDescription());

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePublication(Publication publication) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE publication SET name = ?, address = ?, description = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, publication.getName());
            statement.setString(2, publication.getAddress());
            statement.setString(3, publication.getDescription());
            statement.setInt(4, publication.getId());

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePublication(int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM publication WHERE id=?";
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
    public Publication getPublication(int id) {
        Publication publication = new Publication();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM publication WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                publication.setId(id);
                publication.setName(result.getString("name"));
                publication.setAddress(result.getString("address"));
                publication.setDescription(result.getString("description"));
//                publication.setCreateDate(result.getTimestamp("create_date").toLocalDateTime());
//                publication.setUpdateDate(result.getTimestamp("update_date").toLocalDateTime());

                return publication;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Publication> getAllPublications() {
        ArrayList<Publication> publications = new ArrayList<>();

        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM publication";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Publication publication = new Publication();

                publication.setId(result.getInt("id"));
                publication.setName(result.getString("name"));
                publication.setAddress(result.getString("address"));
                publication.setDescription(result.getString("description"));
//                publication.setCreateDate(result.getTimestamp("create_date").toLocalDateTime());
//                publication.setUpdateDate(result.getTimestamp("update_date").toLocalDateTime());

                publications.add(publication);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publications;
    }
}
