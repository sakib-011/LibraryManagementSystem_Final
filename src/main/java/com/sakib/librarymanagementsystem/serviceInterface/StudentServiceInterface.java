package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Student;

import java.util.ArrayList;

public interface StudentServiceInterface {
    boolean addNewStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(int id);
    Student getStudent(int id);
    Student getStudent(String email);
    ArrayList<Student> getAllStudent();
}
