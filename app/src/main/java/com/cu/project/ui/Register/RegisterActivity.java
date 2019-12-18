package com.cu.project.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cu.project.R;
import com.cu.project.ui.login.loginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterMvpView{
    Button btn_register;
    TextView Alreadyamember ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.Registerregisterbutton);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                startActivity(intent);
            }
        });
        Alreadyamember = findViewById(R.id.Alreadyamembertext);
        Alreadyamember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                startActivity(intent);

            }
        });

    }
}
