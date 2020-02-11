package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cu.project.ui.Upload.UploadActivity;
import com.cu.project.ui.login.loginActivity;


import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiPOST  extends AsyncTask<String , Context, String> {


    private ProgressDialog dialog;

    String token = loginActivity.gettoken();

    Context scontext;

    private WeakReference<Context> contextRef;


    public ApiPOST(Context context)
    {
        contextRef =new WeakReference<> (context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        scontext = contextRef.get();

        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids) {

        String str = voids[0];

        String code = null;
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        String url = "https://apitims1.azurewebsites.net/user/upload/Honors_and_Award?token=";
        url = url + token;

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                    .url(url).post(body).build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            code = String.valueOf(response.code());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return code;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        if(dialog.isShowing())
        {
            dialog.cancel();
        }

        Activity activity = (Activity)scontext;

        if(aVoid.equals("200"))
        {
            Intent intent = new Intent(scontext , UploadActivity.class);
            scontext.startActivity(intent);
            activity.finish();
        }
        else
        {
            Toast.makeText(scontext , "Honor or Award already exists" , Toast.LENGTH_SHORT).show();
        }

    }

}

