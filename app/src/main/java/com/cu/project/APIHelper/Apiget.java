package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.*;
import java.io.IOException;

public class Apiget  extends AsyncTask<String , Void , Void> {

    String urls = "https://apitims1.azurewebsites.net/user/data?token=";


    @Override
    protected Void doInBackground(String... voids) {

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
            String jsonData = null;


            jsonData = responses.body().string();

            Log.v("Jsondata" , jsonData);


        }catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
