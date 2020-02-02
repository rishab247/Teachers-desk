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
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cu.project.ui.login.loginActivity;

public class Apiget  extends AsyncTask<String , Void , String[]> {

    private static Context sContext;
    ProgressDialog dialog;
    public AsyncResponse asyncResponse= null;


    public static String[] statusarr = {""};



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


    String verifyurl = "https://apitims1.azurewebsites.net/Verify?token=";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String[] doInBackground(String... voids) {


        String[] infoarr={""};



        OkHttpClient client = new OkHttpClient();

        String token = voids[0];

        urls = urls + token;

        verifyurl = verifyurl + token;


        Request request = new Request.Builder()
                .url(urls)
                .build();
        Response responses = null;


        try {

            responses = client.newCall(request).execute();

            jsonData = responses.body().string();


            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray arrObj = jsonObject.getJSONArray("Status");


             ecode = arrObj.get(0).toString().trim();
             name = arrObj.get(1).toString().trim();
             email = arrObj.get(2).toString().trim();
             pno = arrObj.get(3).toString().trim();
             depart = arrObj.get(4).toString().trim();
             doj = arrObj.get(5).toString().trim();
             quali = arrObj.get(6).toString().trim();
             uni = arrObj.get(7).toString().trim();
             dob = arrObj.get(8).toString().trim();


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

        if(dialog.isShowing())
            dialog.hide();
        asyncResponse.processFinish(strings);




    }



}
