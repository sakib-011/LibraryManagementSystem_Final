package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.modal.Vendor;

import java.util.ArrayList;

public interface VendorServiceInterface {
    boolean addNewVendor(Vendor vendor);

    boolean updateVendor(Vendor vendor);

    boolean deleteVendor(int vendorId);

    Vendor getVendor(int vendorId);

    Vendor getVendor(String email);

    ArrayList<Vendor> getAllVendors();
}
