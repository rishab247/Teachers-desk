package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.Individual;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApigetIndividual extends AsyncTask<Void, Void, Void> {

    private WeakReference<Context> contextRef;
    private String eid;
    ProgressDialog dialog;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    String url = "https://apitims1.azurewebsites.net/faculty/Profile?token=";

    public ApigetIndividual(Context context, String eid) {
        contextRef =new WeakReference<> (context);
        sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.eid = eid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context sContext = contextRef.get();

        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String token= sharedpreferences.getString("Token", "");

        url = url + token;

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Euid" , eid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String str = jsonObject.toString();

        RequestBody body = RequestBody.create(MEDIA_TYPE, str);
        Request request = new Request.Builder()
                .url(url).post(body).build();


        Response response = null;

        try {
            response = client.newCall(request).execute();

            String mMessage = response.body().string();

            Log.e(TAG, "doInBackground: " + mMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(dialog.isShowing()){
            dialog.cancel();
        }

        Context sContext = contextRef.get();

        Intent intent = new Intent(sContext , Individual.class);
        sContext.startActivity(intent);
    }
}
