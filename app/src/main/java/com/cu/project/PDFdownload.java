package com.cu.project;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.Profiile.Author;
import com.cu.project.ui.Profiile.PageAdapter;
import com.cu.project.ui.Profiile.Pdfmaterial;
import com.cu.project.ui.Profiile.honorpdf;
import com.cu.project.ui.Profiile.patentpdf;
import com.cu.project.ui.Profiile.projectpdf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfAcroForm;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class PDFdownload {


    HashMap<String, ArrayList> mp;
    Context context;

    Document mDoc;
    Font heading_font;
    Font subheading_font;
    Font textfont;
    Font bfBold12;

    float mHeadingFontSize = 12.0f;
    float mValueFontSize = 20.0f;
    float subfontsize = 15.0f;

    String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
    String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

    public PDFdownload(HashMap<String, ArrayList> mp, Context context) throws FileNotFoundException, DocumentException {

        this.mp = mp;
        this.context = context;

        mDoc = new Document(PageSize.A4);

        textfont = new Font();
        textfont.setSize(mHeadingFontSize);
        textfont.setColor(BaseColor.BLACK);
        textfont.setStyle(Font.NORMAL);

        heading_font = new Font();
        heading_font.setSize(mValueFontSize);
        heading_font.setColor(BaseColor.RED);
        heading_font.setStyle(Font.BOLD);

        subheading_font = new Font();
        subheading_font.setSize(subfontsize);
        subheading_font.setColor(BaseColor.RED);
        subheading_font.setStyle(Font.BOLD);

        bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));

        PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));

    }

    public void downloadfile() throws DocumentException, IOException {

        mDoc.open();

        header(mDoc);

        Iterator<Map.Entry<String, ArrayList>>
                iterator = mp.entrySet().iterator();

        // flag to store result
        boolean isKeyPresent1 = false;
        boolean isKeyPresent2 = false;
        boolean isKeyPresent3 = false;
        boolean isKeyPresent4 = false;

        String keyToBeChecked1 = "1";
        String keyToBeChecked2 = "2";
        String keyToBeChecked3 = "3";
        String keyToBeChecked4 = "4";

        // Iterate over the HashMap
        while (iterator.hasNext()) {

            // Get the entry at this iteration
            Map.Entry<String, ArrayList>
                    entry
                    = iterator.next();

            if (keyToBeChecked1.equals(entry.getKey())) {
                isKeyPresent1 = true;
            }
            if (keyToBeChecked2.equals(entry.getKey())) {
                isKeyPresent2 = true;
            }
            if (keyToBeChecked3.equals(entry.getKey())) {
                isKeyPresent3 = true;
            }
            if (keyToBeChecked4.equals(entry.getKey())) {
                isKeyPresent4 = true;
            }
        }

        if (isKeyPresent1 && isKeyPresent2 && isKeyPresent3 && isKeyPresent4) {
            ArrayList<Pdfmaterial> list1 = mp.get("1");
            ArrayList<patentpdf> list2 = mp.get("2");
            ArrayList<projectpdf> list3 = mp.get("3");
            ArrayList<honorpdf> list4 = mp.get("4");

            savepub(list1);
            savepatent(list2);
            saveproject(list3);
            savehonors(list4);
        } else {

            if (isKeyPresent1) {

                ArrayList<Pdfmaterial> list = mp.get("1");
                savepub(list);

            } else if (isKeyPresent2) {
                ArrayList<patentpdf> list = mp.get("2");
                savepatent(list);

            } else if (isKeyPresent3) {
                ArrayList<projectpdf> list = mp.get("3");
                saveproject(list);

            } else if (isKeyPresent4) {
                ArrayList<honorpdf> list = mp.get("4");
                savehonors(list);

            } else {


            }
        }
        mDoc.close();

    }

    private void header(Document mDoc) throws DocumentException, IOException {

        Paragraph heading = new Paragraph();
        heading.setAlignment(Element.ALIGN_CENTER);
        heading.setFont(heading_font);
        heading.add("Full Report");


        Paragraph username = new Paragraph();
        username.setIndentationLeft(20);
        username.setIndentationRight(50);
        username.setFont(textfont);
        username.setAlignment(Element.ALIGN_LEFT);
        username.add("Name : Tushar Tambi"); // name of the user

        Paragraph useremail = new Paragraph();
        useremail.setIndentationLeft(20);
        useremail.setIndentationRight(50);
        useremail.setFont(textfont);
        useremail.setAlignment(Element.ALIGN_LEFT);
        useremail.add("Email : tushartambi@gmail.com"); // email id of user


        Paragraph userpno = new Paragraph();
        userpno.setIndentationLeft(20);
        userpno.setIndentationRight(50);
        userpno.setFont(textfont);
        userpno.setAlignment(Element.ALIGN_LEFT);
        userpno.add("Contact No. : 8426988382"); // Phone number of user

        Paragraph usereid = new Paragraph();
        usereid.setIndentationLeft(20);
        usereid.setIndentationRight(50);
        usereid.setFont(textfont);
        usereid.setAlignment(Element.ALIGN_LEFT);
        usereid.add("Eid : e1101"); // eid of user

        Paragraph userdepart = new Paragraph();
        userdepart.setIndentationLeft(20);
        userdepart.setIndentationRight(50);
        userdepart.setFont(textfont);
        userdepart.setAlignment(Element.ALIGN_LEFT);
        userdepart.add("Department : Computer Science And Engineering"); // department of user

        Paragraph userquali = new Paragraph();
        userquali.setIndentationLeft(20);
        userquali.setIndentationRight(50);
        userquali.setFont(textfont);
        userquali.setAlignment(Element.ALIGN_LEFT);
        userquali.add("Qualification : Phd. in Maths"); // Qualification of user

        Paragraph userdob = new Paragraph();
        userdob.setIndentationLeft(20);
        userdob.setIndentationRight(50);
        userdob.setFont(textfont);
        userdob.setAlignment(Element.ALIGN_LEFT);
        userdob.add("Date of Birth : 09 Oct. 2000"); // DOB of user

        Paragraph userdoj = new Paragraph();
        userdoj.setIndentationLeft(20);
        userdoj.setIndentationRight(50);
        userdoj.setFont(textfont);
        userdoj.setAlignment(Element.ALIGN_LEFT);
        userdoj.add("Date of Joining : 09 July. 2016"); // DOB of user

        mDoc.add(new Paragraph(heading));
        mDoc.add(new Paragraph("\n\n"));
        mDoc.add(username); // adding name
        mDoc.add(new Paragraph("\n"));
        mDoc.add(useremail); // adding email
        mDoc.add(new Paragraph("\n"));
        mDoc.add(userpno); // adding pno
        mDoc.add(new Paragraph("\n"));
        mDoc.add(usereid);
        mDoc.add(new Paragraph("\n"));
        mDoc.add(userdepart);
        mDoc.add(new Paragraph("\n"));
        mDoc.add(userquali);
        mDoc.add(new Paragraph("\n"));
        mDoc.add(userdob);
        mDoc.add(new Paragraph("\n"));
        mDoc.add(userdoj);
        mDoc.add(new Paragraph("\n"));

//            mDoc.close();

    }

    private void savepub(ArrayList<Pdfmaterial> publist) throws DocumentException {

        Paragraph heading = new Paragraph();
        heading.setFont(heading_font);
        heading.setAlignment(Element.ALIGN_LEFT);
        heading.setIndentationRight(20);
        heading.setIndentationLeft(20);
        heading.add("Publication");


        mDoc.add(new Paragraph(heading));
        mDoc.add(new Paragraph("\n\n"));

        for (int i = 0; i < publist.size(); i++) {
            Pdfmaterial pdfmaterial = publist.get(i);


            Paragraph p1 = new Paragraph();
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(50);
            p1.setFont(subheading_font);
            p1.setSpacingAfter(8.0f);
            p1.add(pdfmaterial.getTitle());


            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(50);
            p2.setFont(textfont);
            p2.setSpacingAfter(8.0f);
            p2.add("Date : " + pdfmaterial.getDate());


            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(50);
            p3.setFont(textfont);
            p3.setSpacingAfter(8.0f);
            p3.add("Url : " + pdfmaterial.getURL());


            Paragraph p4 = new Paragraph();
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setIndentationLeft(20);
            p4.setIndentationRight(50);
            p4.setFont(textfont);
            p4.setSpacingAfter(8.0f);
            p4.add("Publisher : " + pdfmaterial.getPublisher());

            Paragraph p5 = new Paragraph();
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setIndentationLeft(20);
            p5.setIndentationRight(50);
            p5.setFont(textfont);
            p5.setSpacingAfter(16.0f);
            p5.add("Description : " + pdfmaterial.getDes());

            Paragraph p6 = new Paragraph();
            p6.setAlignment(Element.ALIGN_LEFT);
            p6.setIndentationLeft(20);
            p6.setSpacingAfter(8.0f);
            p6.setIndentationRight(50);
            p6.setFont(bfBold12);

            p6.add("Authors");

            Font desfont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

            float[] columnWidths = {1f, 3f, 5f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(90f);
            insertCell(table, "S.No.", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Email", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1, bfBold12);
            table.setHeaderRows(1);
            table.setSpacingBefore(8.0f);
            table.setPaddingTop(16.0f);

            ArrayList<Author> author2 = pdfmaterial.getAuthor();

            for (int j = 0; j < author2.size(); j++) {
                Author author1 = author2.get(j);

                insertCell(table, String.valueOf(j + 1), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getName(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getEmail(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getPno(), Element.ALIGN_LEFT, 1, desfont);

            }


            mDoc.add(new Paragraph(p1));

            mDoc.add(new Paragraph(p2));

            mDoc.add(new Paragraph(p3));

            mDoc.add(new Paragraph(p4));
            mDoc.add(new Paragraph(p5));
            mDoc.add(new Paragraph(p6));
            mDoc.add(table);
            mDoc.add(new Paragraph("\n\n"));


        }

    }


    private void savepatent(ArrayList<patentpdf> patentlist) throws DocumentException {
        Paragraph heading = new Paragraph();
        heading.setFont(heading_font);
        heading.setAlignment(Element.ALIGN_LEFT);
        heading.setIndentationRight(20);
        heading.setIndentationLeft(20);
        heading.add("Patents");


        mDoc.add(new Paragraph(heading));
        mDoc.add(new Paragraph("\n\n"));

        for (int i = 0; i < patentlist.size(); i++) {
            patentpdf pdfmaterial = patentlist.get(i);


            Paragraph p1 = new Paragraph();
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(50);
            p1.setFont(subheading_font);
            p1.setSpacingAfter(8.0f);
            p1.add(pdfmaterial.getTitle());


            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(50);
            p2.setFont(textfont);
            p2.setSpacingAfter(8.0f);
            p2.add("Date : " + pdfmaterial.getDate());


            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(50);
            p3.setFont(textfont);
            p3.setSpacingAfter(8.0f);
            p3.add("Url : " + pdfmaterial.getUrl());


            Paragraph p4 = new Paragraph();
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setIndentationLeft(20);
            p4.setIndentationRight(50);
            p4.setFont(textfont);
            p4.setSpacingAfter(8.0f);
            p4.add("Patent Office : " + pdfmaterial.getOffice());

            Paragraph p7 = new Paragraph();
            p7.setAlignment(Element.ALIGN_LEFT);
            p7.setIndentationLeft(20);
            p7.setIndentationRight(50);
            p7.setFont(textfont);
            p7.setSpacingAfter(8.0f);
            p7.add("Application Number : " + pdfmaterial.getAppno());

            Paragraph p5 = new Paragraph();
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setIndentationLeft(20);
            p5.setIndentationRight(50);
            p5.setFont(textfont);
            p5.setSpacingAfter(16.0f);
            p5.add("Description : " + pdfmaterial.getDes());

            Paragraph p6 = new Paragraph();
            p6.setAlignment(Element.ALIGN_LEFT);
            p6.setIndentationLeft(20);
            p6.setSpacingAfter(8.0f);
            p6.setIndentationRight(50);
            p6.setFont(bfBold12);

            p6.add("Inventors");

            Font desfont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

            float[] columnWidths = {1f, 3f, 5f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(90f);
            insertCell(table, "S.No.", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Email", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1, bfBold12);
            table.setHeaderRows(1);
            table.setSpacingBefore(8.0f);
            table.setPaddingTop(16.0f);

            ArrayList<Author> author2 = pdfmaterial.getArrayList();

            for (int j = 0; j < author2.size(); j++) {
                Author author1 = author2.get(j);

                insertCell(table, String.valueOf(j + 1), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getName(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getEmail(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getPno(), Element.ALIGN_LEFT, 1, desfont);

            }


            mDoc.add(new Paragraph(p1));

            mDoc.add(new Paragraph(p2));

            mDoc.add(new Paragraph(p3));

            mDoc.add(new Paragraph(p4));
            mDoc.add(new Paragraph(p7));
            mDoc.add(new Paragraph(p5));
            mDoc.add(new Paragraph(p6));
            mDoc.add(table);
            mDoc.add(new Paragraph("\n\n"));


        }

    }


    private void saveproject(ArrayList<projectpdf> propdf) throws DocumentException {

        Paragraph heading = new Paragraph();
        heading.setFont(heading_font);
        heading.setAlignment(Element.ALIGN_LEFT);
        heading.setIndentationRight(20);
        heading.setIndentationLeft(20);
        heading.add("Projects");


        mDoc.add(new Paragraph(heading));
        mDoc.add(new Paragraph("\n\n"));

        for (int i = 0; i < propdf.size(); i++) {
            projectpdf pdfmaterial = propdf.get(i);


            Paragraph p1 = new Paragraph();
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(50);
            p1.setFont(subheading_font);
            p1.setSpacingAfter(8.0f);
            p1.add(pdfmaterial.getTitle());


            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(50);
            p2.setFont(textfont);
            p2.setSpacingAfter(8.0f);
            p2.add("Date : " + pdfmaterial.getDate());


            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(50);
            p3.setFont(textfont);
            p3.setSpacingAfter(8.0f);
            p3.add("Url : " + pdfmaterial.getUrl());


            Paragraph p5 = new Paragraph();
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setIndentationLeft(20);
            p5.setIndentationRight(50);
            p5.setFont(textfont);
            p5.setSpacingAfter(16.0f);
            p5.add("Description : " + pdfmaterial.getDes());

            Paragraph p6 = new Paragraph();
            p6.setAlignment(Element.ALIGN_LEFT);
            p6.setIndentationLeft(20);
            p6.setSpacingAfter(8.0f);
            p6.setIndentationRight(50);
            p6.setFont(bfBold12);

            p6.add("Creators");

            Font desfont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

            float[] columnWidths = {1f, 3f, 5f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(90f);
            insertCell(table, "S.No.", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Email", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1, bfBold12);
            table.setHeaderRows(1);
            table.setSpacingBefore(8.0f);
            table.setPaddingTop(16.0f);

            ArrayList<Author> author2 = pdfmaterial.getArrayListl();

            for (int j = 0; j < author2.size(); j++) {
                Author author1 = author2.get(j);

                insertCell(table, String.valueOf(j + 1), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getName(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getEmail(), Element.ALIGN_LEFT, 1, desfont);
                insertCell(table, author1.getPno(), Element.ALIGN_LEFT, 1, desfont);

            }


            mDoc.add(new Paragraph(p1));

            mDoc.add(new Paragraph(p2));

            mDoc.add(new Paragraph(p3));
            mDoc.add(new Paragraph(p5));
            mDoc.add(new Paragraph(p6));
            mDoc.add(table);
            mDoc.add(new Paragraph("\n\n"));

        }

    }


    private void savehonors(ArrayList<honorpdf> honorpdfs) throws DocumentException {

        Paragraph heading = new Paragraph();
        heading.setFont(heading_font);
        heading.setAlignment(Element.ALIGN_LEFT);
        heading.setIndentationRight(20);
        heading.setIndentationLeft(20);
        heading.add("Honors and Awards");


        mDoc.add(new Paragraph(heading));
        mDoc.add(new Paragraph("\n\n"));

        for (int i = 0; i < honorpdfs.size(); i++) {

            honorpdf pdfmaterial = honorpdfs.get(i);


            Paragraph p1 = new Paragraph();
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(50);
            p1.setFont(subheading_font);
            p1.setSpacingAfter(8.0f);
            p1.add(pdfmaterial.getTitle());


            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(50);
            p2.setFont(textfont);
            p2.setSpacingAfter(8.0f);
            p2.add("Date : " + pdfmaterial.getDate());


            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(50);
            p3.setFont(textfont);
            p3.setSpacingAfter(8.0f);
            p3.add("Issuer : " + pdfmaterial.getIssuer());


            Paragraph p5 = new Paragraph();
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setIndentationLeft(20);
            p5.setIndentationRight(50);
            p5.setFont(textfont);
            p5.setSpacingAfter(16.0f);
            p5.add("Description : " + pdfmaterial.getDes());


            mDoc.add(new Paragraph(p1));
            mDoc.add(new Paragraph(p2));
            mDoc.add(new Paragraph(p3));
            mDoc.add(new Paragraph(p5));
            mDoc.add(new Paragraph("\n\n"));
        }
    }


    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }


}
