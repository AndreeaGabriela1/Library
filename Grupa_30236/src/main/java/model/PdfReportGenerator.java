package model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.util.List;

public class PdfReportGenerator {

    public static void generateReport(List<Book> soldBooks, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Titlu raport
            Paragraph title = new Paragraph("Selled books report");
            document.add(title);

            // Adăugare informații despre cărțile vândute
            for (Book book : soldBooks) {
                Paragraph para = new Paragraph("Title: " + book.getTitle() +
                        "\nAuthor: " + book.getAuthor() +
                        "\nPrice: " + book.getPrice() +
                        "\nSold quantity: " + book.getQuantity()); // Presupunând că ai un câmp pentru cantitatea vândută
                document.add(para);
            }

            document.close();
            System.out.println("Raportul PDF a fost generat cu succes!");
        } catch (FileNotFoundException e) {
            System.err.println("Fișierul nu a fost găsit: " + e.getMessage());
        }
    }
}
