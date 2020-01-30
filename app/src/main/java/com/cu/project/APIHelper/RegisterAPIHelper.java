package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.Util.util;
import com.cu.project.ui.Register.RegisterMvpView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kotlin.Result;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class RegisterAPIHelper  extends AsyncTask<String , String , String> {

    Context scontext;
    ProgressDialog dialog;

    String length = null;

    public RegisterAPIHelper(Context context) {
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
    protected String doInBackground(String... voids) {

        String str = voids[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String url = util.url + "/register";

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(url).post(body).build();


        try {
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dialog.cancel();
    }
}
