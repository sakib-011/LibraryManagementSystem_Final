package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakib.librarymanagementsystem.modal.Book;
import com.sakib.librarymanagementsystem.modal.Vendor;
import com.sakib.librarymanagementsystem.service.VendorService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class VendorExportController {
    private final VendorService vendorService;

    public VendorExportController(VendorService vendorService){
        this.vendorService = vendorService;
    }


    @GetMapping("/exportVendorsPdf")
    public ResponseEntity<byte[]> exportVendorsPdf(){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<Vendor> vendors = vendorService.getAllVendors();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Vendors List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);


            String[] headers = {"S.No", "Vendor Name", "Company Name", "Email", "Phone Number", "Address"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }


            int count = 1;
            for (Vendor v : vendors) {
                table.addCell(String.valueOf(count++));
                table.addCell(v.getName());
                table.addCell(v.getCompanyName());
                table.addCell(v.getEmail());
                table.addCell(v.getPhone());
                table.addCell(v.getAddress());
            }

            doc.add(table);
            doc.close();


            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "vendors.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
