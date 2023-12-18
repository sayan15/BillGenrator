package com.example.gasbill_generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class Pdf_Creator {
    public void convertNodeToPdf(BillGenerator billGenerator, String pdfOutputPath) {
            Document document = new Document();

            try {

                PdfWriter.getInstance(document, new FileOutputStream(new File(pdfOutputPath)));

                // Open the document
                document.open();

                // Add a title
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("Monthly Bill", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph());
                document.add(new Paragraph());
                // Create a table
                PdfPTable table = new PdfPTable(2); // Two columns

                // Add content with styling to the table
                Font contentFont = new Font(Font.FontFamily.HELVETICA, 12);
                addRowToTable(table, "Payment", "£" + billGenerator.getPayment(), contentFont);
                addRowToTable(table, "Account Balance", "£" + billGenerator.getAccountBalance(), contentFont);
                addRowToTable(table, "Electric Bill", "£" + billGenerator.getElectricityBill(), contentFont);
                addRowToTable(table, "Gas Bill", "£" + billGenerator.getGasBill(), contentFont);
                addRowToTable(table, "Remaining Balance", "£" + billGenerator.getAccountBalance(), contentFont);
                addRowToTable(table, "Due", "£" + billGenerator.getDue(), contentFont);

                // Add the table to the document
                document.add(table);

                // Close the document
                document.close();

                System.out.println("Done");

            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }


    }

    private static void addRowToTable(PdfPTable table, String label, String value, Font font) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, font));
        PdfPCell cellValue = new PdfPCell(new Phrase(value, font));

        table.addCell(cellLabel);
        table.addCell(cellValue);
    }

}
