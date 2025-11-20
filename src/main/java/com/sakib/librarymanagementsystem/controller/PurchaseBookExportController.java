package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakib.librarymanagementsystem.modal.PurchaseBook;
import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.service.BookService;
import com.sakib.librarymanagementsystem.service.PurchaseBookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PurchaseBookExportController {


    private final PurchaseBookService purchaseBookService;

    public PurchaseBookExportController(PurchaseBookService purchaseBookService){
        this.purchaseBookService = purchaseBookService;
    }


    @GetMapping("/exportPurchaseBooksPdf")
    public ResponseEntity<byte[]> exportStudentsToPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<PurchaseBook> purchaseBooks = purchaseBookService.getAllPurchaseBooks();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            // Title
            doc.add(new Paragraph("Purchase Books List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" ")); // empty line

            // Table with columns: S.No, Name, Email, Phone, Identity Type, Registration Date
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Header
            String[] headers = {"S.No", "Book Name", "Vendor Name", "Quantity", "Book Price", "Purchase Date"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Table Rows
            int count = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            for (PurchaseBook p : purchaseBooks) {
                table.addCell(String.valueOf(count++));
                table.addCell(p.getBook_name());
                table.addCell(p.getVendor_name());
                table.addCell(String.valueOf(p.getQuantity()));
                table.addCell(String.valueOf(p.getPricePerBook()));
                table.addCell(p.getPurchaseDate() != null ? p.getPurchaseDate().format(formatter) : "");
            }

            doc.add(table);
            doc.close();

            // Return PDF as byte array
            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "purchaseBooksInformation.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
