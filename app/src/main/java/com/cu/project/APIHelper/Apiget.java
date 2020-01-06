package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.*;
import java.io.IOException;

public class Apiget  extends AsyncTask<Void , Void , Void> {

    String urls = "https://raw.github.com/square/okhttp/master/README.md";


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();

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
