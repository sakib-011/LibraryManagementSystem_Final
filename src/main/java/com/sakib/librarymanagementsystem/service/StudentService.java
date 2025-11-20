package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.serviceInterface.StudentServiceInterface;
import com.sakib.librarymanagementsystem.utility.ConnectionSingleton;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class StudentService implements StudentServiceInterface {

    @Override
    public boolean addNewStudent(Student student) {
        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "INSERT INTO students (full_name, email, phone_number, identity_type, registration_date, identity_file_name) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1 , student.getFullName());
            statement.setString(2 , student.getEmail());
            statement.setString(3 , student.getPhoneNumber());
            statement.setString(4 , student.getIdentity());
            statement.setDate(5 , Date.valueOf(student.getRegistrationDate()));
            statement.setString(6 , student.getIdentityFileName());


            int rowAffected = statement.executeUpdate();

            return rowAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateStudent(Student student) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "UPDATE students SET full_name = ?, email = ?, phone_number = ?, identity_type = ?,  identity_file_name = ? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1 , student.getFullName());
            statement.setString(2 , student.getEmail());
            statement.setString(3 , student.getPhoneNumber());
            statement.setString(4 , student.getIdentity());
            statement.setString(5 , student.getIdentityFileName());
            statement.setInt(6, student.getId());

            int rowAffected = statement.executeUpdate();
            statement.close();

            return rowAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "DELETE FROM students  WHERE id=?";
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
    public Student getStudent(int id) {
        Student  student = new Student();

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM students  WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet file = statement.executeQuery();

            if(file.next()){
                String fullName = file.getString("full_name");
                String email = file.getString("email");
                String phoneNumber = file.getString("phone_number");
                String identity = file.getString("identity_type");
                LocalDate registrationDate = file.getDate("registration_date").toLocalDate();
                String identityFileName = file.getString("identity_file_name");

                student.setFullName(fullName);
                student.setEmail(email);
                student.setPhoneNumber(phoneNumber);
                student.setIdentity(identity);
                student.setRegistrationDate(registrationDate);
                student.setIdentityFileName(identityFileName);
                student.setId(id);

                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Student getStudent(String email) {
        Student  student = new Student();

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM students  WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet file = statement.executeQuery();

            if(file.next()){
                String fullName = file.getString("full_name");
                int id  = file.getInt("id");
                String phoneNumber = file.getString("phone_number");
                String identity = file.getString("identity_type");
                LocalDate registrationDate = file.getDate("registration_date").toLocalDate();
                String identityFileName = file.getString("identity_file_name");

                student.setFullName(fullName);
                student.setEmail(email);
                student.setPhoneNumber(phoneNumber);
                student.setIdentity(identity);
                student.setRegistrationDate(registrationDate);
                student.setIdentityFileName(identityFileName); // fixed here
                student.setId(id);

                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Student> getAllStudent() {
        ArrayList<Student> students = new ArrayList<>();

        try{
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM students";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet file = statement.executeQuery();

            while(file.next()){
                String fullName = file.getString("full_name");
                int id  = file.getInt("id");
                String email = file.getString("email");
                String phoneNumber = file.getString("phone_number");
                String identity = file.getString("identity_type");
                LocalDate registrationDate = file.getDate("registration_date").toLocalDate();
                String identityFileName = file.getString("identity_file_name");

                Student student = new Student();

                student.setFullName(fullName);
                student.setEmail(email);
                student.setPhoneNumber(phoneNumber);
                student.setIdentity(identity);
                student.setRegistrationDate(registrationDate);
                student.setIdentityFileName(identityFileName);
                student.setId(id);

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
