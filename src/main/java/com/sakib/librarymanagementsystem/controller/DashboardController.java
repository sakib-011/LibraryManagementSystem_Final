package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import com.sakib.librarymanagementsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private BookService bookService;
    private StudentService studentService;
    private VendorService vendorService;
    private PublicationService publicationService;
    private BookAllotmentHistoryService bookAllotmentHistoryService;


    public DashboardController(BookService bookService , StudentService studentService , VendorService vendorService, PublicationService publicationService, BookAllotmentHistoryService bookAllotmentHistoryService){
        this.bookService = bookService;
        this.studentService =studentService;
        this.vendorService = vendorService;
        this.publicationService = publicationService;
        this.bookAllotmentHistoryService = bookAllotmentHistoryService;
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model , HttpSession httpSession){

        String name = (String) httpSession.getAttribute("name");
        if(name != null){
            model.addAttribute("dateTime" , LocalDateTime.now());
            model.addAttribute("name" , name);
            model.addAttribute("totalBook" ,getTotalBook());
            model.addAttribute("totalVendor" , getTotalVendor());
            model.addAttribute("totalStudent" , getTotalStudent());
            model.addAttribute("totalPublication" , getTotalPublication());
            model.addAttribute("totalAllotment" ,  getTotalAllotment());
            model.addAttribute("totalEarning" ,  getTotalEarning());
            model.addAttribute("totalReturn" ,  getReturnBookCount());
            model.addAttribute("totalPending" ,  getTotalPendingCount());

            return "dashboard";
        } else{
            return "redirect:/login";
        }

    }



    private int getTotalBook(){
        return bookService.getAllBook().size();
    }

    private int getTotalStudent(){
        return studentService.getAllStudent().size();
    }

    private int getTotalVendor(){
        return vendorService.getAllVendors().size();
    }

    private int getTotalPublication(){return publicationService.getAllPublications().size();}

    private int getTotalAllotment(){return bookAllotmentHistoryService.getAllBookAllotments().stream().filter(c->
            !c.getSubmissionType().equals("Return")).toList().size();}

    private double getTotalEarning(){
        List<BookAllotmentHistory> bookAllotmentHistories =  bookAllotmentHistoryService.getAllBookAllotments().stream().filter(c->
                !c.getSubmissionType().equals("Return")).toList();

        return bookAllotmentHistories.stream()
                .mapToDouble(BookAllotmentHistory::getAmount)
                .sum();
    }

    public int getReturnBookCount(){
        List<BookAllotmentHistory> bookAllotmentHistories =  bookAllotmentHistoryService.getAllBookAllotments().stream().filter(c->
                c.getSubmissionType().equals("Return")).toList();

        return bookAllotmentHistories.size();
    }

    public int getTotalPendingCount(){
        List<BookAllotmentHistory> bookAllotmentHistories =  bookAllotmentHistoryService.getAllBookAllotments().stream().filter(c->
                !c.getSubmissionType().equals("Return")).toList();

        return bookAllotmentHistories.size();
    }




}
