package com.cu.project.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cu.project.APIHelper.Apiget;
import com.cu.project.AsyncResponse;
import com.cu.project.R;
import com.cu.project.Util.ApiLogin;
import com.cu.project.ui.Register.RegisterActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class loginActivity extends Activity implements loginMvpView , AsyncResponse{

    Button signinloginbtn;
    TextView Notamember;
    EditText username , password;
    ProgressDialog dialog;

    static String token = null;

    public ProgressBar bar;
    private static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
          editor = sharedpreferences.edit();


        signinloginbtn = findViewById(R.id.signinloginbutton);

        signinloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onLoginButtonClick();

            }
        });


        bar = findViewById(R.id.pbar);



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


        signinloginbtn.setClickable(false);





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

        if(flag == 0 )
        {
            String str = generatedhash12(passtext);
            Log.e("Hash1",str);
            String hashedpass = generatedhash12(str);
            Log.e("Hash2",hashedpass);

            Log.e("Login Credences", usernametext + hashedpass);



            // logic for login

            ApiLogin apiLogin = new ApiLogin(this,usernametext , hashedpass);

            try {
                apiLogin.asyncResponse=  this;
                  apiLogin.execute();


            } catch (Exception e) {
                e.printStackTrace();
            }



        }



    }

    @Override
    public void processFinish(Object output) {
        String str = output.toString();
        editor.putString("Token", str);
editor.commit();
        Log.e("processFinish", "processFinish: "+str);

        if(str.length() <= 4)
        {
            Toast.makeText(getApplicationContext() , "Information Invalid", Toast.LENGTH_LONG).show();

        }
        else
        {

            Apiget apiget = new Apiget(this);

            apiget.execute(str);


        }
    }
    public static String generatedhash12(String passwordToHash){
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


    public static String gettoken()
    {
        String token1 = sharedpreferences.getString("Token", "");
        Log.e(TAG, "gettoken: "+token1 );
        return token1;
    }


}
