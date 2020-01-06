package com.cu.project.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cu.project.R;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.Register.RegisterActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class loginActivity extends Activity implements loginMvpView {

    Button signinloginbtn;
    TextView Notamember;
    EditText username , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signinloginbtn = findViewById(R.id.signinloginbutton);

        signinloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = findViewById(R.id.fnamelogintext);
                password = findViewById(R.id.lnamelogintext);



                String usernametext = username.getText().toString().trim();
                String passtext = password.getText().toString().trim();


                String hashedpass = generatedhash12(generatedhash12(passtext));


                Log.e("Login Credences", usernametext + hashedpass);






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


    String generatedhash12(String passwordToHash){
        String generatedPassword = null;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


}
