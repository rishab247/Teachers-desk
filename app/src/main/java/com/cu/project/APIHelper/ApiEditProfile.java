package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.textclassifier.TextLanguage;
import android.widget.Toast;

import com.cu.project.ui.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiEditProfile extends AsyncTask<String, Void , String> {

    Context scontext;
    String p_no , depart , quali , uni , pass;

    public static int code = 1;

    String url = "https://apitims1.azurewebsites.net/user/Profile?token=";
    String token = loginActivity.gettoken();

    ProgressDialog dialog;


    public ApiEditProfile(Context context)
    {
        this.scontext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids) {

        url = url + token;

        p_no = voids[0];
        quali = voids[1];
        uni = voids[2];
        depart = voids[3];
        pass = voids[4];

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("password" ,  pass);
            jsonObject.put("phoneno", p_no);
            jsonObject.put("Qualification", quali);
            jsonObject.put("University" , uni);
            jsonObject.put("Department_Name" , depart);
        } catch (JSONException e) {
            e.printStackTrace();
        }


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

        if(dialog.isShowing())
        {
         dialog.cancel();
        }

        if(aVoid.equals("200"))
        {
            Intent resultIntent = new Intent();

            String[] info = {p_no , depart , quali , uni };
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
