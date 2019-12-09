package com.cu.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class Welcome extends AppCompatActivity {
Button signinwelcomebtn ,registerwelcomebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        signinwelcomebtn = findViewById(R.id.signinwelcomebutton);
        registerwelcomebtn = findViewById(R.id.registerwelcomebutton);

        signinwelcomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Welcome.this, Login.class);
                startActivity(myIntent);
            }
        });
        registerwelcomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Welcome.this, Register.class);
                startActivity(myIntent);
            }
        });
     }
}
