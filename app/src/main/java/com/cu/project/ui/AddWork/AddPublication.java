package com.cu.project.ui.AddWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

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

public class AddPublication extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    EditText pubtitle , publisher , pubdate , puburl , pubdes;
    Button savebtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpublication);


        imageView = findViewById(R.id.addauthbtn_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPublication.this , Authorclass.class);
                startActivityForResult(intent , 1);
            }
        });

        listView = findViewById(R.id.authorslist_id);


        pubtitle = findViewById(R.id.title_id);
        publisher = findViewById(R.id.Publication_id);
        pubdate = findViewById(R.id.Publicationdate_id);
        puburl = findViewById(R.id.Publicationurl_id);
        pubdes = findViewById(R.id.Description_id);


        pubdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
//                c.get(Calendar.MILLISECOND);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPublication.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        pubdate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        savebtn = findViewById(R.id.pubsave_id);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = pubtitle.getText().toString();
                String publishertext = publisher.getText().toString();
                String date = pubdate.getText().toString();
                String url = puburl.getText().toString();
                String des = pubdes.getText().toString();

                if(title.equals(""))
                {
                    pubtitle.setError("Field cannot be empty");
                }

                if(des.equals(""))
                {
                    pubdes.setError("Field cannot be empty");
                }

                if(url.equals(""))
                {
                    puburl.setError("Field cannot be empty");
                }

                if( date.equals("") )
                {
                    pubdate.setError("Field cannot be empty");
                }

                if(publishertext.equals("") )
                {
                    publisher.setError("Field cannot be empty");
                }

                else
                {
                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date hdate = null;

                    try {
                        hdate = date1.parse(pubdate.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = hdate.getTime();

                    String dateofpub = String.valueOf(time1);

                    String[] info = {title , publishertext , url , dateofpub , des};
                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    jsonEncoder.jsonify_pub(info);
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

                Log.e("INFO" , authname + " " + authemail + " " + authpno);


                String add = authname;


                final String[] authnames = new String[]{authname};
                final List<String> auth_list = new ArrayList<>(Arrays.asList(authnames));

                // Create an ArrayAdapter from List
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, auth_list);

                // DataBind ListView with items from ArrayAdapter
                listView.setAdapter(arrayAdapter);

            }
        }
    }
}
