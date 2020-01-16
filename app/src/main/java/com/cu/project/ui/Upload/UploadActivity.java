package com.cu.project.ui.Upload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cu.project.R;
import com.cu.project.ui.AddWork.AddHonor;
import com.cu.project.ui.AddWork.AddPatent;
import com.cu.project.ui.AddWork.AddProject;
import com.cu.project.ui.AddWork.AddPublication;
import com.cu.project.ui.Profiile.ProfileActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class UploadActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button savebtn;
    Button btndatepicker;
    private int mYear, mMonth, mDay;
    Spinner spinner , spinner1;
    EditText txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        toolbar = findViewById(R.id.toolbar_upload);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

//        spinner = findViewById(R.id.Catagory);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        spinner1 = findViewById(R.id.type_id);
//        spinner1.setAdapter(adapter);
//
//
//        btndatepicker = findViewById(R.id.btn_date);
//        txtDate = findViewById(R.id.in_date);

        this.arrayAdapterListView();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.upload_menu , menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        Toast.makeText(getApplicationContext() , "Saved", Toast.LENGTH_LONG).show();
//        return true;
//    }


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
                        break;
                    case 1:
                        Intent intent1 = new Intent(UploadActivity.this , AddPatent.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(UploadActivity.this , AddProject.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(UploadActivity.this , AddHonor.class);
                        startActivity(intent3);
                        break;
                }
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                Toast.makeText(getApplicationContext(), "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
