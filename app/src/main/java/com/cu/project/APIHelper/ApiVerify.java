package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.text.BoringLayout;
import android.util.Log;

import com.cu.project.ui.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiVerify extends AsyncTask<Void , Void , String[]> {

    String url = "https://apitims1.azurewebsites.net/Verify?token=";

    String[] statusarr = {""};

    String token = loginActivity.gettoken();

    @Override
    protected String[] doInBackground(Void... voids) {

        url = url + token;

        Log.e("URL VERIFY" , url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();

        Response response = null;

        try {
            response = client.newCall(request).execute();



            String jsondata = response.body().string();

            JSONObject jsonObject = new JSONObject(jsondata);

            Boolean status = jsonObject.getBoolean("Status");
            Boolean HODstatus = jsonObject.getBoolean("Hod");


            statusarr = new String[]{status.toString(), HODstatus.toString()};

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return statusarr;
    }


    @Override
    protected void onPostExecute(String[] aVoid) {
        super.onPostExecute(aVoid);
    }
}
