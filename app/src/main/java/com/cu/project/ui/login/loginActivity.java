package com.cu.project.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.cu.project.APIHelper.ApiVerify;
import com.cu.project.APIHelper.Apiget;
import com.cu.project.APIHelper.AsyncLoginResponse;
import com.cu.project.APIHelper.AsyncResponse;
import com.cu.project.R;
import com.cu.project.APIHelper.ApiLogin;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.Register.RegisterActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static android.view.View.inflate;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class loginActivity extends Activity implements loginMvpView , AsyncLoginResponse , AsyncResponse {

    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        // sign in button
        Button signinloginbtn = findViewById(R.id.signinloginbutton);

        signinloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onLoginButtonClick();

            }
        });

        // not a member text
        TextView notamember = findViewById(R.id.Notamembertext);
        notamember.setOnClickListener(new View.OnClickListener() {
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

        EditText username = findViewById(R.id.fnamelogintext);
        EditText password = findViewById(R.id.lnamelogintext);

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
            String hashedpass = generatedhash12(str);

            ApiLogin apiLogin = new ApiLogin(this,usernametext , hashedpass);

            try {
                apiLogin.asyncLoginResponse =  this;
                apiLogin.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void processLoginFinish(Object output) {
        String str = output.toString();
        editor.putString("Token", str);
        editor.putLong("Exp_time",System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(50));
        editor.commit();

        if(str.length() <= 4)
        {
            Toast.makeText(getApplicationContext() , "Invalid Credentials", Toast.LENGTH_LONG).show();
        }
        else
        {

            Apiget apiget = new Apiget(this);
            apiget.asyncResponse = this;
            apiget.execute(str);

        }
    }


    // generating the hash function
    public static String generatedhash12(String passwordToHash){
        String generatedPassword = null;
          try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
             md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
              for (byte aByte : bytes) {
                  sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
              }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    // returning the token for other functions
    public static String gettoken()
    {   if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token1 = sharedpreferences.getString("Token", "");
        if(token1.equals("")){
            Log.e(TAG, "gettoken: token doesnot exists or expired " );
        }
        return token1;
    }


    @Override
    public void processFinish(Object output) {
        String[] strings = (String[]) output;
        long date1 = Long.parseLong(strings[5]);

        DateFormat simple = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Date result1 = new Date(date1);

        String dojdate = simple.format(result1);
        long date2 = Long.parseLong(strings[8]);
        Date result2 = new Date(date2);

        String dobdate = simple.format(result2);

        Intent intent_name = new Intent();
        intent_name.setClass(this, ProfileActivity.class);
        intent_name.putExtra("e_code" , strings[0]);
        intent_name.putExtra("name_" , strings[1]);
        intent_name.putExtra("email_" , strings[2]);
        intent_name.putExtra("p_no" , strings[3]);
        intent_name.putExtra("depart_" , strings[4]);
        intent_name.putExtra("doj_" , dojdate);
        intent_name.putExtra("quali_" , strings[6]);
        intent_name.putExtra("uni_" , strings[7]);
        intent_name.putExtra("dob_" , dobdate);
        startActivity(intent_name);
    }

}
