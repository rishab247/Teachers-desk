package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.Util.util;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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
    String mMessage;
    int flag = 0;


    @Override
    protected Void doInBackground(String... voids) {

        String str = voids[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String url = util.url + "/register";

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                mMessage = e.getMessage();
                Log.v("failure Response", mMessage);


            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                mMessage = response.body().string();
                Log.e("responce", mMessage);


            }
        });


        return null;
    }
}
