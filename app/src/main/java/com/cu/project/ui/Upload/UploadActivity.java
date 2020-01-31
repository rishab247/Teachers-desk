package com.cu.project.ui.Upload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.R;
import com.cu.project.ui.AddWork.AddHonor;
import com.cu.project.ui.AddWork.AddPatent;
import com.cu.project.ui.AddWork.AddProject;
import com.cu.project.ui.AddWork.AddPublication;
import com.cu.project.ui.login.loginActivity;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView text1 , text2 , text3, text4 , totaltext;

    int c1 = 0;
    int c2 = 0;
    int c3 = 0;
    int c4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        toolbar = findViewById(R.id.toolbar_upload);
        setSupportActionBar(toolbar);



        text1 = findViewById(R.id.textView4);
        text2 = findViewById(R.id.textView5);
        text3 = findViewById(R.id.textView6);
        text4 = findViewById(R.id.textView7);
        totaltext = findViewById(R.id.total);



        c1 = ApigetPaper.countpub;
        c2 = ApigetPaper.countpatent;
        c3 = ApigetPaper.countproject;
        c4 = ApigetPaper.counthonor;

        text1.setText("Publications \n" + c1);
        text2.setText("Patents \n" + c2);
        text3.setText("Projects \n" + c3);
        text4.setText("Honors \n And \n Awards \n" + c4);

        int totalcount =  c1 + c2 + c3 + c4;

        totaltext.setText("TOTAL COUNT = " + totalcount);
        this.arrayAdapterListView();



    }
    private void arrayAdapterListView() {
        List<String> dataList = new ArrayList<>();
        dataList.add("Add a Publication");
        dataList.add("Add a Patent");
        dataList.add("Add a Project");
        dataList.add("Add a Honor or Award");

        ListView listView = findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                switch (index){
                    case 0:
                        Intent intent = new Intent(UploadActivity.this , AddPublication.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent intent1 = new Intent(UploadActivity.this , AddPatent.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case 2:
                        Intent intent2 = new Intent(UploadActivity.this , AddProject.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent(UploadActivity.this , AddHonor.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
