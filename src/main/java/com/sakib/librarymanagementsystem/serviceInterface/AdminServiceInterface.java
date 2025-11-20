package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Admin;

import java.util.ArrayList;

public interface AdminServiceInterface {
    boolean addNewAdmin(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(int adminId);
    Admin getAdmin(String identity);
    ArrayList<Admin> getAllAdmin();
    Admin isExist(String identity , String password);
}
