package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.cu.project.ui.Profiile.Pdfmaterial;
import com.cu.project.ui.Profiile.SubjectData;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiDownload extends AsyncTask<String , Void , HashMap<String , ArrayList>> {

    private ArrayList<Pdfmaterial> listitems =  new ArrayList<>();
    private ArrayList<Pdfmaterial> listitems1 =  new ArrayList<>();
    private ArrayList<Pdfmaterial> listitems2 =  new ArrayList<>();
    private ArrayList<Pdfmaterial> listitems3 =  new ArrayList<>();

    HashMap<String , ArrayList> map = new HashMap<>();

    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;
    private WeakReference<Context> contextRef;
    ProgressDialog dialog;

    String url = "https://apitims1.azurewebsites.net/report/download?token=";

    public ApiDownload(Context context) {
        contextRef = new WeakReference<>(context);
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
    protected HashMap<String , ArrayList> doInBackground(String... voids) {

        String mMessage = null;




        String startdate = voids[0];
        String enddate = voids[1];
        String type = voids[2];

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        Context scontext = contextRef.get();

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if (sharedpreferences.getLong("Exp_time", 0) < System.currentTimeMillis())
            editor.clear();
        String token = sharedpreferences.getString("Token", "");


        url = url + token;


        Log.e("URL DOWNLOAD", url);

        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("datestart", startdate);
            jsonObject1.put("dateend", enddate);
            jsonObject1.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String str = jsonObject1.toString();
        RequestBody body = RequestBody.create(MEDIA_TYPE, str);

        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = null;


        try {
            response = client.newCall(request).execute();


            mMessage = Objects.requireNonNull(response.body()).string();

            JSONObject jsonObject = new JSONObject(mMessage);

            JSONArray honorarry = jsonObject.getJSONArray("Honors_and_Award");
            JSONArray patentarry = jsonObject.getJSONArray("Patent");
            JSONArray projectarry = jsonObject.getJSONArray("Project");
            JSONArray pubarry = jsonObject.getJSONArray("Publication");


            for(int i =0 ;i < pubarry.length() ; i++)
            {

                listitems.add(new Pdfmaterial(pubarry.get(1).toString() , pubarry.get(2).toString() , pubarry.get(3).toString() ,
                        pubarry.get(4).toString() , pubarry.get(5).toString()));
            }


            Log.e("SDFDSFSDFSD" , mMessage);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String , ArrayList> mp) {
        super.onPostExecute(mp);

        if (dialog.isShowing()) {
            dialog.cancel();
        }


    }
}
