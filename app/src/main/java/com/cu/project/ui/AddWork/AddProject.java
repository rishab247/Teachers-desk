package com.cu.project.ui.AddWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Authorclass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddProject extends AppCompatActivity {
    ImageView imageView;
    ListView listView;

    EditText title , date, url , des;

    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        imageView = findViewById(R.id.addauthbtn_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProject.this , Authorclass.class);
                startActivityForResult(intent , 1);
            }
        });

        listView = findViewById(R.id.authorslist_id);


        title = findViewById(R.id.protitle_id);
        date = findViewById(R.id.Projectdate_id);
        url = findViewById(R.id.Projecturl_id);
        des = findViewById(R.id.PRODescription_id);



        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
//                c.get(Calendar.MILLISECOND);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProject.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        btn = findViewById(R.id.prosavebtn_id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titletext = title.getText().toString().trim();
                String datetext = date.getText().toString().trim();
                String urltext = url.getText().toString().trim();
                String destext = des.getText().toString().trim();

                if(titletext.equals(""))
                {
                    title.setError("Field cannot be empty");
                }


                if(datetext.equals(""))
                {
                    date.setError("Field cannot be empty");
                }


                if(urltext.equals(""))
                {
                    url.setError("Field cannot be empty");
                }


                if(destext.equals(""))
                {
                    des.setError("Field cannot be empty");
                }

                else
                {


                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date hdate = null;

                    try {
                        hdate = date1.parse(date.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = hdate.getTime();

                    String dateofpro = String.valueOf(time1);

                    String[] info = {titletext , urltext , dateofpro , destext};
                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    jsonEncoder.jsonify_project(info);
                }
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                String authname = data.getStringExtra("authname");
                String authemail = data.getStringExtra("authemail");
                String authpno = data.getStringExtra("authpno");

                String add = authname;

                final String[] authnames = new String[]{authname};
                final List<String> fruits_list = new ArrayList<>(Arrays.asList(authnames));

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fruits_list);

                listView.setAdapter(arrayAdapter);

            }
        }
    }


}
