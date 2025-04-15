package com.example.naturelink.service;

import com.example.naturelink.entity.Event;
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

}
