package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import com.sakib.librarymanagementsystem.service.BookAllotmentHistoryService;
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
public class AllotmentHistoryExportController {
    private final BookService bookService;
    private final BookAllotmentHistoryService bookAllotmentHistoryService;

    public AllotmentHistoryExportController(BookService bookService, BookAllotmentHistoryService bookAllotmentHistoryService) {
        this.bookService = bookService;
        this.bookAllotmentHistoryService = bookAllotmentHistoryService;
    }

    @GetMapping("/exportBookAllotmentHisotyPdf")
    public ResponseEntity<byte[]> exportBooksToPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<BookAllotmentHistory> bookAllotmentHistorie = bookAllotmentHistoryService.getAllBookAllotments();
            List<BookAllotmentHistory> bookAllotmentHistories = bookAllotmentHistorie.stream().filter(c-> !c.getSubmissionType().equals("Return")).toList();


            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Book Allotment History List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" ")); // empty line

            PdfPTable table = new PdfPTable(7); // columns: S.No, Name, Title, Author, Publisher, Qty

            // Table Header
            String[] headers = {"S.No","Student Name", "Student Email", "Book Name", "Qty", "amount", "Issue Date"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Table Rows
            int count = 1;
            for (BookAllotmentHistory b : bookAllotmentHistories) {
                table.addCell(String.valueOf(count++));
                table.addCell(b.getStudentName());
                table.addCell(b.getEmail());
                table.addCell(b.getTitle());
                table.addCell(String.valueOf(b.getQuantity()));
                table.addCell(String.valueOf(b.getAmount()));
                table.addCell(String.valueOf(b.getDate()));
            }

            doc.add(table);
            doc.close();

            // Return PDF as byte array
            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "BookAllotmentHistory.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
