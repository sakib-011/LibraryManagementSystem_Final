package com.sakib.librarymanagementsystem.modal;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Vendor {
    private String name;
    private String companyName;
    private String phone;
    private String email;
    private String address;
    private int vendorId;
    private LocalDate addDate;

    public Vendor(String name, String companyName, String phone, String email, String address) {
        this.name = name;
        this.companyName = companyName;
        this.phone = phone;
        this.email =email;
        this.address = address;
    }
}
