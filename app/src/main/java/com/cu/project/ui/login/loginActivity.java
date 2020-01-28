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
import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class loginActivity extends Activity implements loginMvpView , AsyncLoginResponse, AsyncResponse {

    Button signinloginbtn;
    TextView Notamember;
    EditText username , password;


    public ProgressBar bar;
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

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
        Log.e("processLoginFinish", "processLoginFinish: "+str);

        if(str.length() <= 4)
        {
            Toast.makeText(getApplicationContext() , "Information Invalid", Toast.LENGTH_LONG).show();

        }
        else
        {

            Apiget apiget = new Apiget(this);
            apiget.asyncResponse = this;
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
    {   if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token1 = sharedpreferences.getString("Token", "");
        if(token1.equals("")){
            //token doesnot exists or expired
            Log.e(TAG, "gettoken: token doesnot exists or expired " );

        }
        Log.e(TAG, "gettoken: "+token1 );
        return token1;
    }


    @Override
    public void processFinish(Object output) {
        String[] strings = (String[]) output;
        long date1 = Long.parseLong(strings[5]);

        DateFormat simple = new SimpleDateFormat("dd MM yyyy");

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
