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
import android.widget.Spinner;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPatent extends AppCompatActivity {
    Spinner spinner;
    ImageView imageView;
    ListView listView;

    EditText title , number , url , des , date;
    Button savebtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatent);

        imageView = findViewById(R.id.addauthbtnpatent_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPatent.this , Authorclass.class);
                startActivityForResult(intent , 1);
            }
        });

        listView = findViewById(R.id.authorslistpatent_id);
        spinner = findViewById(R.id.pspinner);

        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);



        title = findViewById(R.id.patenttitle_id);
        date = findViewById(R.id.Patentdate_id);
        number = findViewById(R.id.Patentapp_id);
        url = findViewById(R.id.Patenturl_id);
        des = findViewById(R.id.pDescription_id);


        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPatent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        savebtn = findViewById(R.id.psavebtn_id);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setError(null);
                date.setError(null);
                number.setError(null);
                url.setError(null);
                des.setError(null);

                String titletext = title.getText().toString().trim();
                String datetext = date.getText().toString().trim();
                String appnumber = number.getText().toString().trim();
                String urltext = url.getText().toString().trim();
                String destext = des.getText().toString().trim();
                String office = spinner.getSelectedItem().toString().trim();


                if(titletext.equals(""))
                {
                    title.setError("Field cannot be empty");
                }
                if(datetext.equals(""))
                {
                    date.setError("Field cannot be empty");
                }
                if(appnumber.equals(""))
                {
                    number.setError("Field cannot be empty");
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

                    String dateofpatent = String.valueOf(time1);

                    String[] info = {titletext ,office , appnumber , urltext , dateofpatent , destext};
                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    jsonEncoder.jsonify_patent(info);

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
