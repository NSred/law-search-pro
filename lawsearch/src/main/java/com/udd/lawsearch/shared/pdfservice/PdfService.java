package com.udd.lawsearch.shared.pdfservice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfService {
    public PdfContentData getContentData(InputStream stream) {
        try (PDDocument document = PDDocument.load(stream)){
            PDFTextStripper textStripper = new PDFTextStripper();
            String content = textStripper.getText(document);
            return extractPersonInfo(content);
        } catch (IOException e) {
            throw new RuntimeException("Greksa pri konvertovanju dokumenta u pdf");
        }
    }

    private PdfContentData extractPersonInfo(String content) {
        // Find the starting index of "Potpisnik ugovora za klijenta"
        int index = content.indexOf("Potpisnik ugovora za klijenta");

        if (index != -1) {
            // Initialize counters for the occurrences of '<'
            int count = 0;
            int endIndex = index - 4; // End index for the name search
            String ime = null;
            String prezime = null;

            // Search backwards from "Potpisnik ugovora za klijenta"
            for (int i = index; i >= 0; i--){
                 if (content.charAt(i) == '<') {
                    count++; // Increment count when '<' is found
                    if (count == 1) {
                        // The first '<' found; extract the last name
                        prezime = content.substring(i + 1, endIndex);
                        endIndex = i - 2;
                    } else if (count == 2) {
                        // The second '<' found; extract the first name
                        ime = content.substring(i + 1, endIndex);
                        break; // Break the loop as both names are found
                    }
                }
            }

            if (ime != null && prezime != null) {
                return new PdfContentData(ime, prezime, content);
            }
        }
        return new PdfContentData("Imenko", "Prezimic", content);
    }
}
