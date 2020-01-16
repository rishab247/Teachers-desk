package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApigetPaper extends AsyncTask<String , Void , Void> {

    String urls = "https://apitims1.azurewebsites.net/user/Accomplishment?token=";



    @Override
    protected Void doInBackground(String... voids) {

        String jsonData;


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




        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
