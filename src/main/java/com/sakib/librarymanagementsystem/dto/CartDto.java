package com.sakib.librarymanagementsystem.dto;

import java.time.LocalDate;

public record CartDto(String submissionType , String subscriptionType , LocalDate addDate) {
}
