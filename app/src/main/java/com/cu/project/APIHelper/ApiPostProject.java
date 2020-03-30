package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiPostProject  extends AsyncTask<String , Void , String> {
//    String token = loginActivity.gettoken();
private   SharedPreferences sharedpreferences;
      SharedPreferences.Editor editor;
//    Context scontext;
    ProgressDialog dialog;

    private WeakReference<Context> contextRef;


    public ApiPostProject(Context context) {
        contextRef =new WeakReference<> (context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
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
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token = sharedpreferences.getString("Token", "");
        if(token.equals("")){
            Log.e(TAG, "gettoken: token doesnot exists or expired " );
        }

        String urlpost = "https://apitims.azurewebsites.net/user/upload/Project?token=";
        urlpost = urlpost + token;

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(urlpost).post(body).build();

      String code = null;

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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        if(dialog.isShowing())
        {
            dialog.cancel();
        }
        Context scontext = contextRef.get();

        Activity activity = (Activity)scontext;

        if(s.equals("200"))
        {
            Intent intent = new Intent(scontext , UploadActivity.class);
            scontext.startActivity(intent);
            activity.finish();
        }
        else
        {
            Toast.makeText(scontext , "Project already exists" , Toast.LENGTH_SHORT).show();
        }
    }
}
