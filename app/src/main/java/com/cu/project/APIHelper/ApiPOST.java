package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.AddWork.AddHonor;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ApiPOST  extends AsyncTask<String , Context, Void> {
    private ProgressDialog dialog;
    String token = loginActivity.gettoken();
    Context scontext;

    public ApiPOST(Context context)
    {
        scontext = context;
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//        dialog = new ProgressDialog(scontext);
//        dialog.setMessage("Please wait...");
//        dialog.setIndeterminate(true);
//        dialog.show();
//    }


    @Override
    protected Void doInBackground(String... voids) {

        String str = voids[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        Log.v("INFO" , str);

        String url = "https://apitims1.azurewebsites.net/user/upload/Honors_and_Award?token=";
        url = url + token;
        Log.e("honor_url" , url);





        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                    .url(url).post(body).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    String mMessage = e.getMessage();
                    Log.v("honor failure Response", mMessage);

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    String mMessage = response.body().string();
                    Log.e("honor_responce", mMessage);
                }
            });


        return null;
    }

//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//        dialog.hide();
//    }
}

