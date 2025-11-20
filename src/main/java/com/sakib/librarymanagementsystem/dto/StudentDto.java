package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.service.StudentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public record StudentDto(String fullName , String email , String phoneNumber , String identity , LocalDate registrationDate, MultipartFile identityFile) {



    public Student toStudent() throws IOException {
        Student student = new Student();
        student.setFullName(fullName);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setIdentity(identity);
        student.setRegistrationDate(registrationDate);

        if(identityFile == null){
            return student;
        }


            String identityFileName = identityFile.getOriginalFilename();
            String uploadDir = "src/main/resources/static/uploadIdentityFile/";
            Path path = Paths.get(uploadDir + identityFileName);
            Files.write(path , identityFile.getBytes());
            student.setIdentityFileName(identityFileName);


        return student;

    }

}
