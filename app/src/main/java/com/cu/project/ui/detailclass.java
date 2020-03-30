package com.cu.project.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.cu.project.APIHelper.ApiDelete;

import com.cu.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.cu.project.R.layout.accomplishments_details;

public class detailclass  extends AppCompatActivity {


    Context context;

    FloatingActionButton downloadfbtn, deletebtn;
    TextView Title, Date, URL, publisher, appnumber, authname;
    TextView tohideurl, tohidepublisher, tohideapp, tohideauthor, typetext;
    TextView des;

    private View popupInputDialogView = null;
    EditText passwordEditText;
    private TextView cancelUserDataButton;
    private TextView saveUserDataButton;
    String id = null, type = null;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<String> pnos = new ArrayList<>();
    private ArrayList<String> details = new ArrayList<>();
    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(accomplishments_details);

        context = this;
        Bundle bundle = getIntent().getExtras();

        HashMap<String, ArrayList> info = (HashMap<String, ArrayList>) bundle.get("info");


        tohideurl = findViewById(R.id.textView10);
        tohidepublisher = findViewById(R.id.textView14);
        tohideapp = findViewById(R.id.textView16);
        tohideauthor = findViewById(R.id.textview16);

        Title = findViewById(R.id.textView8);
        Date = findViewById(R.id.dateid);
        URL = findViewById(R.id.textView12);
        des = findViewById(R.id.editText);
        publisher = findViewById(R.id.textView15);
        appnumber = findViewById(R.id.textView17);
        authname = findViewById(R.id.names);
        typetext = findViewById(R.id.typetext);


        names = info.get("1");
        emails = info.get("2");
        pnos = info.get("3");
        details = info.get("4");

        String authdetails = "";

        for (int i = 0; i < names.size(); i++) {
            authdetails += names.get(i) + "\n";
        }


        int last = details.size();

        type = details.get(last - 2);
        id = details.get(last - 1);

        typetext.setText(type);

        Log.e("TYPE ", type);


        if (type.equals("HonorsandAward")) {

            Title.setText(details.get(0));
            tohideurl.setText("Issuer");
            URL.setText(details.get(1));

            long date1 = Long.parseLong(details.get(2));

            DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);

            des.setText(details.get(3));

