package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Subscription;

public record SubscriptionDto(String title , Double amount , Integer days) {
    public Subscription toSubscription(){
        return new Subscription(title , amount ,days);
    }
}
