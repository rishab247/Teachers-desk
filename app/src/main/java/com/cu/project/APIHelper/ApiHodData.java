package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.SubjectDataHod;
import com.cu.project.ui.Profiile.SubjectData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiHodData extends AsyncTask<Void, Void , ArrayList> {
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;
    String department;
    Context context;


    ArrayList<SubjectDataHod> listitem = new ArrayList<>();

    private String urls = "https://apitims1.azurewebsites.net//facultylist?token=";
    ProgressDialog dialog;
    private WeakReference<Context> contextRef;

    public AsynHodResponse asynHodResponse = null;

    public ApiHodData(String department, Context context) {
        contextRef =new WeakReference<> (context);
        this.department = department;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
//        dialog = new ProgressDialog(scontext);
//        dialog.setMessage("Please wait...");
//        dialog.setIndeterminate(true);
//        dialog.setCancelable(false);
//        dialog.show();

    }


    @Override
    protected ArrayList doInBackground(Void... voids) {

        Context scontext = contextRef.get();

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token= sharedpreferences.getString("Token", "");

        listitem.clear();


        urls = urls + token;


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Department" , "all");



            RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(urls).post(body).build();

            Response response = client.newCall(request).execute();


            String res = response.body().string();

            JSONObject jsonObject1 = new JSONObject(res);

            Log.e("RESPONSE ", res);

            JSONArray jsonArray = (JSONArray)jsonObject1.get("msg");

            for(int i = 0;i < jsonArray.length() ; i ++){

                JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                listitem.add(new SubjectDataHod(jsonArray1.get(0).toString().trim() , jsonArray1.get(1).toString().trim() , jsonArray1.getBoolean(2)));
            }

            for(int i = 0;i < listitem.size() ;i ++){

                SubjectDataHod subjectDataHod = listitem.get(i);

                Log.e("ITEMS" , subjectDataHod.getEid() + subjectDataHod.getName() + subjectDataHod.getVerify());

            }



        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return listitem;
    }

    @Override
    protected void onPostExecute(ArrayList aVoid) {
        super.onPostExecute(aVoid);

        asynHodResponse.asyncresponsefinish(aVoid);
    }
}
