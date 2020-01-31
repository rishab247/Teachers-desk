package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.ui.Upload.UploadActivity;
import com.cu.project.ui.login.loginActivity;

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

public class ApiPostPub extends AsyncTask<String , Void , Void>  {
    String token = loginActivity.gettoken();

    Context scontext;

    ProgressDialog dialog;


    public ApiPostPub(Context context) {
        scontext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {

        String str = strings[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String urlpost = "https://apitims1.azurewebsites.net/user/upload/Publication?token=";
        urlpost = urlpost + token;

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(urlpost).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                String mMessage = e.getMessage();
                Log.v("pub failure Response", mMessage);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String mMessage = response.body().string();


                try {
                    JSONObject jsonObject1 = new JSONObject(mMessage);

                    String info = jsonObject1.getString("msg");
                    Log.e("pub_responce", info);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.cancel();
    }


}
