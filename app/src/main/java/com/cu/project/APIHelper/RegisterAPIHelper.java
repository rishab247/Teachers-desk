package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cu.project.Util.util;
import com.cu.project.ui.Register.RegisterMvpPresenter;
import com.cu.project.ui.Register.RegisterMvpView;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import kotlin.Result;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class RegisterAPIHelper  extends AsyncTask<String , String , Integer> {

    Context scontext;
    ProgressDialog dialog;

    String length = null;

    private WeakReference<Context> contextRef;


    public RegisterAPIHelper(Context context) {
        contextRef =new WeakReference<> (context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        scontext = contextRef.get();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... voids) {

        String str = voids[0];

        Log.e("REGISTER" , str);
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String url = util.url + "/register";

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            String str1 = response.body().string();
            Log.e("Register Response" , str1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 200;
    }


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        Context scontext = contextRef.get();

        if(dialog.isShowing()){
            dialog.cancel();
        }

        if(result == 200){
            Intent intent = new Intent(scontext , loginActivity.class);
            scontext.startActivity(intent);
            ((Activity)scontext).finish();
        }
        else{
            Toast.makeText(scontext, "Error Occurred" ,  Toast.LENGTH_SHORT).show();
        }

    }
}
