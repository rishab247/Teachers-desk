package com.cu.project.APIHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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

public class ApiPassword extends AsyncTask<String , Void , String> {
    String url = "https://apitims.azurewebsites.net/Verify/password?token=";
//    String token = loginActivity.gettoken();

    String oldpass = null;
    String newpass = null;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
     ProgressDialog dialog;

    private WeakReference<Context> contextRef;

    public ApiPassword(Context context)
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

        String code = null;

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token= sharedpreferences.getString("Token", "");
        url = url + token;
        oldpass = voids[0];
        newpass = voids[1];

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("password" , oldpass);
            jsonObject.put("new_password" , newpass);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(MEDIA_TYPE , jsonObject.toString());

        Request request = new Request.Builder()
                .url(url).put(body).build();


        try {
            Response response = client.newCall(request).execute();

            code = String.valueOf(response.code());


        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("CODE" ,code);
        return code;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        Context sContext = contextRef.get();

        if(dialog.isShowing())
        {
            dialog.cancel();
        }


        if(aVoid.equals("200"))
        {
            Toast.makeText(sContext , "Password Changed" , Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(sContext , "Password Invalid" , Toast.LENGTH_SHORT).show();
        }

    }
}
