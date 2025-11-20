package com.sakib.librarymanagementsystem.modal;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String userName;
    private LocalDate registrationDate;
}
