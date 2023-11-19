package com.example.resturantsystem.Misc;

import com.example.resturantsystem.DL.OrderDL;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.IOException;
public class ItextPDF {

    private static String getFormatedValue(float value){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        if(value % 1 == 0.0f){ decimalFormat=new DecimalFormat("0"); }
        return decimalFormat.format(value);
    }
    private static String getFormatedValue(String value){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        if(Float.parseFloat(value) % 1 == 0.0f){ decimalFormat=new DecimalFormat("0"); }
        return decimalFormat.format(Float.parseFloat(value));
    }
    public static void printPDF(String pdfFileName) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                File pdfFile = new File(pdfFileName);
                if (pdfFile.exists()) {
                    desktop.print(pdfFile);
                } else {
                    System.out.println("PDF file does not exist.");
                }
            } else {
                System.out.println("Desktop not supported, cannot print PDF.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createXPDF(ArrayList<String[]> perByOrder,String[] orderNum, Date startingDate, Date endingDate,Date issueDate,String name,String title){
        try {
            Document document = new Document(new Rectangle(170,350),10,10,4,4);
            String fileName=name + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
            String currentDateTime = dateTimeFormat.format(issueDate);

            addHeader(document,title);
            addHeader(document,"STREET CAFE", TabStop.Alignment.LEFT);
            addHeader(document,"A.N MNAAEk&EI", TabStop.Alignment.LEFT);
            addHeader(document,"COFFEE SHOP", TabStop.Alignment.LEFT);
            addHeader(document,"Address : ADDRESS 150-150", TabStop.Alignment.LEFT);
            addHeader(document,"ATHENS", TabStop.Alignment.LEFT);


            addHeader(document,"VAT ID NUMBER OF BUSINESS : "+BusinessHandler.getBusinessVatNum(), TabStop.Alignment.LEFT);
            addHeader(document,"Tax office : A athens", TabStop.Alignment.LEFT);
            addHeader(document,"TEL : 2108254002", TabStop.Alignment.LEFT);


            addLineCutter(document);

            addParagraphChunks(document,"DATE AND TIME  : ",currentDateTime);

            addLineCutter(document);

            addBoldParagraph(document,"TOTALS PER % VAT");
            float GrandTotal=0f;
            float TotalVat=0f;
            float CleanTotal=0f;
            float totalCTG=0f;
            StringBuilder perVatStr = new StringBuilder();
            for (String[] subItem:perByOrder){
                //  txt.append("[ 1][              ]    153,93");
                GrandTotal += Float.parseFloat(subItem[1] != null ? subItem[1] : "0");
                TotalVat += Float.parseFloat(subItem[2] != null ? subItem[2] : "0");
                CleanTotal += Float.parseFloat(subItem[3] != null ? subItem[3] : "0");
                perVatStr.append(String.format("  %s   %s€  %s€   %s€ \n",
//                        subItem[0] != null ? subItem[0]+'%' :"0",
//                        subItem[1] != null ? subItem[1]+'%' :"0",
//                        subItem[2] != null ? subItem[2]+'%' :"0",
//                        subItem[3] != null ? subItem[3]+'%' :"0")
                                subItem[0] != null ? getFormatedValue(subItem[0]) :"0",
                                subItem[1] != null ? getFormatedValue(subItem[1]) :"0",
                                subItem[2] != null ? getFormatedValue(subItem[2]) :"0",
                                subItem[3] != null ? getFormatedValue(subItem[3]) :"0")
                );

                totalCTG += subItem[1] !=null ? Float.parseFloat(subItem[1]) :0;
            }
            addPara(document,perVatStr.toString());

            addLineCutter(document);

            addParagraphChunks(document,"GRAND TOTAL            :",getFormatedValue(GrandTotal) + '€');
            addParagraphChunks(document,"TOTAL VAT              :",getFormatedValue(TotalVat)+ '€');
            addParagraphChunks(document,"CLEAN TOTAL            :",getFormatedValue(CleanTotal)+'€');

            addLineCutter(document);
            SimpleDateFormat tofromFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            addParagraphChunks(document,"RECEIPTS NUMBER : ",orderNum[0]+" - "+orderNum[1]);
            addParagraphChunks(document,"FROM : ",tofromFormat.format(startingDate));
            addParagraphChunks(document,"TO : ",tofromFormat.format(endingDate));

            addLineCutter(document);

            addParagraphChunks(document,"CASH : ",getFormatedValue(CleanTotal) +'€');
            addParagraphChunks(document,"GRAND TOTAL : ",getFormatedValue(CleanTotal)+'€');

            addLineCutter(document);

            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createEZPDF(ArrayList<String[]> perByOrder,String[] orderNum,int seqId, Date startingDate, Date endingDate,Date issueDate,String name,String title){
        try {
            Document document = new Document(new Rectangle(170,350),10,10,4,4);
            String fileName=name + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
            String currentDateTime = dateTimeFormat.format(issueDate);

            addHeader(document,title);

            addHeader(document,"STREET CAFE", TabStop.Alignment.LEFT);
            addHeader(document,"A.N MNAAEk&EI", TabStop.Alignment.LEFT);
            addHeader(document,"COFFEE SHOP", TabStop.Alignment.LEFT);
            addHeader(document,"Address : ADDRESS 150-150", TabStop.Alignment.LEFT);
            addHeader(document,"ATHENS", TabStop.Alignment.LEFT);


            addHeader(document,"VAT ID NUMBER OF BUSINESS : "+BusinessHandler.getBusinessVatNum(), TabStop.Alignment.LEFT);
            addHeader(document,"Tax office : A athens", TabStop.Alignment.LEFT);
            addHeader(document,"TEL : 2108254002", TabStop.Alignment.LEFT);

            addLineCutter(document);

            addParagraphChunks(document,"ZOUT  STARTING SEQ id: ",String.valueOf(seqId));
            addParagraphChunks(document,"DATE AND TIME  : ",currentDateTime);

            addLineCutter(document);
            addBoldParagraph(document,"TOTALS PER % VAT");
            float GrandTotal=0f;
            float TotalVat=0f;
            float CleanTotal=0f;
            float totalCTG=0f;
            StringBuilder perVatStr = new StringBuilder();
            for (String[] subItem:perByOrder){
                //  txt.append("[ 1][              ]    153,93");
                GrandTotal += Float.parseFloat(subItem[1] != null ? subItem[1] : "0");
                TotalVat += Float.parseFloat(subItem[2] != null ? subItem[2] : "0");
                CleanTotal += Float.parseFloat(subItem[3] != null ? subItem[3] : "0");
                perVatStr.append(String.format("  %s   %s€  %s€   %s€ \n",
//                        subItem[0] != null ? subItem[0]+'%' :"0",
//                        subItem[1] != null ? subItem[1]+'%' :"0",
//                        subItem[2] != null ? subItem[2]+'%' :"0",
//                        subItem[3] != null ? subItem[3]+'%' :"0")
                        subItem[0] != null ? getFormatedValue(subItem[0]) :"0",
                        subItem[1] != null ? getFormatedValue(subItem[1]) :"0",
                        subItem[2] != null ? getFormatedValue(subItem[2]) :"0",
                        subItem[3] != null ? getFormatedValue(subItem[3]) :"0")
                );

                totalCTG += subItem[1] !=null ? Float.parseFloat(subItem[1]) :0;
            }
            addPara(document,perVatStr.toString());

            addLineCutter(document);

            addParagraphChunks(document,"GRAND TOTAL            :",getFormatedValue(GrandTotal) + '€');
            addParagraphChunks(document,"TOTAL VAT              :",getFormatedValue(TotalVat)+ '€');
            addParagraphChunks(document,"CLEAN TOTAL            :",getFormatedValue(CleanTotal)+'€');

            addLineCutter(document);
            SimpleDateFormat tofromFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            addParagraphChunks(document,"RECEIPTS NUMBER : ",orderNum[0]+" - "+orderNum[1]);
            addParagraphChunks(document,"FROM : ",tofromFormat.format(startingDate));
            addParagraphChunks(document,"TO : ",tofromFormat.format(endingDate));

            addLineCutter(document);

            addParagraphChunks(document,"CASH : ",getFormatedValue(CleanTotal) +'€');
            addParagraphChunks(document,"GRAND TOTAL : ",getFormatedValue(CleanTotal)+'€');

            addLineCutter(document);
            addPara(document,"-------------- END OF DAY ----------------");
            addParagraphChunks(document,"ZOUT CLOSING SEQ id :",String.valueOf(seqId));

            document.close();
//            printPDF(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addBoldParagraph(Document doc,String text) throws DocumentException{
        Font font=new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
        Paragraph p = new Paragraph(text,font);
        p.setAlignment(Element.ALIGN_LEFT);
        doc.add(p);
    }
    public static void addHeader(Document doc,String text) throws DocumentException {
        Font font=new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Paragraph p = new Paragraph(text,font);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }
    public static void addHeader(Document doc,String text,TabStop.Alignment align) throws DocumentException {
        Font font=new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Paragraph p = new Paragraph(text,font);
        p.setAlignment(align.ordinal());
        doc.add(p);
    }

    public static void addPara(Document doc,String text) throws DocumentException {
        Font font=new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL,  BaseColor.DARK_GRAY);
        Paragraph p = new Paragraph(text,font);
//        p.setSpacingAfter(0.0f);
//        p.setSpacingBefore(0.0f);
        p.setAlignment(Element.ALIGN_LEFT);
        doc.add(p);
    }
    public static void addCenterPara(Document doc,String text) throws DocumentException {
        Font font=new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL,  BaseColor.DARK_GRAY);
        Paragraph p = new Paragraph(text,font);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }
    public static void addParagraphChunks(Document doc,String title,String content) throws DocumentException{
        Font dataGreenFont = FontFactory.getFont("Garamond", 6, Font.BOLD,BaseColor.BLACK);
        Font dataBlackFont = FontFactory.getFont("Garamond", 6,Font.NORMAL, BaseColor.DARK_GRAY);
        Paragraph p= new Paragraph();
        p.add(new Chunk(title, dataGreenFont));
        p.add(new Chunk(content, dataBlackFont));
//        p.setSpacingAfter(0.0f);
//        p.setSpacingBefore(0.0f);
        p.setSpacingAfter(0.0f);  // Set spacing after the paragraph (adjust as needed)
        p.setSpacingBefore(0.0f); // Set spacing before the paragraph (adjust as needed)
        p.setLeading(10f);
        doc.add(p);
    }
    public static void addHeaderChunks(Document doc,String title,String content) throws DocumentException{
        Font dataGreenFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Font dataBlackFont = new Font(Font.FontFamily.HELVETICA, 8,Font.NORMAL, BaseColor.DARK_GRAY);
        Paragraph p= new Paragraph();
        p.add(new Chunk(title, dataGreenFont));
        p.add(new Chunk(content, dataBlackFont));
        p.setSpacingAfter(0.0f);  // Set spacing after the paragraph (adjust as needed)
        p.setSpacingBefore(0.0f); // Set spacing before the paragraph (adjust as needed)
        p.setLeading(10f);
        doc.add(p);
    }

    public static void addParagraphCenterChunks(Document doc, String title, String content, TabStop.Alignment center) throws DocumentException{
        Font dataGreenFont = FontFactory.getFont("Garamond", 6, Font.BOLD,BaseColor.BLACK);
        Font dataBlackFont = FontFactory.getFont("Garamond", 6,Font.NORMAL, BaseColor.DARK_GRAY);
        Paragraph p= new Paragraph();
        p.add(new Chunk(title, dataGreenFont));
        p.add(new Chunk(content, dataBlackFont));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(0.0f);  // Set spacing after the paragraph (adjust as needed)
        p.setSpacingBefore(0.0f); // Set spacing before the paragraph (adjust as needed)
        p.setLeading(10f);
        doc.add(p);
    }
    public static void addLineCutter(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setPercentage(100);
        lineSeparator.setLineColor(BaseColor.GRAY);
        lineSeparator.setAlignment(Element.ALIGN_CENTER);
        Chunk lineChunk = new Chunk(lineSeparator);
        document.add(lineChunk);
    }
    public static void createTabularPDF(String Title, ArrayList<String[]> perByCtg,ArrayList<String[]> perByOrder,Integer[] orderNum, String name) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
            document.open();
            Rectangle one = new Rectangle(80,80);
            document.setPageSize(one);

            // Create a table for better formatting
            PdfPTable table = new PdfPTable(new float[] { 0.3f, 0.7f }); // Column widths in proportion (30% and 70%)
            Font titleFont = new Font(Font.FontFamily.COURIER, 20, Font.BOLDITALIC, BaseColor.BLACK);
            Paragraph title = new Paragraph(Title, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10f);
//            table.addCell(new PdfPCell(title));
//            Add horizontal rules
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(100);
            lineSeparator.setAlignment(Element.ALIGN_CENTER);
            Chunk lineChunk = new Chunk(lineSeparator);


            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String currentDateTime = dateTimeFormat.format(new Date());
            Font dateTimeFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC, BaseColor.BLACK);
            Paragraph dateTimeParagraph = new Paragraph("Date and Time: " + currentDateTime, dateTimeFont);

            Font textFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
            StringBuilder txt = new StringBuilder();




            addRow("","",table);
            addRow("","",table);

            txt.append("          STREET CAFE \n");
            txt.append("      A.N MNΑAAEkΑ&EIΑ ΕΕ \n");
            txt.append("          COFFEE SHOP \n");
            txt.append(" \n");
            txt.append("ΚΕ ADDRESS 150-157 \n");
            txt.append("ATHENS 104445 \n");
            txt.append("ΔΟΥ Α ΑΘΗΝΩΝ \n");
            addRow("VAT ID NUMBER OF BUSSINES",BusinessHandler.getBusinessVatNum(),table);
            addRow("TEL 2108254002 ","",table);
            txt.append("----------------------------------------------- "+"\n");
            addRow("ZOUT  STARTING SEQ id: ","1047",table);
            addRow("DATE AND TIME ","11/04/2022"+"ΩΡΑ 23:16 ",table);

            txt.append("-----------------------------------------------  \n");
            txt.append("\n");
            txt.append("TOTALS BY VAT CATEGORY"+" \n");
            txt.append("");
            float totalCTG=0f;
            for (String[] subItem : perByCtg){
//                txt.append("[ 1][              ]    153,93");
                txt.append(String.format("%s [              ]  %s \n", subItem[0], subItem[1] !=null ? subItem[1] :"0"));
                totalCTG += subItem[1] !=null ? Float.parseFloat(subItem[1]) :0;
            }


            txt.append("-----------------------------------------------  \n");
            txt.append(""+" \n");
            txt.append("GRAND TOTAL           "+totalCTG+" \n");
            txt.append("-----------------------------------------------  \n");
            txt.append("\n");
            txt.append("TOTALS PER % VAT"+" \n");
            txt.append("");
            float GrandTotal=0f;
            float TotalVat=0f;
            float CleanTotal=0f;
            for (String[] subItem:perByOrder){
                //  txt.append("[ 1][              ]    153,93");
                GrandTotal += Float.parseFloat(subItem[1] != null ? subItem[1] : "0");
                TotalVat += Float.parseFloat(subItem[2] != null ? subItem[2] : "0");
                CleanTotal += Float.parseFloat(subItem[3] != null ? subItem[3] : "0");
                txt.append(String.format("  %s   %s  %s   %s \n",
                        subItem[0] != null ? subItem[0]+'%' :"0",
                        subItem[1] != null ? subItem[1]+'%' :"0",
                        subItem[2] != null ? subItem[2]+'%' :"0",
                        subItem[3] != null ? subItem[3]+'%' :"0"));

                totalCTG += subItem[1] !=null ? Float.parseFloat(subItem[1]) :0;
            }

            txt.append("-----------------------------------------------  \n");
            txt.append("\n");
            txt.append("GRAND TOTAL            "+GrandTotal+" \n");
            txt.append("TOTAL VAT              "+TotalVat+" \n");
            txt.append("CLEAN TOTAL            "+CleanTotal+" \n");
            txt.append("-----------------------------------------------  \n");
            txt.append("\n");

            txt.append("RECEIPTS NUMBER"+  orderNum[0] + " -- "+ orderNum[1]+" \n");
            txt.append(" \n");
            txt.append("FROM 11.04.2022 14:36"+" \n");
            txt.append("TOO 11.04.2022 23:04"+" \n");
            txt.append("\n");
            txt.append("-----------------------------------------------  \n");
            txt.append("CASH                    "+CleanTotal+" \n");
            txt.append(" \n");
            txt.append("GRAND TOTAL              "+CleanTotal+" \n");
            txt.append("-----------------------------------------------  \n");
            txt.append(" \n");
            txt.append("-----------------------------------------------  \n");
            txt.append("ZOUT CLOSING SEQ id: 1047 \n");

            // Add the text to the table
            PdfPCell textCell = new PdfPCell(new Phrase(txt.toString(), textFont));
            textCell.setVerticalAlignment(Element.ALIGN_LEFT);
            textCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(textCell);

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }
    private static void addRow(String Col1,String Col2,PdfPTable table){
        Font textFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        PdfPCell cell1 = new PdfPCell(new Phrase(Col1));
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(Col2));
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell2);
    }
}
