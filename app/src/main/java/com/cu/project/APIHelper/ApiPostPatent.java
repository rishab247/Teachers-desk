package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.Upload.UploadActivity;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiPostPatent extends AsyncTask<String , Void , String> {
    String token = loginActivity.gettoken();

    Context scontext;

    private WeakReference<Context> contextRef;


    public ApiPostPatent(Context context) {
        contextRef =new WeakReference<> (context);

    }

    ProgressDialog dialog;

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
    protected String doInBackground(String... strings) {

        String str = strings[0];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");


        String urlpost = "https://apitims1.azurewebsites.net/user/upload/Patent?token=";
        urlpost = urlpost + token;

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(urlpost).post(body).build();


        Response response = null;

        try {
             response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String code = String.valueOf(response.code());
        return code;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(dialog.isShowing())
        {
            dialog.cancel();
        }

        Activity activity = (Activity)scontext;

        if(s.equals("200"))
        {
            Intent intent = new Intent(scontext , UploadActivity.class);
            scontext.startActivity(intent);
            activity.finish();
        }
        else
        {
            Toast.makeText(scontext , "Patent already exists" , Toast.LENGTH_SHORT).show();
        }


    }
}
