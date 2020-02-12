package com.cu.project.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.cu.project.APIHelper.ApiDelete;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.Apigetdetails;
import com.cu.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.cu.project.R.layout.accomplishments_details;

public class detailclass  extends AppCompatActivity {


    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;

    private View mRootView;
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


        if (type.equals("Honors_and_Award")) {

            Title.setText(details.get(0));
            tohideurl.setText("Issuer");
            URL.setText(details.get(1));

            long date1 = Long.parseLong(details.get(2));

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

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

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);

            des.setText(details.get(3));
            URL.setText(details.get(4));
            tohidepublisher.setText("Publication or Publisher");


            tohideapp.setVisibility(View.GONE);

            appnumber.setVisibility(View.GONE);

            authname.setText(authdetails);

        } else if (type.equals("Patent")) {

            Title.setText(details.get(0));
            tohidepublisher.setText("Patent Office");
            publisher.setText(details.get(1));
            appnumber.setText(details.get(2));

            long date1 = Long.parseLong(details.get(3));

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

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

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

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
                        savePdf();
                    }
                }
                else{
                    savePdf();
                }
            }
        });
    }

    private void savePdf() {

        Document mDoc = new Document();

        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {


            Bitmap screen = getBitmapFromView(this.getWindow().findViewById(R.id.layout));

            PdfWriter.getInstance(mDoc , new FileOutputStream(mFilePath));

            mDoc.open();

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            ConstraintLayout root = (ConstraintLayout) inflater.inflate(accomplishments_details, null);
            root.setDrawingCacheEnabled(true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            screen.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byteArray = stream.toByteArray();

            Image image = null;

            image = Image.getInstance(byteArray);

            mDoc.add(image);

            Log.e("PRINT FILE NAME" , mFileName);


            mDoc.close();

            Toast.makeText(this , "File Saved" , Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this  ,   e.getMessage() , Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case STORAGE_CODE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf();
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


    public static Bitmap getBitmapFromView(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }


}