package com.cu.project.ui.AddWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.R;
import com.cu.project.ui.Authorclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPublication extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
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
                final List<String> fruits_list = new ArrayList<>(Arrays.asList(authnames));

                // Create an ArrayAdapter from List
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fruits_list);

                // DataBind ListView with items from ArrayAdapter
                listView.setAdapter(arrayAdapter);

            }
        }
    }

}
