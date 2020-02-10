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

import com.cu.project.APIHelper.ApiDelete;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.Apigetdetails;
import com.cu.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class detailclass  extends AppCompatActivity {


    FloatingActionButton downloadfbtn ,deletebtn;
    TextView Title , Date , URL , publisher , appnumber , authname;
    TextView tohideurl , tohidepublisher , tohideapp , tohideauthor, typetext;
    TextView des;

    private View popupInputDialogView = null;
    EditText passwordEditText;
    private TextView cancelUserDataButton;
    private TextView saveUserDataButton;
    String id = null , type = null;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<String> pnos = new ArrayList<>();
    private ArrayList<String> details = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accomplishments_details);

        Bundle bundle = getIntent().getExtras();

        HashMap<String , ArrayList> info = (HashMap<String, ArrayList>) bundle.get("info");


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

        for(int i =0; i < names.size() ; i ++)
        {
            authdetails += names.get(i) + "\n";
        }



        int last = details.size();

        type = details.get(last - 2);
        id = details.get(last - 1);

        typetext.setText(type);

        Log.e("TYPE ", type);


        if(type.equals("Honors_and_Award")){

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


        }
        else if(type.equals("Publication")){

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

        }
        else if(type.equals("Patent"))
        {

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




        }
        else{
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
                        Toast.makeText(getApplicationContext() , "Your Data will be removed permanently" , Toast.LENGTH_SHORT).show();

                        if(passwordEditText.getText().toString().trim().equals(""))
                        {
                            passwordEditText.setError("Field cannot be empty");
                        }
                        else
                        {
                            String pass =  passwordEditText.getText().toString().trim();
                            alertDialog.cancel();
                            if(type.equals("Honors_and_Award"))
                                type = "HonorsandAward";

                            ApiDelete apiDelete = new ApiDelete(detailclass.this , id, type , pass);
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

    }

    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(detailclass.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.deletepopup, null);

        passwordEditText =  popupInputDialogView.findViewById(R.id.pass1);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);


    }
}