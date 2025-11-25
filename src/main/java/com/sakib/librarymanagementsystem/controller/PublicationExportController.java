package com.sakib.librarymanagementsystem.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakib.librarymanagementsystem.modal.Publication;
import com.sakib.librarymanagementsystem.modal.PurchaseBook;
import com.sakib.librarymanagementsystem.service.PublicationService;
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
public class PublicationExportController {

    private final PublicationService publicationService;

    public PublicationExportController( PublicationService publicationService){
        this.publicationService = publicationService;
    }


    @GetMapping("/exportPublicationPdf")
    public ResponseEntity<byte[]> exportStudentsToPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<Publication> publications = publicationService.getAllPublications();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();


            doc.add(new Paragraph("Publication List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE)));
            doc.add(new Paragraph(" "));


            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);


            String[] headers = {"S.No", "Publication Name", "Address", "Description"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }


            int count = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            for (Publication p : publications) {
                table.addCell(String.valueOf(count++));
                table.addCell(p.getName());
                table.addCell(p.getAddress());
                table.addCell(p.getDescription());

            }

            doc.add(table);
            doc.close();


            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "publication.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headersResp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
