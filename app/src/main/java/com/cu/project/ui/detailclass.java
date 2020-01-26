package com.cu.project.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.Apigetdetails;
import com.cu.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class detailclass  extends AppCompatActivity {


    FloatingActionButton downloadfbtn ,deletebtn;
    TextView Title , Date , URL , publisher , appnumber , authname;
    TextView tohideurl , tohidepublisher , tohideapp , tohideauthor, typetext;
    EditText des;

    private View popupInputDialogView = null;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText confirmpassedittext;
    private TextView cancelUserDataButton;
    private TextView saveUserDataButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accomplishments_details);

        Bundle bundle = getIntent().getExtras();



        String[] information = bundle.getStringArray("info");

        for(int i= 0;i < information.length;i++)
            Log.e("INFOEMATION" , information[i]);


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


        int last = information.length;

        String type = information[last - 1];

        typetext.setText(type);

        Log.e("TYPE ", type);


        if(type.equals("Honors_and_Award")){

            Title.setText(information[0]);
            tohideurl.setText("Issuer");
            URL.setText(information[1]);

            long date1 = Long.parseLong(information[2]);

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);

            des.setText(information[3]);

            tohidepublisher.setVisibility(View.GONE);
            tohideapp.setVisibility(View.GONE);
            tohideauthor.setVisibility(View.GONE);
            authname.setVisibility(View.GONE);
            publisher.setVisibility(View.GONE);
            appnumber.setVisibility(View.GONE);


        }
        else if(type.equals("Publication")){

            Title.setText(information[0]);
            publisher.setText(information[1]);

            long date1 = Long.parseLong(information[2]);

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);

            des.setText(information[3]);
            URL.setText(information[4]);
            tohidepublisher.setText("Publication or Publisher");


            tohideapp.setVisibility(View.GONE);

            appnumber.setVisibility(View.GONE);

        }
        else if(type.equals("Patent"))
        {

            Title.setText(information[0]);
            tohidepublisher.setText("Patent Office");
            publisher.setText(information[1]);
            appnumber.setText(information[2]);

            long date1 = Long.parseLong(information[3]);

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);
            Date.setText(hdate);

            des.setText(information[4]);
            URL.setText(information[5]);
            tohideauthor.setText("Inventors");




        }
        else{
            Title.setText(information[0]);


            long date1 = Long.parseLong(information[1]);

            DateFormat simple = new SimpleDateFormat("dd MM yyyy");

            java.util.Date result1 = new Date(date1);

            String hdate = simple.format(result1);

            Date.setText(hdate);
            URL.setText(information[2]);
            des.setText(information[3]);

            tohidepublisher.setVisibility(View.GONE);
            publisher.setVisibility(View.GONE);
            tohideapp.setVisibility(View.GONE);
            appnumber.setVisibility(View.GONE);
            tohideauthor.setText("Creators");


        }









        downloadfbtn = findViewById(R.id.floatingActionButton1);
        deletebtn = findViewById(R.id.deletepaperid);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(detailclass.this);
                alertDialogBuilder.setTitle("Please Enter Your Password to delete");
                alertDialogBuilder.setCancelable(false);

                initPopupViewControls();

                alertDialogBuilder.setView(popupInputDialogView);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext() , "Your Data will be removed prmanently" , Toast.LENGTH_SHORT).show();
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

    }

    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(detailclass.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.deletepopup, null);

        passwordEditText =  popupInputDialogView.findViewById(R.id.pass1);
        confirmpassedittext =  popupInputDialogView.findViewById(R.id.pass2);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);


    }
}