            tohidepublisher.setVisibility(View.GONE);
            tohideapp.setVisibility(View.GONE);
            tohideauthor.setVisibility(View.GONE);
            authname.setVisibility(View.GONE);
            publisher.setVisibility(View.GONE);
            appnumber.setVisibility(View.GONE);


        } else if (type.equals("Publication")) {

            Title.setText(details.get(0));
            publisher.setText(details.get(1));

            long date1 = Long.parseLong(details.get(2));

            DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);

            des.setText(details.get(3));
            URL.setText(details.get(4));
            tohidepublisher.setText("Publication or Publisher");


            tohideapp.setVisibility(View.VISIBLE);
            tohideapp.setText("Type");

            appnumber.setVisibility(View.VISIBLE);
            appnumber.setText(details.get(5));

            authname.setText(authdetails);

        } else if (type.equals("Patent")) {

            Title.setText(details.get(0));
            tohidepublisher.setText("Patent Office");
            publisher.setText(details.get(1));
            appnumber.setText(details.get(2));

            long date1 = Long.parseLong(details.get(3));

            DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);
            Date.setText(hdate);

            des.setText(details.get(4));
            URL.setText(details.get(5));
            authname.setText(authdetails);
            tohideauthor.setText("Inventors");


        } else {
            Title.setText(details.get(0));


            long date1 = Long.parseLong(details.get(1));

            DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);
            URL.setText(details.get(2));
            des.setText(details.get(3));

            authname.setText(authdetails);

            tohidepublisher.setVisibility(View.GONE);
            publisher.setVisibility(View.GONE);
            tohideapp.setVisibility(View.GONE);
            appnumber.setVisibility(View.GONE);
            tohideauthor.setText("Creators");


        }
        deletebtn = findViewById(R.id.deletepaperid);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(detailclass.this);
                alertDialogBuilder.setCancelable(false);

                initPopupViewControls();

                alertDialogBuilder.setView(popupInputDialogView);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Your Data will be removed permanently", Toast.LENGTH_SHORT).show();

                        if (passwordEditText.getText().toString().trim().equals("")) {
                            passwordEditText.setError("Field cannot be empty");
                        } else {
                            String pass = passwordEditText.getText().toString().trim();
                            alertDialog.cancel();
                            if (type.equals("Honors_and_Award"))
                                type = "HonorsandAward";

                            ApiDelete apiDelete = new ApiDelete(detailclass.this, id, type, pass);
                            apiDelete.execute();
                        }


                    }
                });

                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });



        downloadfbtn = findViewById(R.id.floatingActionButton1);



        downloadfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission , STORAGE_CODE);
                    }
                    else{
                        if(type.equals("Honors_and_Award"))
                        {
                            savePdfhonor();
                        }
                        else if(type.equals("Patent"))
                        {
                            savePdfpatent();
                        }
                        else if(type.equals("Publication"))
                        {
                            savePdfpub();
                        }
                        else
                        {
                            savePdf();
                        }

                    }
                }
                else{
                    if(type.equals("Honors_and_Award"))
                    {
                        savePdfhonor();
                    }
                    else if(type.equals("Patent"))
                    {
                        savePdfpatent();
                    }
                    else if(type.equals("Publication"))
                    {
                        savePdfpub();
                    }
                    else
                    {
                        savePdf();
                    }
                }
            }
        });
    }

    private String savePdfhonor() {

        Document mDoc = new Document();

        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {

            PdfWriter.getInstance(mDoc , new FileOutputStream(mFilePath));

            mDoc.open();


            float mHeadingFontSize = 35.0f;
            float mValueFontSize = 20.0f;

            Font font1 = new Font();
            font1.setSize(mHeadingFontSize);
            font1.setColor(BaseColor.RED);
            font1.setStyle(Font.BOLD);

            Font font2 = new Font();
            font2.setSize(mValueFontSize);


            mDoc.setPageSize(PageSize.A4);

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(20);
            paragraph.setIndentationRight(20);
            paragraph.setFont(font1);
            paragraph.add(Title.getText().toString());


            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            paragraph1.setIndentationLeft(20);
            paragraph1.setIndentationRight(20);
            paragraph1.setFont(font2);
            paragraph1.add(des.getText().toString());


            Paragraph paragraph2 = new Paragraph();
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            paragraph2.setIndentationLeft(20);
            paragraph2.setIndentationRight(20);
            paragraph2.setFont(font2);
            String url = "URL: " + URL.getText().toString();
            paragraph2.add(url);


            Paragraph paragraph3 = new Paragraph();
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            paragraph3.setFont(font2);
            paragraph3.setIndentationRight(20);
            paragraph3.setIndentationLeft(20);
            paragraph3.add(Date.getText().toString());



            mDoc.add(paragraph);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph3);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph2);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph1);


            mDoc.close();


            Toast.makeText(this , "File Saved" , Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this  ,   e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        return mFilePath;

    }



    private String savePdfpub() {

        Document mDoc = new Document(PageSize.A4);

        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {

            PdfWriter.getInstance(mDoc , new FileOutputStream(mFilePath));

            mDoc.open();


            float mHeadingFontSize = 35.0f;
            float mValueFontSize = 20.0f;

            Font font1 = new Font();
            font1.setSize(mHeadingFontSize);
            font1.setColor(BaseColor.RED);
            font1.setStyle(Font.BOLD);

            Font font2 = new Font();
            font2.setSize(mValueFontSize);

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(20);
            paragraph.setIndentationRight(20);
            paragraph.setFont(font1);
            paragraph.add(Title.getText().toString());


            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            paragraph1.setIndentationLeft(20);
            paragraph1.setIndentationRight(20);
            paragraph1.setFont(font2);
            paragraph1.add(des.getText().toString());

            Paragraph paragraph3 = new Paragraph();
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            paragraph3.setFont(font2);
            paragraph3.setIndentationRight(20);
            paragraph3.setIndentationLeft(20);
            paragraph3.add(Date.getText().toString());



            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));


            Font desfont = new Font(Font.FontFamily.TIMES_ROMAN , 14);

            float[] columnWidths = {1.5f, 5f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(90f);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1 , bfBold12);
            insertCell(table, "Email", Element.ALIGN_LEFT, 1 , bfBold12);
            insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1 , bfBold12);
            table.setHeaderRows(1);


            for(int i =0;i < names.size(); i ++)
            {
                insertCell(table , names.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                insertCell(table , emails.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                insertCell(table , pnos.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
            }


            float[] columnWidths1 = {5f};
            PdfPTable tabledes = new PdfPTable(columnWidths1);
            tabledes.setWidthPercentage(90f);
            insertCell(tabledes , des.getText().toString() , Element.ALIGN_LEFT , 1 , desfont);


            Paragraph paragraph2 = new Paragraph();
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            paragraph2.setIndentationLeft(20);
            paragraph2.setIndentationRight(20);
            paragraph2.setFont(font2);
            String url = URL.getText().toString();
            paragraph2.add(url);

            Paragraph paragraph4 = new Paragraph();
            paragraph4.setAlignment(Element.ALIGN_LEFT);
            paragraph4.setIndentationLeft(20);
            paragraph4.setIndentationRight(20);
            paragraph4.setFont(font2);
            String publisher1 = publisher.getText().toString();
            paragraph4.add(publisher1);



            Font bold = new Font();
            bold.setStyle(Font.BOLD);
            bold.setSize(20);

            Paragraph p1 = new Paragraph();

            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(20);
            p1.setFont(bold);
            p1.add("Description");

            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(20);
            p2.setFont(bold);
            p2.add("Authors");

            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(20);
            p3.setFont(bold);
            p3.add("URL");

            Paragraph p4 = new Paragraph();
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setIndentationLeft(20);
            p4.setIndentationRight(20);
            p4.setFont(bold);
            p4.add("Publisher");


            mDoc.add(paragraph); // title
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph3); // date
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p1);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(tabledes); // description
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p2);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(table); // tables containing author details
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p3);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph2); // url
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p4);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph4);


            mDoc.close();



            Toast.makeText(this , "File Saved" , Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this  ,   e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        return mFilePath;


    }
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }



    private String savePdfpatent() {

        Document mDoc = new Document(PageSize.A4);

        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {

            PdfWriter.getInstance(mDoc , new FileOutputStream(mFilePath));

            mDoc.open();


            float mHeadingFontSize = 35.0f;
            float mValueFontSize = 20.0f;

            Font font1 = new Font();
            font1.setSize(mHeadingFontSize);
            font1.setColor(BaseColor.RED);
            font1.setStyle(Font.BOLD);

            Font font2 = new Font();
            font2.setSize(mValueFontSize);

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(20);
            paragraph.setIndentationRight(20);
            paragraph.setFont(font1);
            paragraph.add(Title.getText().toString());


            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            paragraph1.setIndentationLeft(20);
            paragraph1.setIndentationRight(20);
            paragraph1.setFont(font2);
            paragraph1.add(des.getText().toString());

            Paragraph paragraph3 = new Paragraph();
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            paragraph3.setFont(font2);
            paragraph3.setIndentationRight(20);
            paragraph3.setIndentationLeft(20);
            paragraph3.add(Date.getText().toString());



            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Font desfont = new Font(Font.FontFamily.TIMES_ROMAN , 14);

            float[] columnWidths = {1.5f, 5f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(90f);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1 , bfBold12);
            insertCell(table, "Email", Element.ALIGN_LEFT, 1 , bfBold12);
            insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1 , bfBold12);
            table.setHeaderRows(1);


            for(int i =0;i < names.size(); i ++)
            {
                insertCell(table , names.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                insertCell(table , emails.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                insertCell(table , pnos.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
            }


            float[] columnWidths1 = {5f};
            PdfPTable tabledes = new PdfPTable(columnWidths1);
            tabledes.setWidthPercentage(90f);
            insertCell(tabledes , des.getText().toString() , Element.ALIGN_LEFT , 1 , desfont);


            Paragraph paragraph2 = new Paragraph();
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            paragraph2.setIndentationLeft(20);
            paragraph2.setIndentationRight(20);
            paragraph2.setFont(font2);
            String url = URL.getText().toString();
            paragraph2.add(url);

            Paragraph paragraph4 = new Paragraph();
            paragraph4.setAlignment(Element.ALIGN_LEFT);
            paragraph4.setIndentationLeft(20);
            paragraph4.setIndentationRight(20);
            paragraph4.setFont(font2);
            String publisher1 = publisher.getText().toString();
            paragraph4.add(publisher1);

            Paragraph paragraph5 = new Paragraph();
            paragraph5.setAlignment(Element.ALIGN_LEFT);
            paragraph5.setIndentationLeft(20);
            paragraph5.setIndentationRight(20);
            paragraph5.setFont(font2);
            String publisher2 = appnumber.getText().toString();
            paragraph5.add(publisher2);



            Font bold = new Font();
            bold.setStyle(Font.BOLD);
            bold.setSize(20);

            Paragraph p1 = new Paragraph();

            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setIndentationLeft(20);
            p1.setIndentationRight(20);
            p1.setFont(bold);
            p1.add("Description");

            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setIndentationLeft(20);
            p2.setIndentationRight(20);
            p2.setFont(bold);
            p2.add("Inventors");

            Paragraph p3 = new Paragraph();
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setIndentationLeft(20);
            p3.setIndentationRight(20);
            p3.setFont(bold);
            p3.add("URL");

            Paragraph p = new Paragraph();
            p.setAlignment(Element.ALIGN_LEFT);
            p.setIndentationLeft(20);
            p.setIndentationRight(20);
            p.setFont(bold);
            p.add("Application Number");

            Paragraph p4 = new Paragraph();
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setIndentationLeft(20);
            p4.setIndentationRight(20);
            p4.setFont(bold);
            p4.add("Patent Office");


            mDoc.add(paragraph); // title
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph3); // date
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p1);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(tabledes); // description
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p2);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(table); // tables containing author details
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p3);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph2); // url
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph5);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));
            mDoc.add(p4);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(paragraph4);
            mDoc.add(new Paragraph("\n"));
            mDoc.add(new Paragraph("\n"));



            mDoc.close();



            Toast.makeText(this , "File Saved" , Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this  ,   e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return mFilePath;
    }



    private String savePdf() {

            Document mDoc = new Document(PageSize.A4);

            String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(System.currentTimeMillis());

            String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

            try {

                PdfWriter.getInstance(mDoc , new FileOutputStream(mFilePath));

                mDoc.open();


                float mHeadingFontSize = 35.0f;
                float mValueFontSize = 20.0f;

                Font font1 = new Font();
                font1.setSize(mHeadingFontSize);
                font1.setColor(BaseColor.RED);
                font1.setStyle(Font.BOLD);

                Font font2 = new Font();
                font2.setSize(mValueFontSize);

                Paragraph paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.setIndentationLeft(20);
                paragraph.setIndentationRight(20);
                paragraph.setFont(font1);
                paragraph.add(Title.getText().toString());


                Paragraph paragraph1 = new Paragraph();
                paragraph1.setAlignment(Element.ALIGN_LEFT);
                paragraph1.setIndentationLeft(20);
                paragraph1.setIndentationRight(20);
                paragraph1.setFont(font2);
                paragraph1.add(des.getText().toString());

                Paragraph paragraph3 = new Paragraph();
                paragraph3.setAlignment(Element.ALIGN_RIGHT);
                paragraph3.setFont(font2);
                paragraph3.setIndentationRight(20);
                paragraph3.setIndentationLeft(20);
                paragraph3.add(Date.getText().toString());



                Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
                Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


                Font desfont = new Font(Font.FontFamily.TIMES_ROMAN , 14);

                float[] columnWidths = {1.5f, 5f, 2f};
                //create PDF table with the given widths
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(90f);
                insertCell(table, "Name", Element.ALIGN_LEFT, 1 , bfBold12);
                insertCell(table, "Email", Element.ALIGN_LEFT, 1 , bfBold12);
                insertCell(table, "Phone Number", Element.ALIGN_LEFT, 1 , bfBold12);
                table.setHeaderRows(1);


                for(int i =0;i < names.size(); i ++)
                {
                    insertCell(table , names.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                    insertCell(table , emails.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                    insertCell(table , pnos.get(i) ,Element.ALIGN_LEFT, 1 , desfont);
                }


                float[] columnWidths1 = {5f};
                PdfPTable tabledes = new PdfPTable(columnWidths1);
                tabledes.setWidthPercentage(90f);
                insertCell(tabledes , URL.getText().toString() , Element.ALIGN_LEFT , 1 , desfont);


                Paragraph paragraph2 = new Paragraph();
                paragraph2.setAlignment(Element.ALIGN_LEFT);
                paragraph2.setIndentationLeft(20);
                paragraph2.setIndentationRight(20);
                paragraph2.setFont(font2);
                String url = des.getText().toString();
                paragraph2.add(url);



                Font bold = new Font();
                bold.setStyle(Font.BOLD);
                bold.setSize(20);

                Paragraph p1 = new Paragraph();

                p1.setAlignment(Element.ALIGN_LEFT);
                p1.setIndentationLeft(20);
                p1.setIndentationRight(20);
                p1.setFont(bold);
                p1.add("Description");

                Paragraph p2 = new Paragraph();
                p2.setAlignment(Element.ALIGN_LEFT);
                p2.setIndentationLeft(20);
                p2.setIndentationRight(20);
                p2.setFont(bold);
                p2.add("Creators");

                Paragraph p3 = new Paragraph();
                p3.setAlignment(Element.ALIGN_LEFT);
                p3.setIndentationLeft(20);
                p3.setIndentationRight(20);
                p3.setFont(bold);
                p3.add("URL");


                mDoc.add(paragraph); // title
                mDoc.add(new Paragraph("\n"));
                mDoc.add(new Paragraph("\n"));
                mDoc.add(paragraph3); // date
                mDoc.add(new Paragraph("\n"));
                mDoc.add(new Paragraph("\n"));
                mDoc.add(p1);
                mDoc.add(new Paragraph("\n"));
                mDoc.add(tabledes); // description
                mDoc.add(new Paragraph("\n"));
                mDoc.add(new Paragraph("\n"));
                mDoc.add(p2);
                mDoc.add(new Paragraph("\n"));
                mDoc.add(table); // tables containing author details
                mDoc.add(new Paragraph("\n"));
                mDoc.add(new Paragraph("\n"));
                mDoc.add(p3);
                mDoc.add(new Paragraph("\n"));
                mDoc.add(paragraph2); // url

                mDoc.close();



                Toast.makeText(this , "File Saved" , Toast.LENGTH_SHORT).show();

            }catch (Exception e)
            {
                Toast.makeText(this  ,   e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        return mFilePath;

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case STORAGE_CODE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(type.equals("Honors_and_Award"))
                    {
                        savePdfhonor();
                    }
                    else if(type.equals("Patent"))
                    {
                        savePdfpatent();
                    }
                    else if(type.equals("Publication"))
                    {
                        savePdfpub();
                    }
                    else
                    {
                        savePdf();
                    }
                }
                else{
                    Toast.makeText(this , "Permission Denied" , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initPopupViewControls() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(detailclass.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.deletepopup, null);

        passwordEditText = popupInputDialogView.findViewById(R.id.pass1);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);


    }
}