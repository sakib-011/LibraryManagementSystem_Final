package com.sakib.librarymanagementsystem.service;

import com.sakib.librarymanagementsystem.modal.Admin;
import com.sakib.librarymanagementsystem.serviceInterface.AdminServiceInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService implements AdminServiceInterface {

    private final ArrayList<Admin> admins = new ArrayList<>();


    @Override
    public boolean addNewAdmin(Admin admin) {

        if(getAdmin(admin.getIdentity()) != null){
            return false;
        }

       admins.add(admin);
       return true;
    }

    @Override
    public boolean updateAdmin(Admin admin) {

        if(getAdmin(admin.getIdentity()) == null){
            return false;
        }

        deleteAdmin(admin.getId());
        addNewAdmin(admin);
        return true;
    }

    @Override
    public boolean deleteAdmin(int adminId) {
      return admins.removeIf(a-> a.getId() == adminId);
    }

    @Override
    public Admin getAdmin(String identity) {
       return  admins.stream().filter(a-> a.getIdentity().equals(identity)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Admin> getAllAdmin() {
        return admins;
    }

    @Override
    public Admin isExist(String identity, String password) {
        return  admins.stream().filter(a-> a.getIdentity().equals(identity) && a.getPassword().equals(password)).findFirst().orElse(null);
    }
}
