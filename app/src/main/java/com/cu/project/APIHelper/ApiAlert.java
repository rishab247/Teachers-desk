package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;
import android.view.textclassifier.TextLanguage;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiAlert extends AsyncTask<Void, Void , Void> {

    String url = "https://apitims1.azurewebsites.net/Alert";
    @Override
    protected Void doInBackground(Void... voids) {


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            Log.e("splash alert" , response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
