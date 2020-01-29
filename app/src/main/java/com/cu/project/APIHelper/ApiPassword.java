package com.cu.project.APIHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiPassword extends AsyncTask<String , Void , Integer> {

    public Integer code;
    String url = "https://apitims1.azurewebsites.net/Verify/password?token=";
    String token = loginActivity.gettoken();

    String oldpass = null;
    String newpass = null;

    Context sContext;
    ProgressDialog dialog;

    public ApiPassword(Context context)
    {
        this.sContext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... voids) {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();

        url = url + token;
        oldpass = voids[0];
        newpass = voids[1];

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("password" , oldpass);
            jsonObject.put("new_password" , newpass);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(MEDIA_TYPE , jsonObject.toString());

        Request request = new Request.Builder()
                .url(url).put(body).build();


        Integer code = 0;
        try {
            Response response = client.newCall(request).execute();

            code = response.code();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    @Override
    protected void onPostExecute(Integer aVoid) {
        super.onPostExecute(aVoid);
        dialog.hide();

        if(aVoid.equals(200))
        {
            Toast.makeText(sContext , "Password Changed" , Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(sContext , "Password Invalid" , Toast.LENGTH_SHORT).show();
        }

    }
}
