package com.cu.project.APIHelper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.ui.detailclass;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Apigetdetails extends AsyncTask<String , Void , String[]> {

    String token = loginActivity.gettoken();

    ArrayList<String> listitems = new ArrayList<>();

    String url = "https://apitims1.azurewebsites.net/user/Accomplishmen/Details?token=";

    String mMessage = "";
    ProgressDialog dialog;
    Context sContext;


    public Apigetdetails(Context context) {
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
    protected String[] doInBackground(String... voids) {

        url = url + token;
        String[] infoarray = null;


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        String id = voids[0];
        final String type = voids[1];

        OkHttpClient client = new OkHttpClient();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("Type", type);

            Log.e("JSON STRING", jsonObject.toString());


            RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url).post(body).build();

//            String responce = client.newCall(request).execute().toString();
//            String mssage = responce.string();


            Response response = client.newCall(request).execute();

            String res;


            JSONArray jsonArray = null;

            if (!response.isSuccessful()) {
                res = String.valueOf(response.code());
            }
            //System.out.println(response.body().string());


            else {
                JSONObject jsonObject1 = new JSONObject(response.body().string());

                jsonArray = jsonObject1.getJSONArray("data");


            }
            for (int i = 0; i < jsonArray.length(); i++)
                System.out.println(jsonArray.get(i));

            String title, issuer, date, des;
            String publisher, url;
            String poffice, appno;


            if (type.equals("Honors_and_Award")) {
                title = jsonArray.get(2).toString();
                issuer = jsonArray.get(3).toString();
                date = jsonArray.get(4).toString();
                des = jsonArray.get(5).toString();
                infoarray = new String[]{title, issuer, date, des, type};


                Log.e("PRINT", title + issuer + date + des);

            } else {
                if (type.equals("Patent")) {
                    title = jsonArray.get(1).toString();
                    poffice = jsonArray.get(2).toString();
                    appno = jsonArray.get(3).toString();
                    date = jsonArray.get(4).toString();
                    des = jsonArray.get(5).toString();
                    url = jsonArray.get(6).toString();
                    infoarray = new String[]{title, poffice, appno, date, des, url, type};


                } else if (type.equals("Publication")) {

                    title = jsonArray.get(1).toString();
                    publisher = jsonArray.get(2).toString();
                    date = jsonArray.get(3).toString();
                    des = jsonArray.get(4).toString();
                    url = jsonArray.get(5).toString();
                    infoarray = new String[]{title, publisher, date, des, url, type};

                } else {
                    title = jsonArray.get(1).toString();
                    date = jsonArray.get(2).toString();
                    des = jsonArray.get(3).toString();
                    url = jsonArray.get(4).toString();
                    infoarray = new String[]{title, date, des, url, type};
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i =0;i < infoarray.length; i ++)
        {
            System.out.println(infoarray[i]);

        }
        return infoarray;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        Intent intent = new Intent(sContext, detailclass.class);
        intent.putExtra("info" , strings);

        sContext.startActivity(intent);
        dialog.hide();
    }
}