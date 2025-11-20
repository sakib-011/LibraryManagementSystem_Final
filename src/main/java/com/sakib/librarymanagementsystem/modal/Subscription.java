package com.sakib.librarymanagementsystem.modal;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Subscription {
    private int id;
    private String title;
    private double amount;
    private int days;

    public Subscription(String title, Double amount, Integer days) {
        this.title = title;
        this.amount = amount;
        this.days =days;
    }
}
