package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIVerifyIndividual extends AsyncTask<Void, Void, String> {


    String url = "https://apitims.azurewebsites.net/vfacultyverify?token=";
    String[] eid = null;
    WeakReference<Context> contextRef;
    int reference;

    ProgressDialog dialog;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public APIVerifyIndividual(Context context, String[] eid,int reference) {
        contextRef = new WeakReference<>(context);
        sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.eid = eid;
        this.reference = reference;
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
    protected String doInBackground(Void... voids) {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String token= sharedpreferences.getString("Token", "");

        url = url + token;

        OkHttpClient client = new OkHttpClient();

        JSONArray jsonArray = new JSONArray();

        for(int i = 0;i < eid.length; i++){
            jsonArray.put(eid[i]);
        }

        Log.e("JSON ARRAY" , jsonArray.toString());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Euid" , jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(MEDIA_TYPE , jsonObject.toString());

        Request request = new Request.Builder().url(url).put(body).build();

        Response response = null;
        String mMessage = null;
        try {
            response = client.newCall(request).execute();

            mMessage = response.body().string();

            JSONObject jsonObject1 = new JSONObject(mMessage);

            mMessage = jsonObject1.getString("msg");

            Log.e("mMessage" , mMessage);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }





        return mMessage;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        if(dialog.isShowing()){
            dialog.cancel();
        }

        Toast.makeText(contextRef.get(), aVoid , Toast.LENGTH_SHORT).show();

        if(reference == 1){
            ((Activity)contextRef.get()).finish();
        }
    }
}
