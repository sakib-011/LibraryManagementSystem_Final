package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class BookExportController {

    private final BookService bookService;

    public BookExportController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/exportBooksPdf")
    public ResponseEntity<byte[]> exportBooksToPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<Book> books = bookService.getAllBook();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Book List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);


            String[] headers = {"S.No", "Book Name", "Title", "Author", "Publisher", "Qty"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }


            int count = 1;
            for (Book b : books) {
                table.addCell(String.valueOf(count++));
                table.addCell(b.getBookName());
                table.addCell(b.getBookTitle());
                table.addCell(b.getAuthorName());
                table.addCell(b.getBookPublisher());
                table.addCell(String.valueOf(b.getAvailableQuantity()));
            }

            doc.add(table);
            doc.close();


            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "books.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
