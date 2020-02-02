package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.cu.project.ui.Profiile.SubjectData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApigetPaper extends AsyncTask<String , Void , HashMap<String,ArrayList>> {

    public static int counthonor = 0 ;
    public static int countpatent = 0;
    public static int countpub = 0 ;
    public static int countproject = 0;
    String urls = "https://apitims1.azurewebsites.net/user/Accomplishment?token=";

    private ArrayList<SubjectData> listitems =  new ArrayList<>();
     private ArrayList<SubjectData> listitems1 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems2 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems3 =  new ArrayList<>();



    @Override
    protected HashMap<String, ArrayList> doInBackground(String... voids) {
        HashMap<String, ArrayList> map = new HashMap<>();
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

            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray honorarry = jsonObject.getJSONArray("Honors_and_Award");
            JSONArray patentarry = jsonObject.getJSONArray("Patent");
            JSONArray projectarry = jsonObject.getJSONArray("Project");
            JSONArray pubarry = jsonObject.getJSONArray("Publication");


            listitems.clear();
            listitems1.clear();
            listitems2.clear();
            listitems3.clear();
            map.clear();

            countpatent = 0;
            countproject = 0;
            counthonor = 0;
            countpub = 0;


            for(int i= 0;i < pubarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);
                countpub++;


                long date1 = Long.parseLong(jsonObject1.get(2).toString().trim());

                DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

                Date result1 = new Date(date1);

                String hdate = simple.format(result1);


                listitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate,  (Integer) jsonObject1.get(0)));

            }



            for(int i= 0;i < patentarry.length() ;i ++)
            {
                JSONArray jsonObject1 = patentarry.getJSONArray(i);
                countpatent++;

                long date1 = Long.parseLong(jsonObject1.get(2).toString().trim());

                DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

                Date result1 = new Date(date1);

                String hdate = simple.format(result1);

                listitems1.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate , (Integer) jsonObject1.get(0)));

            }


            for(int i= 0;i < projectarry.length() ;i ++)
            {
                JSONArray jsonObject1 = projectarry.getJSONArray(i);

                countproject++;

                long date1 = Long.parseLong(jsonObject1.get(2).toString().trim());

                DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

                Date result1 = new Date(date1);

                String hdate = simple.format(result1);
                listitems2.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate, (Integer) jsonObject1.get(0)));

            }


            for(int i= 0;i < honorarry.length() ;i ++)
            {
                JSONArray jsonObject1 = honorarry.getJSONArray(i);
                counthonor++;

                long date1 = Long.parseLong(jsonObject1.get(2).toString().trim());

                DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

                Date result1 = new Date(date1);

                String hdate = simple.format(result1);

                listitems3.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate, (Integer)jsonObject1.get(0)));

            }

            countpub = pubarry.length();
            counthonor = honorarry.length();
            countpatent = patentarry.length();
            countproject = projectarry.length();


            Log.e("arrayoflist", "doInBackground: " );


            map.put("1",listitems);
            map.put("2",listitems1);
            map.put("3",listitems2);
            map.put("4",listitems3);

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    protected void onPostExecute(HashMap aVoid) {
        super.onPostExecute(aVoid);
    }
}
