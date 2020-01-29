package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiDelete extends AsyncTask<Void , Void , String> {

    String id = null, type = null, pass = null;
    Context scontext;
    ProgressDialog dialog;


    String token = loginActivity.gettoken();

    String url = "https://apitims1.azurewebsites.net//user/upload?token=";

    public ApiDelete(Context context, String id, String type, String pass) {
        this.scontext = context;
        this.id = id;
        this.type = type;
        this.pass = pass;
    }

    @Override
    protected String doInBackground(Void... voids) {

        url = url + token;
        String message = null;


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

            message = response.body().string();

            if(message.equals(""))
            {

            }
            else
            {
                JSONObject jsonObject1 = new JSONObject(message);

                message = jsonObject1.getString("msg");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return message;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
    }
}
