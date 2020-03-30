package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.cu.project.Individual;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApigetIndividual extends AsyncTask<Void, Void, String[]> {

    private WeakReference<Context> contextRef;
    private String eid;
    private boolean verify;
    ProgressDialog dialog;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    String url = "https://apitims.azurewebsites.net/faculty/Profile?token=";

    public ApigetIndividual(Context context, String eid , boolean vereify) {
        contextRef =new WeakReference<> (context);
        sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.eid = eid;
        this.verify = vereify;
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
    protected String[] doInBackground(Void... voids) {

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

        String[] infoarr = null;

        try {
            response = client.newCall(request).execute();

            String mMessage = response.body().string();

            Log.e(TAG, "doInBackground: " + mMessage);

            JSONObject jsonObject1 = new JSONObject(mMessage);

            JSONArray jsonArray = jsonObject1.getJSONArray("msg");


            String imagestr = (String) jsonObject1.get("pic");

            Log.e("PIC JSON" , imagestr);


            String name = jsonArray.get(0).toString().trim();
            String pno = jsonArray.get(1).toString().trim();
            String mail = jsonArray.get(2).toString().trim();
            String branch = jsonArray.get(3).toString().trim();
            String dob = jsonArray.get(4).toString().trim();
            String doj = jsonArray.get(5).toString().trim();
            String quali = jsonArray.get(6).toString().trim();
            String uni = jsonArray.get(7).toString().trim();



            infoarr = new String[]{name, pno, mail, branch, dob, doj, quali, uni , imagestr};


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return infoarr;
    }

    @Override
    protected void onPostExecute(String[] aVoid) {
        super.onPostExecute(aVoid);

        if(dialog.isShowing()){
            dialog.cancel();
        }

        Context sContext = contextRef.get();

        Intent intent = new Intent(sContext , Individual.class);
        intent.putExtra("information", aVoid);
        intent.putExtra("Verify", verify);
        intent.putExtra("EID" , eid);
        sContext.startActivity(intent);
    }
}
