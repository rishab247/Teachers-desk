package com.cu.project.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


    FloatingActionButton downloadfbtn;
    TextView Title , Date , URL , publisher , appnumber , authname;
    TextView tohideurl , tohidepublisher , tohideapp , tohideauthor;
    EditText des;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accomplishments_details);

        Bundle bundle = getIntent().getExtras();



        String[] information = bundle.getStringArray("info");

        for(int i= 0;i < information.length;i++)
            System.out.println(information[i]);


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


        int last = information.length;

        String type = information[last - 1];

        Log.e("TYPE ", type);


      if(type.equals("Honors_and_Award")){

          Title.setText(information[0]);
          tohidepublisher.setText("Issuer");
          publisher.setText(information[1]);

          tohideauthor.setVisibility(View.GONE);
          tohideapp.setVisibility(View.GONE);
          tohideurl.setVisibility(View.GONE);


          long date1 = Long.parseLong(information[2]);

          DateFormat simple = new SimpleDateFormat("dd MM yyyy");

          java.util.Date result1 = new Date(date1);

          String hdate = simple.format(result1);

          Date.setText(hdate);

          des.setText(information[3]);


      }
      else if(type.equals("Publication")){

      }
      else if(type.equals("Patent")){

      }
      else{

      }









        downloadfbtn = findViewById(R.id.floatingActionButton1);

    }
}
