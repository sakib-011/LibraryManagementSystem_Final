package com.sakib.librarymanagementsystem.modal;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAllotmentHistory {
    private int id;
    private int bookId;
    private int quantity;
    private double amount;
    private String title;
    private String submissionType;
    private String subscriptionType;
    private LocalDate date;
    private String email;
    private String studentName;
}
