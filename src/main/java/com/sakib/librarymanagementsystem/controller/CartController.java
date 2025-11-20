package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.StudentInfoDto;
import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import com.sakib.librarymanagementsystem.modal.Cart;
import com.sakib.librarymanagementsystem.service.BookAllotmentHistoryService;
import com.sakib.librarymanagementsystem.service.CartService;
import com.sakib.librarymanagementsystem.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class CartController {

    private final CartService cartService;
    private final BookAllotmentController bookAllotmentController;
    private final ArrayList<Cart> carts;
    private final StudentService studentService;
    private final BookAllotmentHistoryService bookAllotmentHistoryService;

    public CartController(CartService cartService, BookAllotmentController bookAllotmentController, StudentService studentService, BookAllotmentHistoryService bookAllotmentHistoryService) {
        this.cartService = cartService;
        this.bookAllotmentController = bookAllotmentController;
        carts = new ArrayList<>(bookAllotmentController.carts);
        this.studentService = studentService;
        this.bookAllotmentHistoryService = bookAllotmentHistoryService;
    }





    @GetMapping("/cart")
    public String cart(Model model , HttpSession httpSession){
        model.addAttribute("studentInfoDto" , new StudentInfoDto("" , ""));

        model.addAttribute("students" , studentService.getAllStudent());

        System.out.println("size " + bookAllotmentController.carts.size());
        if(!bookAllotmentController.carts.isEmpty()){
            model.addAttribute("totalAmount" , bookAllotmentController.findTotalAmount());
            model.addAttribute("carts" , bookAllotmentController.carts);
        }

        String confirm = (String) httpSession.getAttribute("confirm");
        if(confirm!=null && confirm.equals("true")){
            model.addAttribute("alertMessage", "Book allot successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("confirm");
        } else if(confirm !=null && confirm.equals("false")){
            model.addAttribute("alertMessage", "Failed to allot Book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("confirm");
        }

        return "cart";
    }




    @PostMapping("/cart")
    public String cart(
            @ModelAttribute("studentInfoDto") StudentInfoDto studentInfoDto,
            @RequestParam(value =  "action") String action ,
            @RequestParam(value =  "deleteId" , required = false) Long deleteId,
            Model model, HttpSession httpSession){


        if(action.equalsIgnoreCase("delete")){
            bookAllotmentController.carts.removeIf(c-> c.getBookId() == deleteId.intValue());
        }

        if(action.equals("confirm")){

            if(addIntoHistory(studentInfoDto , bookAllotmentController.carts)){
                httpSession.setAttribute("confirm" , "true");
            }else{
                httpSession.setAttribute("confirm" , "false");
            }

        }

        return "redirect:/cart";

    }


    private boolean addIntoHistory(StudentInfoDto studentInfoDto , ArrayList<Cart> carts){




        for(Cart cart : carts){
            BookAllotmentHistory bookAllotmentHistory = new BookAllotmentHistory();
            bookAllotmentHistory.setStudentName(studentInfoDto.studentName());
            bookAllotmentHistory.setEmail(studentInfoDto.studentEmail());

            bookAllotmentHistory.setTitle(cart.getBookTitle());
            bookAllotmentHistory.setAmount(cart.getAmount());
            bookAllotmentHistory.setDate(cart.getDate());
            bookAllotmentHistory.setBookId(cart.getBookId());
            bookAllotmentHistory.setQuantity(cart.getQuantity());
            bookAllotmentHistory.setSubmissionType(cart.getSubmissionType());
            bookAllotmentHistory.setSubscriptionType(cart.getSubscriptionType());

            bookAllotmentHistoryService.addBookAllotment(bookAllotmentHistory);
        }

        carts.clear();
        return true;


    }
}
