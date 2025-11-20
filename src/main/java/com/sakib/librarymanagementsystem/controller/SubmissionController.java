package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.service.BookAllotmentHistoryService;
import com.sakib.librarymanagementsystem.service.BookService;
import com.sakib.librarymanagementsystem.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubmissionController {


    private final BookAllotmentHistoryService bookAllotmentHistoryService;
    private final StudentService studentService;
    private final BookService bookService;
    public SubmissionController(BookAllotmentHistoryService bookAllotmentHistoryService, StudentService studentService, BookService bookService) {
        this.bookAllotmentHistoryService = bookAllotmentHistoryService;
        this.studentService = studentService;
        this.bookService = bookService;
    }

    @GetMapping("/submission")
    public String submission(
            @RequestParam(value = "searchQuery" , required = false) String searchInput,
            @RequestParam(value  = "invoiceId" , required = false) Long invoiceId,
            Model model){


        if(invoiceId!=null){
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String formattedDate = date.format(formatter);
            System.out.println(formattedDate);


//            model.addAttribute("")
            System.out.println(invoiceId);
            BookAllotmentHistory bookAllotmentHistory = bookAllotmentHistoryService.getBookAllotment(invoiceId.intValue());
            Student student = studentService.getStudent(bookAllotmentHistory.getEmail());
            Book book = bookService.getBook((long) bookAllotmentHistory.getBookId());
            model.addAttribute("studentName" , student.getFullName());
            model.addAttribute("currentDate" , formattedDate);
            model.addAttribute("studentEmail" , student.getEmail());
            model.addAttribute("studentPhoneNumber" , student.getPhoneNumber());
            model.addAttribute("invoiceMode" , true);



            model.addAttribute("bookName", book.getBookName());
            model.addAttribute("quantity" , bookAllotmentHistory.getQuantity());
            model.addAttribute("IssueDate" , bookAllotmentHistory.getDate());
            LocalDate submitDate = null;
            if(bookAllotmentHistory.getSubscriptionType().equals("Weekly")){
                submitDate = bookAllotmentHistory.getDate().plusWeeks(1);
            } else if(bookAllotmentHistory.getSubscriptionType().equals("Monthly")){
                submitDate = bookAllotmentHistory.getDate().plusMonths(1);
            }
            model.addAttribute("submissionDate" , submitDate);
            model.addAttribute("subscriptionType" , bookAllotmentHistory.getSubscriptionType());
            model.addAttribute("totalAmount" , bookAllotmentHistory.getQuantity() * bookAllotmentHistory.getAmount());

        }

        model.addAttribute("history" , getAllSubmission());

        if(searchInput!=null){
            model.addAttribute("history" , searchFiled(searchInput));
        }


        return "submission";
    }



    private List<BookAllotmentHistory> searchFiled(String text) {
        if (text == null || text.trim().isEmpty()) {
            return bookAllotmentHistoryService.getAllBookAllotments();
        }

        List<BookAllotmentHistory> bookAllotmentHistories = bookAllotmentHistoryService.getAllBookAllotments().stream().filter(c-> c.getSubmissionType().equals("Return")).toList();
        final String input = text.trim().toLowerCase();

        return bookAllotmentHistories.stream()
                .filter(b ->
                        b.getTitle().toLowerCase().trim().contains(input) ||

                                b.getStudentName().toLowerCase().trim().contains(input) ||

                                b.getEmail().toLowerCase().trim().contains(input) ||

                                String.valueOf(b.getAmount()).contains(input) ||

                                String.valueOf(b.getQuantity()).trim().contains(input) ||

                                String.valueOf(b.getDate()).trim().contains(input) ||

                                String.valueOf(b.getSubscriptionType()).trim().contains(input) ||

                                String.valueOf(b.getSubmissionType()).trim().contains(input)
                ).toList();
    }




    private List<BookAllotmentHistory> getAllSubmission(){
        ArrayList<BookAllotmentHistory> bookAllotmentHistories = bookAllotmentHistoryService.getAllBookAllotments();

        return  bookAllotmentHistories.stream().filter(c-> c.getSubmissionType().equals("Return")).toList();

    }
}
