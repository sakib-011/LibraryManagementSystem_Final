package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Cart;
import java.util.ArrayList;

public interface CartServiceInterface {
    boolean addCart(Cart cart);
    boolean updateCart(Cart cart);
    boolean deleteCart(int id);
    Cart getCart(int id);
    ArrayList<Cart> getAllCarts();
}
