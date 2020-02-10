package com.cu.project.APIHelper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncAdapterType;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.cu.project.ui.Profiile.SubjectData;
import com.cu.project.ui.detailclass;
import com.cu.project.ui.login.loginActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Apigetdetails extends AsyncTask<String , Void , HashMap<String , ArrayList>> {

    String token = loginActivity.gettoken();


    String url = "https://apitims1.azurewebsites.net/user/Accomplishmen/Details?token=";
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    String mMessage = "";
    private ProgressDialog dialog;
      Context sContext;


    public Apigetdetails(Context context) {
        this.sContext = context;
        sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(sContext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        token = sharedpreferences.getString("Token", "");
        if(token.equals("")){
            Log.e(TAG, "gettoken: token doesnot exists or expired " );
        }


    }

    @Override
    protected HashMap<String, ArrayList> doInBackground(String... voids) {

        HashMap<String, ArrayList> map = new HashMap<>();

        url = url + token;
        Log.d("URL VERIFY1", "doInBackground: "+url);


        ArrayList<String> infoarray = new ArrayList<>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> pnos = new ArrayList<>();

        names.clear();
        emails.clear();
        pnos.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        String id = voids[0];
        final String type = voids[1];

        OkHttpClient client = new OkHttpClient();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("Type", type);

            Log.e("JSON" , jsonObject.toString());


            RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url).post(body).build();

            Response response = client.newCall(request).execute();

            String res;


            JSONArray jsonArray = null;

            JSONArray authorjsonarr = null;

            if (!response.isSuccessful()) {
                res = String.valueOf(response.code());
            }

            else {
                JSONObject jsonObject1 = new JSONObject(response.body().string());

                jsonArray = jsonObject1.getJSONArray("data");

                if(!type.equals("Honors_and_Award")){
                    authorjsonarr = jsonObject1.getJSONArray("author");

                    Log.e("DATA" , authorjsonarr.toString());
                }


            }



            String title, issuer, date, des;
            String publisher, url;
            String poffice, appno;




            if (type.equals("Honors_and_Award")) {
                title = jsonArray.get(2).toString();
                issuer = jsonArray.get(3).toString();
                date = jsonArray.get(4).toString();
                des = jsonArray.get(5).toString();
                infoarray.add(title);
                infoarray.add(issuer);
                infoarray.add(date);
                infoarray.add(des);
                infoarray.add(type);
                infoarray.add(id);


            } else {
                if (type.equals("Patent")) {


                    title = jsonArray.get(1).toString();
                    poffice = jsonArray.get(2).toString();
                    appno = jsonArray.get(3).toString();
                    date = jsonArray.get(4).toString();
                    des = jsonArray.get(5).toString();
                    url = jsonArray.get(6).toString();

                    infoarray.add(title);
                    infoarray.add(poffice);
                    infoarray.add(appno);
                    infoarray.add(date);
                    infoarray.add(des);
                    infoarray.add(url);
                    infoarray.add(type);
                    infoarray.add(id);



                    // authors details

                    for(int i=0;i < authorjsonarr.length(); i ++)
                    {
                        JSONArray jsonArray1 = authorjsonarr.getJSONArray(i);

                        names.add(jsonArray1.get(1).toString());
                        emails.add(jsonArray1.get(2).toString());
                        pnos.add(jsonArray1.get(3).toString());
                    }


                } else if (type.equals("Publication")) {

                    title = jsonArray.get(1).toString();
                    publisher = jsonArray.get(2).toString();
                    date = jsonArray.get(3).toString();
                    des = jsonArray.get(4).toString();
                    url = jsonArray.get(5).toString();

                    infoarray.add(title);
                    infoarray.add(publisher);
                    infoarray.add(date);
                    infoarray.add(des);
                    infoarray.add(url);
                    infoarray.add(type);
                    infoarray.add(id);

                    // authors details

                    for(int i=0;i < authorjsonarr.length(); i ++)
                    {
                        JSONArray jsonArray1 = authorjsonarr.getJSONArray(i);

                        names.add(jsonArray1.get(1).toString());
                        emails.add(jsonArray1.get(2).toString());
                        pnos.add(jsonArray1.get(3).toString());
                    }

                } else {
                    title = jsonArray.get(1).toString();
                    date = jsonArray.get(2).toString();
                    des = jsonArray.get(3).toString();
                    url = jsonArray.get(4).toString();
                    infoarray.add(title);
                    infoarray.add(date);
                    infoarray.add(des);
                    infoarray.add(url);
                    infoarray.add(type);
                    infoarray.add(id);

                    // authors details

                    for(int i=0;i < authorjsonarr.length(); i ++)
                    {
                        JSONArray jsonArray1 = authorjsonarr.getJSONArray(i);

                        names.add(jsonArray1.get(1).toString());
                        emails.add(jsonArray1.get(2).toString());
                        pnos.add(jsonArray1.get(3).toString());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("1" , names);
        map.put("2" , emails);
        map.put("3" , pnos);
        map.put("4" , infoarray);


        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String , ArrayList> strings) {
        super.onPostExecute(strings);

        Intent intent = new Intent(sContext, detailclass.class);
        intent.putExtra("info" , strings);

        sContext.startActivity(intent);
        dialog.hide();
    }
}