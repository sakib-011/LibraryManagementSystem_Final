package com.sakib.librarymanagementsystem.modal;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Publication {
    private int id;
    private String name;
    private String address;
    private String description;

    public Publication(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }
}
