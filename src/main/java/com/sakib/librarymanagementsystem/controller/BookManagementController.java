package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.BookDto;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookManagementController {

    private final BookService bookService;

    public BookManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book_management")
    public String bookManagement(@RequestParam(value = "id", required = false) Long id,
                                 @RequestParam(value = "deleteBookId", required = false) Long deleteId,
                                 @RequestParam(value = "filterMode" , required = false) String clicked,
                                 @RequestParam(value = "searchQuery" , required = false) String searchInput,
                                 Model model,
                                 HttpSession httpSession) {

        ArrayList<Book> books = bookService.getAllBook();

        model.addAttribute("books", books);

        if (id != null) {
            // Edit mode
            System.out.println(id);
            model.addAttribute("dtoBook", new BookDto(0,"", "", "", "", "", 0 , null));model.addAttribute("dtoBook", new BookDto(0,"", "", "", "", "", 0 , null));
            Book bookToEdit = bookService.getBook(id);
            model.addAttribute("editBook", bookToEdit);
            model.addAttribute("editMode", true);
            httpSession.setAttribute("bookId", id);
        } else {
            // Default new book
            model.addAttribute("dtoBook", new BookDto(0,"", "", "", "", "", 0 , null));
        }

        if (deleteId != null) {
            model.addAttribute("deleteMode", true);
            model.addAttribute("deleteBook" , bookService.getBook(deleteId));
            httpSession.setAttribute("deleteBookId", deleteId);
        } else{
                model.addAttribute("deleteMode", false);
        }

        if(clicked != null){
            System.out.println("Filter button was clicked!");
            model.addAttribute("filterMode" , true);
        } else{
            model.addAttribute("filterMode" , false);
        }


        if(searchInput != null && !searchInput.isEmpty()){
            System.out.println("Received Query: " + searchInput);
            model.addAttribute("books" , searchFiled(searchInput));
        }else{
            model.addAttribute("books" , books);
        }




         String bookAddSuccess = (String) httpSession.getAttribute("bookAddSuccess");
         String bookAddFailed = (String) httpSession.getAttribute("bookAddFailed");
        if(bookAddSuccess!=null && bookAddSuccess.equals("true")){
            model.addAttribute("alertMessage", "Book added successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("bookAddSuccess");
            bookAddSuccess = null;
        } else if(bookAddSuccess!=null && bookAddFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to add book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("bookAddFailed");
            bookAddFailed = null;
        }


        String bookEditSuccess = (String) httpSession.getAttribute("bookUpdateSuccessfully");
        String bookEditFailed = (String) httpSession.getAttribute("bookUpdateFailed");

        if(bookEditSuccess!=null && bookEditSuccess.equals("true")){
            model.addAttribute("alertMessage", "Book Update successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("bookUpdateSuccessfully");
            bookAddSuccess = null;
        } else if(bookEditFailed!=null && bookEditFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Update book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("bookUpdateFailed");
            bookAddFailed = null;
        }


        String bookDeleteSuccess = (String) httpSession.getAttribute("bookDeleteSuccessfully");
        String bookDeleteFailed = (String) httpSession.getAttribute("bookDeleteFailed");

        if(bookDeleteSuccess!=null && bookDeleteSuccess.equals("true")){
            model.addAttribute("alertMessage", "Book Delete successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("bookDeleteSuccessfully");
            bookAddSuccess = null;
        } else if(bookDeleteFailed!=null && bookDeleteFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Delete book!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("bookDeleteFailed");
            bookAddFailed = null;
        }


        return "book_management";
    }

    @PostMapping("/book_management")
    public String bookManagement(@RequestParam("action") String action,
                                 @ModelAttribute BookDto bookDto,
                                 Model model,
                                 HttpSession httpSession) throws IOException {


        switch (action) {
            case "add":
                Book book = bookDto.toBook();

                MultipartFile imageFile = bookDto.image();
                String fileName = imageFile.getOriginalFilename();

                String uploadDir = "src/main/resources/static/uploads/";

                Path path = Paths.get(uploadDir + fileName);
                Files.write(path, imageFile.getBytes());

                book.setImageName(fileName);

                if(!checkIsValidAllField(book)){
                    return "redirect:/book_managemnet";
                }

                boolean addNewBook = addBook(book);
                if (addNewBook) {
                    httpSession.setAttribute("bookAddSuccess" , "true");

                    return "redirect:/book_management";
                } else {
                   httpSession.setAttribute("bookAddFailed" , "true");
                }
                break;

            case "edit":
                Long bookId = (Long) httpSession.getAttribute("bookId");

                Book updated = bookDto.toBook();
                updated.setBookId(bookId.intValue());

                MultipartFile editImage = bookDto.image();
                if (editImage != null && !editImage.isEmpty()) {
                    String fileName2 = editImage.getOriginalFilename();
                    String uploadDir2 = "src/main/resources/static/uploads/";
                    Path path2 = Paths.get(uploadDir2 + fileName2);
                    Files.write(path2, editImage.getBytes());
                    updated.setImageName(fileName2);

                }else{
                    updated.setImageName(bookService.getBook(bookId).getImageName());
                    System.out.println("Failed to upload edit image");
                }

                if (bookService.updateBook(updated)) {
                    httpSession.setAttribute("bookUpdateSuccessfully" , "true");
                    return "redirect:/book_management";
                } else{
                    httpSession.setAttribute("bookUpdateFailed" , "true");
                }
                break;

            case "delete":
                Long deleteBookId = (Long) httpSession.getAttribute("deleteBookId");
                boolean deleteBook = deleteBook(deleteBookId.intValue());
                if (deleteBook) {
                   httpSession.setAttribute("bookDeleteSuccessfully" , "true");
                    return "redirect:/book_management";
                } else {
                    httpSession.setAttribute("bookDeleteFailed" , "true");
                }
                break;
        }

        return "redirect:/book_management";
    }

    private boolean addBook(Book book) {
        return bookService.addNewBook(book);
    }

    private boolean updateBook(int bookId, Book book) {
        book.setBookId(bookId);
        return bookService.updateBook(book);
    }

    private boolean deleteBook(int bookId) {
        return bookService.deleteBook(bookId);
    }

    private boolean checkIsValidAllField(Book book){
        return book.getBookName() != null || book.getBookTitle() != null ||
                book.getAuthorName() != null || book.getBookPublisher() != null ||
                book.getAvailableQuantity() != 0 || book.getImageName() != null;
    }


    private List<Book> searchFiled(String text) {
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
