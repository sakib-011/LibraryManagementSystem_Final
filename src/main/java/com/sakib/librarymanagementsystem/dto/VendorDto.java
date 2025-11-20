package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Vendor;

public record VendorDto(String name , String companyName , String phone, String email, String address) {
    public Vendor toVendor(){
        return new Vendor(name , companyName , phone , email , address);
    }
}
