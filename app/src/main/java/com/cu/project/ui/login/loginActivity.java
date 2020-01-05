package com.cu.project.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cu.project.R;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.Register.RegisterActivity;


public class loginActivity extends Activity implements loginMvpView {

    Button signinloginbtn;
    TextView Notamember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signinloginbtn = findViewById(R.id.signinloginbutton);

        signinloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this , ProfileActivity.class);
                startActivity(intent);
            }
        });
        Notamember  =findViewById(R.id.Notamembertext);
        Notamember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void onLoginButtonClick() {

    }


}
