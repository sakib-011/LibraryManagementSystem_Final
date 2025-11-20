package com.sakib.librarymanagementsystem.modal;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String identity;
    private LocalDate registrationDate;
    private String identityFileName;
    private MultipartFile identityFile;
}
