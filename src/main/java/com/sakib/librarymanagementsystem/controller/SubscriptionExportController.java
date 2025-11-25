package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakib.librarymanagementsystem.modal.Subscription;
import com.sakib.librarymanagementsystem.modal.Vendor;
import com.sakib.librarymanagementsystem.service.SubscriptionService;
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
public class SubscriptionExportController {
    private final SubscriptionService subscriptionService;

    public SubscriptionExportController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/exportSubscriptionPdf")
    public ResponseEntity<byte[]> exportVendorsPdf(){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<Subscription> subscriptions = subscriptionService.getAllSubscription();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Subscription List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);


            String[] headers = {"S.No", "Subscription Title", "Amount", "Days"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }


            int count = 1;
            for (Subscription s : subscriptions) {
                table.addCell(String.valueOf(count++));
                table.addCell(s.getTitle());
                table.addCell(String.valueOf(s.getAmount()));
                table.addCell(String.valueOf(s.getDays()));
            }

            doc.add(table);
            doc.close();


            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "subscription.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
