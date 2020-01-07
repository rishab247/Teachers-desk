package com.cu.project.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cu.project.APIHelper.Apiget;
import com.cu.project.R;
import com.cu.project.Util.ApiLogin;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.Register.RegisterActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


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


                onLoginButtonClick();

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

        int flag = 0;

        username = findViewById(R.id.fnamelogintext);
        password = findViewById(R.id.lnamelogintext);



        String usernametext = username.getText().toString().trim();
        String passtext = password.getText().toString().trim();

        if(usernametext.equals(""))
        {
            username.setError("Enter Euid");
            flag = 1;
        }

        if(passtext.equals(""))
        {
            password.setError("Enter Password");
            flag = 1;
        }

        if(flag == 0)
        {
            String str = generatedhash12(passtext);
            Log.e("Hash1",str);
            String hashedpass = generatedhash12(str);
            Log.e("Hash2",hashedpass);

            Log.e("Login Credences", usernametext + hashedpass);

            ApiLogin apiLogin = new ApiLogin(usernametext , hashedpass);
            String token = null;
            try {
                token = apiLogin.execute().get();
                Log.e("TOKEN obtained" , token);


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(token.length() <= 4)
            {
                Toast.makeText(getApplicationContext() , "Information Invalid", Toast.LENGTH_LONG).show();

            }
            else
            {

                Apiget apiget = new Apiget();
                apiget.execute(token);

                Intent intent = new Intent(loginActivity.this , ProfileActivity.class);
                startActivity(intent);
            }

        }



    }


    String generatedhash12(String passwordToHash){
        String generatedPassword = null;
          try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
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
