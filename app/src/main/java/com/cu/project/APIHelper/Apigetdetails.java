package com.cu.project.APIHelper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.ui.detailclass;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Apigetdetails extends AsyncTask<String , Void , String[]> {

    String token = loginActivity.gettoken();

    String url = "https://apitims1.azurewebsites.net/user/Accomplishmen/Details?token=";

    String mMessage = "";
    ProgressDialog dialog;
    Context sContext;
    public static String[] infoarray = {""};


    public Apigetdetails(Context context)
    {
        this.sContext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected String[] doInBackground(String... voids) {

        final String[] data = null;


        url = url + token;
        Log.e("ACCOM DETAIL", url);

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

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    mMessage = e.getMessage();
                    Log.v("failure Response", mMessage);

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    mMessage = response.body().string();
                    Log.e("responce from post", mMessage);

                    JSONArray datarray = null;

                    try {
                        JSONObject jsonObject1 = new JSONObject(mMessage);
                        Log.e("FSFDSDSFA", jsonObject1.toString());


                        datarray = jsonObject1.getJSONArray("data");

                        Log.e("data", datarray.get(0).toString());

                    } catch (JSONException e) {
                        Log.e("ERROR ", e.getMessage());
                    }


                    String title, issuer, date, des;
                    String publisher, url;
                    String poffice, appno;


                    if (type.equals("Honors_and_Award")) {
                        try {
                            title = datarray.get(2).toString();
                            issuer = datarray.get(3).toString();
                            date = datarray.get(4).toString();
                            des = datarray.get(5).toString();
                            infoarray = new String[]{title, issuer, date, des, type};


                            Log.e("PRINT", title + issuer + date + des);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (type.equals("Patent")) {
                            try {
                                title = datarray.get(1).toString();
                                poffice = datarray.get(2).toString();
                                appno = datarray.get(3).toString();
                                date = datarray.get(4).toString();
                                des = datarray.get(5).toString();
                                url = datarray.get(6).toString();
                                infoarray = new String[]{title, poffice, appno, date, des, url, type};


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (type.equals("Publication")) {
                            try {
                                title = datarray.get(1).toString();
                                publisher = datarray.get(2).toString();
                                date = datarray.get(3).toString();
                                des = datarray.get(4).toString();
                                url = datarray.get(5).toString();
                                infoarray = new String[]{title, publisher, date, des, url, type};


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                title = datarray.get(1).toString();
                                date = datarray.get(2).toString();
                                des = datarray.get(3).toString();
                                url = datarray.get(4).toString();
                                infoarray = new String[]{title, date, des, url, type};

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //data = infoarray;

                }

            });


            for (String s : infoarray) {
                Log.e("MSSAGE", s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infoarray;
    }

        @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        for(int i= 0; i <strings.length ;i ++)
            System.out.println(strings[i]);

        Intent intent = new Intent(sContext , detailclass.class);
        intent.putExtra("info" , strings);
        sContext.startActivity(intent);

    }


}

