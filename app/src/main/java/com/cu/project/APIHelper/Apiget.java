package com.cu.project.APIHelper;
import com.cu.project.R;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.Profiile.profile_fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cu.project.ui.login.loginActivity;

public class Apiget  extends AsyncTask<String , Void , String[]> {

    private static Context sContext;
    ProgressDialog dialog;


    public Apiget(Context context)
    {
        sContext = context;
    }

    String urls = "https://apitims1.azurewebsites.net/user/Profile?token=";
    String jsonData = null;

    String ecode="";
    String name="";
    String email="" ;
    String pno="";
    String depart="" ;
    String doj ="";
    String quali="";
    String uni="";
    String dob="";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected String[] doInBackground(String... voids) {


        String[] infoarr={""};

        OkHttpClient client = new OkHttpClient();

        String token = voids[0];

        urls = urls + token;


        Log.e("URL" , urls);

        Request request = new Request.Builder()
                .url(urls)
                .build();
        Response responses = null;


        try {

            responses = client.newCall(request).execute();


            jsonData = responses.body().string();

            Log.v("Jsondata" , jsonData);


            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray arrObj = jsonObject.getJSONArray("Status");

            Log.e("JSON ARRAY" , String.valueOf(arrObj));


             ecode = arrObj.get(0).toString().trim();
             name = arrObj.get(1).toString().trim();
             email = arrObj.get(2).toString().trim();
             pno = arrObj.get(3).toString().trim();
             depart = arrObj.get(4).toString().trim();
             doj = arrObj.get(5).toString().trim();
             quali = arrObj.get(6).toString().trim();
             uni = arrObj.get(7).toString().trim();
             dob = arrObj.get(8).toString().trim();

            Log.e("VALUES" ,ecode + name + email + pno + depart + doj + quali + uni + dob );


            infoarr = new String[]{ecode , name , email , pno , depart , doj , quali , uni ,dob};


        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return infoarr;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);


        long date1 = Long.parseLong(strings[5]);

        DateFormat simple = new SimpleDateFormat("dd MM yyyy");

        Date result1 = new Date(date1);

        String dojdate = simple.format(result1);



        long date2 = Long.parseLong(strings[8]);

        DateFormat simple1 = new SimpleDateFormat("dd/MM/yyyy");

        Date result2 = new Date(date2);

        String dobdate = simple.format(result2);

        Intent intent_name = new Intent();
        intent_name.setClass(sContext,ProfileActivity.class);
        intent_name.putExtra("e_code" , strings[0]);
        intent_name.putExtra("name_" , strings[1]);
        intent_name.putExtra("email_" , strings[2]);
        intent_name.putExtra("p_no" , strings[3]);
        intent_name.putExtra("depart_" , strings[4]);
        intent_name.putExtra("doj_" , dojdate);
        intent_name.putExtra("quali_" , strings[6]);
        intent_name.putExtra("uni_" , strings[7]);
        intent_name.putExtra("dob_" , dobdate);
        sContext.startActivity(intent_name);








    }

}
