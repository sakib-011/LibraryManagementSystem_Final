package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.Subscription;

import java.util.ArrayList;

public interface SubscriptionInterface {
    boolean addNewSubscription(Subscription subscription);
    boolean updateSubscription(Subscription subscription);
    boolean deleteSubscription(int id);
    Subscription getSubscription(int id);
    Subscription getSubscription(String title);
    ArrayList<Subscription> getAllSubscription();
}
