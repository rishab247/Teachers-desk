package com.cu.project.ui.AddWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.ApiPOST;
import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Register.RegisterActivity;
import com.cu.project.ui.Upload.UploadActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHonor extends AppCompatActivity{

    EditText title , date , issuer , des;
    Button savebtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhonor);

        title = findViewById(R.id.htitle_id);
        date = findViewById(R.id.honordate_id);
        issuer = findViewById(R.id.issuer_id);
        des = findViewById(R.id.Des_id);


        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddHonor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        savebtn = findViewById(R.id.hsavebtn_id);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setError(null);
                date.setError(null);
                issuer.setError(null);
                des.setError(null);


                String titletext = title.getText().toString().trim();
                String datetext = date.getText().toString().trim();
                String issuertext = issuer.getText().toString().trim();
                String destext = des.getText().toString().trim();


                int flag = 0;

                if(titletext.equals(""))
                {
                    title.setError("Field cannot be empty");
                    flag = 1;
                }
                if(datetext.equals(""))
                {
                    date.setError("Choose a date");
                    flag = 1;

                }

                if(issuertext.equals(""))
                {
                    issuer.setError("Field cannot be empty");
                    flag = 1;

                }
                if(destext.equals(""))
                {
                    des.setError("Field cannot be empty");
                    flag = 1;

                }

                if(flag == 0)
                {

                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date hdate = null;

                    try {
                        hdate = date1.parse(date.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = hdate.getTime();

                    String dateofhonor = String.valueOf(time1);

                    String[] valstr ={titletext , "honor" , issuertext , dateofhonor , destext};
                    JsonEncoder jsonEncoder = new JsonEncoder(AddHonor.this);
                    String info = jsonEncoder.jsonify_honor(valstr);

                    ApiPOST apiPOST = new ApiPOST(AddHonor.this);
                    apiPOST.execute(info);

                }

            }
        });
    }

}