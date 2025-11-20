package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.PurchaseBookDto;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.PurchaseBook;
import com.sakib.librarymanagementsystem.modal.Vendor;
import com.sakib.librarymanagementsystem.service.BookService;
import com.sakib.librarymanagementsystem.service.PurchaseBookService;
import com.sakib.librarymanagementsystem.service.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PurchaseBooksController {


    private final BookService bookService;
    private final VendorService vendorService;
    private final PurchaseBookService purchaseBookService;

    public PurchaseBooksController(BookService bookService , VendorService vendorService , PurchaseBookService purchaseBookService){
        this.bookService = bookService;
        this.vendorService = vendorService;
        this.purchaseBookService = purchaseBookService;
    }


    @GetMapping("/purchase_books")
    public String purchaseBook(
            @RequestParam(value = "searchQuery" , required = false) String searchInput,
            @RequestParam(value = "editId" , required = false) Long editId,
            @RequestParam(value = "deleteId" , required = false) Long deleteId ,
            @RequestParam(value = "id" , required = false) Long id,
            Model model , HttpSession httpSession){
        ArrayList<Book> books = bookService.getAllBook();
        ArrayList<Vendor> vendors = vendorService.getAllVendors();
        ArrayList<PurchaseBook> purchaseBooks = purchaseBookService.getAllPurchaseBooks();

        model.addAttribute("currentDate" , "Date : " + LocalDate.now());
        model.addAttribute("books" , books);
        model.addAttribute("vendors" , vendors);
        model.addAttribute("purchaseBooks" , purchaseBooks);
        model.addAttribute("purchaseBookDto" , new PurchaseBookDto(null , null , null , null , null , ""));


        String addPurBookSuccess = (String) httpSession.getAttribute("addPurBookSuccess");
        String addPurBookFailed = (String) httpSession.getAttribute("addPurBookFailed");
        if(addPurBookSuccess!=null && addPurBookSuccess.equals("true")){
            model.addAttribute("alertMessage", "Purchase Book added successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("addPurBookSuccess");

        } else if(addPurBookFailed!=null && addPurBookFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to add Purchase book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("addPurBookFailed");

        }


        if(id !=null ){
            PurchaseBook purchaseBook = purchaseBookService.getPurchaseBook(id.intValue());
            Book book = bookService.getBook((long) purchaseBook.getBookId());
            Vendor vendor = vendorService.getVendor(purchaseBook.getVendorId());
            model.addAttribute("vendorName" , vendor.getName() );
            model.addAttribute("vendorEmail" ,vendor.getEmail() );
            model.addAttribute("vendorPhone" , vendor.getPhone() );
            model.addAttribute("vendorCompanyName" , vendor.getCompanyName() );
            model.addAttribute("vendorAddDate" , vendor.getAddDate() );
            model.addAttribute("invoiceMode" , true);
            model.addAttribute("bookName" , book.getBookName());
            model.addAttribute("bookPrice" , purchaseBook.getPricePerBook());
            model.addAttribute("purchaseDate" , purchaseBook.getPurchaseDate());
            model.addAttribute("quantity" , purchaseBook.getQuantity());
            double totalAmount = purchaseBook.getPricePerBook() * purchaseBook.getQuantity();
            model.addAttribute("totalAmount" , totalAmount);
        }

        if(deleteId!=null){
            System.out.println(deleteId);
            httpSession.setAttribute("deleteId" , deleteId);
            model.addAttribute("deleteMode" , true);
        }


        String deletePurBookSuccess = (String) httpSession.getAttribute("deletePurBookSuccess");
        String deletePurBookFailed = (String) httpSession.getAttribute("deletePurBookFailed");
        if(deletePurBookSuccess!=null && deletePurBookSuccess.equals("true")){
            model.addAttribute("alertMessage", "Purchase Book delete successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("deletePurBookSuccess");

        } else if(deletePurBookFailed!=null && deletePurBookFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to delete Purchase book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("deletePurBookFailed");
        }

        if(editId !=null){
            httpSession.setAttribute("editId" , editId);
            model.addAttribute("editMode" , true);
            model.addAttribute("editPurchaseBook" , purchaseBookService.getPurchaseBook(editId.intValue()));
        } else{
            model.addAttribute("editPurchaseBook" , new PurchaseBookDto(null , null, null , null , null , ""));
        }



        String editPurBookSuccess = (String) httpSession.getAttribute("editPurBookSuccess");
        String editPurBookFailed = (String) httpSession.getAttribute("editPurBookFailed");
        if(editPurBookSuccess!=null && editPurBookSuccess.equals("true")){
            model.addAttribute("alertMessage", "Purchase Book Updated successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("editPurBookSuccess");

        } else if(editPurBookFailed!=null && editPurBookFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to update Purchase book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("editPurBookFailed");
        }

        if(searchInput!=null){
            model.addAttribute("purchaseBooks", searchInput(searchInput));
        }

        return "purchase_books";
    }


    @PostMapping("/purchase_books")
    public String purchaseBook(
            @RequestParam(value = "action" ,required = false) String action,
            @ModelAttribute PurchaseBookDto purchaseBookDto , Model model , HttpSession httpSession){


        switch (action){
            case "add" :
                PurchaseBook purchaseBook = purchaseBookDto.toPurchaseBook();
                if(addNewPurchaseBook(purchaseBook)){
                     httpSession.setAttribute("addPurBookSuccess" , "true");
                } else{
                    httpSession.setAttribute("addPurBookFailed" , "true");
                }
                break;

            case "edit" :

                PurchaseBook editPurchaseBook = purchaseBookDto.toPurchaseBook();
                Long editId = (Long) httpSession.getAttribute("editId");
                editPurchaseBook.setId(editId.intValue());
                System.out.println(editId.intValue());

                if(editPurchaseBook.getPurchaseDate() == null){
                    editPurchaseBook.setPurchaseDate(purchaseBookService.getPurchaseBook(editId.intValue()).getPurchaseDate());
                }

                if(purchaseBookService.updatePurchaseBook(editPurchaseBook , editId.intValue())){
                    httpSession.setAttribute("editPurBookSuccess" , "true");
                } else{
                    httpSession.setAttribute("editPurBookFailed" , "true");
                }
                break;

            case "delete" :
                Long deleteId = (Long) httpSession.getAttribute("deleteId");
                if(purchaseBookService.deletePurchaseBook(deleteId.intValue())){
                    httpSession.setAttribute("deletePurBookSuccess" , "true");
                } else{
                    httpSession.setAttribute("deletePurBookFailed" , "true");
                }
                break;

        }


        return "redirect:/purchase_books";
    }


    private boolean addNewPurchaseBook(PurchaseBook purchaseBook){
        return  purchaseBookService.addPurchaseBook(purchaseBook);
    }


    private List<PurchaseBook> searchInput(String text){

        ArrayList<PurchaseBook> purchaseBooks = purchaseBookService.getAllPurchaseBooks();

        if(text == null || text.isEmpty()){
            return purchaseBooks;
        }
        final String input = text.trim().toLowerCase();

        return purchaseBooks.stream()
                .filter(b ->
                        b.getVendor_name().trim().toLowerCase().contains(input) ||
                                b.getBook_name().trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getQuantity()).trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getPricePerBook()).trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getPurchaseDate()).contains(input)
                ).toList();

    }

}
