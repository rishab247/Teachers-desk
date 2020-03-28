package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

public class ApiDownloadHod extends AsyncTask<Void, Void, Void> {


    Context sContext;
    private String[] eid;
    private String type;
    private String start, end;

    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;
    private WeakReference<Context> contextRef;
    ProgressDialog dialog;


    String url = "https://apitims1.azurewebsites.net/faculty/download?token=";

    public ApiDownloadHod(Context context, String[] eid, String start, String end, String type) {

        contextRef = new WeakReference<>(context);
        this.eid = eid;
        this.type = type;
        this.start = start;
        this.end = end;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String mMessage = null;

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        Context scontext = contextRef.get();

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if (sharedpreferences.getLong("Exp_time", 0) < System.currentTimeMillis())
            editor.clear();
        String token = sharedpreferences.getString("Token", "");


        url = url + token;


        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < eid.length; i++) {
            jsonArray.put(eid[i]);
        }

        Log.e("JSON ARRAY", jsonArray.toString());

        JSONObject jsonObject = new JSONObject();

        if(type.equals("All")){
            type = "all";
        }
        if(type.equals("Honors and Award")){
            type = "HonorsandAward";
        }

        try {
            jsonObject.put("Euid", jsonArray);
            jsonObject.put("datestart", start);
            jsonObject.put("dateend", end);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "doInBackground: "+ jsonObject.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());

        Request request = new Request.Builder().url(url).post(body).build();



        Response response = null;
        try {
            response = client.newCall(request).execute();

            mMessage = response.body().string();

            Log.e("MESSAGE RESPONSE", mMessage);

            return null;
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
    }
}


