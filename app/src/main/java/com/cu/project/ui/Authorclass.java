package com.cu.project.ui;
import com.cu.project.setauthdetails;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cu.project.R;
import com.cu.project.ui.AddWork.AddPublication;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class Authorclass  extends AppCompatActivity {
    MaterialSearchView materialSearchView;

    EditText authname , authemail, authpno;
    Button save_btn , btn;

    List<setauthdetails> auth_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.author);


        Toolbar toolbar = findViewById(R.id.toolbar__);
        setSupportActionBar(toolbar);


        materialSearchView = findViewById(R.id.searchview);

        authname = findViewById(R.id.authors_name);
        authemail = findViewById(R.id.authors_email);
        authpno = findViewById(R.id.authors_pno);

        save_btn = findViewById(R.id.savebtnid__);
        btn = findViewById(R.id.button);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name , email , pno;
                name = authname.getText().toString().trim();
                email = authemail.getText().toString().trim();
                pno = authpno.getText().toString().trim();

                if(name.equals(""))
                {
                    authname.setError("Field Cannot be empty");
                }
                if(email.equals(""))
                {
                    authemail.setError("Field Cannot be empty");
                }
                if(pno.equals(""))
                {
                    authpno.setError("Field Cannot be empty");
                }

                else
                {
                    auth_list.add(new setauthdetails(name , email , pno));
                    authname.setText("");
                    authemail.setText("");
                    authpno.setText("");


                }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name , email , pno;
                name = authname.getText().toString().trim();
                email = authemail.getText().toString().trim();
                pno = authpno.getText().toString().trim();

                if(name.equals(""))
                {
                    authname.setError("Field Cannot be empty");
                }
                if(email.equals(""))
                {
                    authemail.setError("Field Cannot be empty");
                }
                if(pno.equals(""))
                {
                    authpno.setError("Field Cannot be empty");
                }

                else
                {
//                    auth_list.add(new setauthdetails(name , email , pno));
                    Intent intent = new Intent(Authorclass.this , AddPublication.class);
                    intent.putExtra("authname" , name);
                    intent.putExtra("authemail" , email);
                    intent.putExtra("authpno" , pno);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_update , menu);
        MenuItem item = menu.findItem(R.id.searchicon);


        materialSearchView.setMenuItem(item);

        return true;



    }
}
