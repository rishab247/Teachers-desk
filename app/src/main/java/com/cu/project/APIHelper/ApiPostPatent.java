package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiPostPatent extends AsyncTask<String , Void , Void> {
    String token = loginActivity.gettoken();
    @Override
    protected Void doInBackground(String... strings) {

        String str = strings[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String urlpost = "https://apitims1.azurewebsites.net/user/upload/Patent?token=";
        urlpost = urlpost + token;
        Log.e("TOKEN PUB" , urlpost);


        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(urlpost).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                String mMessage = e.getMessage();
                Log.v("patent failure Response", mMessage);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e("patent_responce", mMessage);
            }
        });

        return null;
    }
}
