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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cu.project.R;
import com.cu.project.ui.Profiile.ProfileActivity;

import java.util.Calendar;
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

        spinner = findViewById(R.id.Catagory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner1 = findViewById(R.id.type_id);
        spinner1.setAdapter(adapter);


        btndatepicker = findViewById(R.id.btn_date);
        txtDate = findViewById(R.id.in_date);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.upload_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Toast.makeText(getApplicationContext() , "Saved", Toast.LENGTH_LONG).show();
        return true;
    }


}
