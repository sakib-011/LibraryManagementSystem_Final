package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.CartDto;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.Cart;
import com.sakib.librarymanagementsystem.modal.Subscription;
import com.sakib.librarymanagementsystem.service.BookService;
import com.sakib.librarymanagementsystem.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookAllotmentController {
    private final ArrayList<CartDto> cartDtos = new ArrayList<>();
    public final ArrayList<Cart> carts = new ArrayList<>();
    private final BookService bookService;
    private final SubscriptionService subscriptionService;

    public BookAllotmentController(BookService bookService, SubscriptionService subscriptionService){
        this.bookService = bookService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/book_allotment")
    public String bookAllotment(
            @RequestParam(value =  "searchQuery" , required = false) String searchInput,
            @RequestParam(value  = "bookId" , required = false) Long bookId,
            Model model, HttpSession httpSession){
        ArrayList<Book> books = bookService.getAllBook();


        model.addAttribute("cartDto" , new CartDto("" ,"" , null));
        model.addAttribute("books" , books);
        model.addAttribute("subscription" , subscriptionService.getAllSubscription());

        if(bookId != null){

            model.addAttribute("qty" , bookService.getBook(bookId).getAvailableQuantity());
            model.addAttribute("submissionMode" , true);
            httpSession.setAttribute("bookId" , bookId);
        }

//        cartDtos.add(new CartDto(1 , "java" , "java"));
//        cartDtos.remove(0);

        if(!carts.isEmpty()){
            model.addAttribute("totalItems" , carts.size());
            model.addAttribute("totalAmount" , findTotalAmount());
            model.addAttribute("cartBooks" , carts);
            model.addAttribute("cartOption" , true);
        } else{
            model.addAttribute("cartEmpty" , true);
        }

        if(searchInput!=null){
            model.addAttribute("books" , searchInput(searchInput));
        }




        return "book_allotment";
    }





    @PostMapping("/book_allotment")
    public String bookAllotment(
            @RequestParam(value = "cartPlusBookId" , required = false) Long cartPlusBookId,
            @RequestParam(value = "cartMinusId" , required = false) Long cartMinusId,
            @RequestParam(value = "cartDeleteId" , required = false) Long cartDeleteId,
            @RequestParam(value = "action" , required = false) String action,
            @ModelAttribute CartDto cartDto, Model model , HttpSession httpSession){


        switch (action){
            case "add":
                System.out.println("SubmissionType  ; " + cartDto.submissionType());
                Long bookId = (long) httpSession.getAttribute("bookId");
                Book book = bookService.getBook(bookId);

                if(cartDto.submissionType().equals("Return")){
                    book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                    book.setBookId(bookId.intValue());
                    bookService.updateBook(book);
                } else if(cartDto.submissionType().equals("Brow")){
                    book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                    book.setBookId(bookId.intValue());
                    bookService.updateBook(book);
                }

                Subscription subscription = subscriptionService.getSubscription(cartDto.subscriptionType());
                Cart find = carts.stream().filter(c-> c.getBookId()==bookId.intValue()).findAny().orElse(null);
                if(find == null){
                    carts.add(new Cart(bookId.intValue() , 1 , subscription.getAmount() , book.getBookName() , cartDto.submissionType() , cartDto.subscriptionType() , cartDto.addDate()));
                } else{
                    find.setQuantity(find.getQuantity() + 1);
                    find.setAmount(subscription.getAmount());
                    find.setSubmissionType(cartDto.submissionType());
                    find.setSubscriptionType(cartDto.subscriptionType());
                    find.setDate(cartDto.addDate());
                }
                break;
            case "plus":
                if(cartPlusBookId!=null){
                    Cart newFind = carts.stream().filter(c-> c.getBookId() == cartPlusBookId.intValue()).findAny().orElse(null);
                    if(newFind!=null){
                        newFind.setQuantity(newFind.getQuantity()+1);
                    }
                }
                break;

            case "minus":
                System.out.println(cartMinusId);
                if(cartMinusId!=null){
                    Cart newFind = carts.stream().filter(c-> c.getBookId() == cartMinusId.intValue()).findAny().orElse(null);
                    if(newFind!=null){

                        if(newFind.getQuantity() == 0){
                            carts.removeIf(c-> c.getBookId() == newFind.getBookId());
                            break;
                        }

                        newFind.setQuantity(newFind.getQuantity()-1);
                    }
                }
                break;

            case "delete":
                System.out.println(cartDeleteId);

                if(cartDeleteId!=null){
                    carts.removeIf(c->c.getBookId() == cartDeleteId);
                }

                break;



        }


        return "redirect:/book_allotment";

    }


    public double findTotalAmount(){
        double sum = 0.0;
        for(Cart c : carts){
            String subscriptionType = c.getSubscriptionType();
            String submissionType = c.getSubscriptionType();
            Subscription subscription = subscriptionService.getSubscription(c.getSubscriptionType());

            System.out.println(subscription.getAmount());

            if(!submissionType.equals("Return")){
                    sum += subscription.getAmount() * c.getQuantity();
            }
        }

        System.out.println("Sum" + sum);
        return sum;

    }


    private List<Book> searchInput(String text) {
        if (text == null || text.trim().isEmpty()) {
            return bookService.getAllBook();
        }

        List<Book> books = bookService.getAllBook();
        final String input = text.trim().toLowerCase();

        return books.stream()
                .filter(b ->
                        b.getBookName().toLowerCase().trim().contains(input) ||

                                b.getBookTitle().toLowerCase().trim().contains(input) ||

                                b.getBookPublisher().toLowerCase().trim().contains(input) ||

                                b.getAuthorName().toLowerCase().trim().contains(input) ||

                                String.valueOf(b.getAvailableQuantity()).trim().contains(input)
                ).toList();
    }

}
