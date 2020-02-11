package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.Profiile.View_fragment;
import com.cu.project.ui.detailclass;
import com.cu.project.ui.login.loginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiDelete extends AsyncTask<Void , Void , String> {
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;
    String id = null, type = null, pass = null;
     ProgressDialog dialog;

    private WeakReference<Context> contextRef;

//    String token = loginActivity.gettoken();

    String url = "https://apitims1.azurewebsites.net//user/upload?token=";

    public ApiDelete(Context context, String id, String type, String pass) {
        contextRef =new WeakReference<> (context);
        this.id = id;
        this.type = type;
        this.pass = pass;
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
    protected String doInBackground(Void... voids) {
        Context scontext = contextRef.get();

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token= sharedpreferences.getString("Token", "");



        url = url + token;
        String code = null;


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();

        try {

            String haspass = loginActivity.generatedhash12(pass);
            String doublehash = loginActivity.generatedhash12(haspass);
            jsonObject.put("password", doublehash);
            jsonObject.put("id", id);
            jsonObject.put("Type", type);


            RequestBody body = RequestBody.create(MEDIA_TYPE ,jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url).delete(body).build();

            Response response = client.newCall(request).execute();

            code = String.valueOf(response.code());


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return code;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        Context scontext = contextRef.get();
        Log.e("STRING" , string);

        if(dialog.isShowing())
        {
            dialog.cancel();
        }

        if(string.equals("401")){
            Toast.makeText(scontext , "Please check your password" , Toast.LENGTH_SHORT).show();
        }

        else{
            Activity activity = (Activity) scontext;
            activity.finish();
        }




    }
}
