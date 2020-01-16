package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.Util.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class RegisterAPIHelper  extends AsyncTask<String , String , Void> {

    @Override
    protected Void doInBackground(String... voids) {

        String str = voids[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        Log.v("INFO" , str);


        String url = util.url + "/register";

        Log.e("URLS" , url);



        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                String mMessage = e.getMessage();
                Log.v("failure Response", mMessage);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e("responce", mMessage);
            }
        });


        return null;
    }
}
