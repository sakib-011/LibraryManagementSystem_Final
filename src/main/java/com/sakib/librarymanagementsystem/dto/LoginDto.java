package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Admin;
import org.springframework.stereotype.Service;


public record LoginDto(String identity , String password) {

}
