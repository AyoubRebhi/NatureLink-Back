package com.example.naturelink.Service;

import com.example.naturelink.dto.ReservationDTO;
import com.example.naturelink.Entity.Event;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;


import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportPDFService {
    public ByteArrayInputStream exportEventsToPdf(List<Event> events) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Events List", titleFont));
            document.add(new Paragraph(" "));

            for (Event event : events) {
                document.add(new Paragraph("Title: " + event.getTitle(), bodyFont));
                document.add(new Paragraph("Date: " + event.getDate(), bodyFont));
                document.add(new Paragraph("Location: " + event.getLocation(), bodyFont));
                document.add(new Paragraph("Founder: " + event.getFounder(), bodyFont));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateReservationPDF(Long reservationId, ReservationDTO reservation) {
        // Create a document instance
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Create PDF writer to output the document
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Add a title to the document
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reservation Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add reservation ID
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Paragraph reservationIdParagraph = new Paragraph("Reservation ID: " + reservationId, headerFont);
            reservationIdParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(reservationIdParagraph);

            // Add space between the title and reservation details
            document.add(Chunk.NEWLINE);

            // Add reservation details
            Font regularFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            Paragraph clientNames = new Paragraph("Client Names: " + String.join(", ", reservation.getClientNames()), regularFont);
            document.add(clientNames);

            Paragraph dateDebut = new Paragraph("Start Date: " + reservation.getDateDebut(), regularFont);
            document.add(dateDebut);

            Paragraph dateFin = new Paragraph("End Date: " + reservation.getDateFin(), regularFont);
            document.add(dateFin);

            Paragraph statut = new Paragraph("Status: " + reservation.getStatut(), regularFont);
            document.add(statut);

            // Add reservation type
            Paragraph reservationType = new Paragraph("Reservation Type: " + reservation.getTyperes(), regularFont);
            document.add(reservationType);

            // Add space before footer
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Add footer text
            Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);
            Paragraph footer = new Paragraph("Thank you for booking with us. For any inquiries, contact support@example.com", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

