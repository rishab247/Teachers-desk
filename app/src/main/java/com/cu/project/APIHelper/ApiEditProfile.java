package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.textclassifier.TextLanguage;
import android.widget.Toast;

import com.cu.project.ui.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiEditProfile extends AsyncTask<String, Void , String> {

     private String p_no , depart , quali , uni , pass , imagestr;

    public static int code = 1;
    private WeakReference<Context> contextRef;

    String url = "https://apitims1.azurewebsites.net/user/Profile?token=";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;


    public ApiEditProfile(Context context)
    {
        contextRef =new WeakReference<> (context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();

         dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids) {
        Context scontext = contextRef.get();

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
        {
            editor.clear();
        Toast.makeText(scontext,"You are Loggesd out",Toast.LENGTH_SHORT).show();



        }
        String token= sharedpreferences.getString("Token", "");
        url = url + token;

        p_no = voids[0];
        quali = voids[1];
        uni = voids[2];
        depart = voids[3];
        pass = voids[4];
        imagestr = voids[5];


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("password" ,  pass);
            jsonObject.put("phoneno", p_no);
            jsonObject.put("Qualification", quali);
            jsonObject.put("University" , uni);
            jsonObject.put("Department_Name" , depart);
            jsonObject.put("pic" , imagestr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("JSON PIC" , imagestr);


        RequestBody body = RequestBody.create(MEDIA_TYPE , jsonObject.toString());

        Request request = new Request.Builder().url(url).put(body).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();


        } catch (IOException e) {
            e.printStackTrace();
        }


        code = response.code();

        Log.e("REUQWE" , String.valueOf(response.code()));
        return String.valueOf(response.code());
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        Context scontext = contextRef.get();

        if(dialog.isShowing())
        {
         dialog.cancel();
        }

        if(aVoid.equals("200"))
        {
            Intent resultIntent = new Intent();

            String[] info = {p_no , depart , quali , uni , imagestr};
            resultIntent.putExtra("result" , info);

            Activity activity = (Activity)scontext;
            activity.setResult(Activity.RESULT_OK , resultIntent);
            activity.finish();
        }
        else{
            Toast.makeText(scontext , "Password Invalid", Toast.LENGTH_SHORT).show();
        }

    }
}
