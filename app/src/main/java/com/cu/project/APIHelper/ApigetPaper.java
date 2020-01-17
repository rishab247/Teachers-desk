package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;


import com.cu.project.ui.Profiile.Achievements;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApigetPaper extends AsyncTask<String , Void , List<Achievements>> {

    String urls = "https://apitims1.azurewebsites.net/user/Accomplishment?token=";

    List<Achievements> listitems =  new ArrayList<>();

    public static int counthonor =0 ;
    public static int countpatent= 0;
    public static int countpub =0 ;
    public static int countproject = 0;

    @Override
    protected List<Achievements> doInBackground(String... voids) {

        String jsonData;


        OkHttpClient client = new OkHttpClient();

        String token = voids[0];

        urls = urls + token;


        Log.e("URL" , urls);

        Request request = new Request.Builder()
                .url(urls)
                .build();
        Response responses = null;


        try {

            responses = client.newCall(request).execute();


            jsonData = responses.body().string();

            Log.v("Jsondata" , jsonData);

            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray honorarry = jsonObject.getJSONArray("Honors_and_Award");
            JSONArray patentarry = jsonObject.getJSONArray("Patent");
            JSONArray projectarry = jsonObject.getJSONArray("Project");
            JSONArray pubarry = jsonObject.getJSONArray("Publication");

            Log.e("Rcords " , honorarry +  " " + patentarry + " " + projectarry + " " + pubarry);


            for(int i= 0;i < pubarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);
                countpub++;

                Log.e("1st" , jsonObject1.get(1).toString() + " " + jsonObject1.get(2).toString());


                listitems.add(new Achievements(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), "PUBLICATION"));

            }

            for(int i= 0;i < patentarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);
                countpatent++;


                listitems.add(new Achievements(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), "PATENT"));

            }


            for(int i= 0;i < projectarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);

                countproject++;
                listitems.add(new Achievements(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim() , "PROJECT"));

            }


            for(int i= 0;i < honorarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);
                counthonor++;

                listitems.add(new Achievements(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), "HONOR AND AWARD"));

            }



        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return listitems;
    }
}
