package com.cu.project.APIHelper;

import android.os.AsyncTask;
import android.util.Log;


import com.cu.project.ui.Profiile.Achievements;
import com.cu.project.ui.Profiile.SubjectData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApigetPaper extends AsyncTask<String , Void , List<Achievements>> {

    String urls = "https://apitims1.azurewebsites.net/user/Accomplishment?token=";

    public static ArrayList<SubjectData> listitems =  new ArrayList<>();
    public static ArrayList<SubjectData> listitems1 =  new ArrayList<>();
    public static ArrayList<SubjectData> listitems2 =  new ArrayList<>();
    public static ArrayList<SubjectData> listitems3 =  new ArrayList<>();

    public static ArrayList<SubjectData> listofitems = new ArrayList<>();


    @Override
    protected List<Achievements> doInBackground(String... voids) {



        String jsonData;

         int counthonor = 0 ;
         int countpatent = 0;
         int countpub = 0 ;
         int countproject = 0;


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

            listitems.clear();
            listitems1.clear();
            listitems2.clear();
            listitems3.clear();


            for(int i= 0;i < pubarry.length() ;i ++)
            {
                JSONArray jsonObject1 = pubarry.getJSONArray(i);
                countpub++;
                listitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), (Integer) jsonObject1.get(0)));
                listofitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), (Integer) jsonObject1.get(0)));
            }



            for(int i= 0;i < patentarry.length() ;i ++)
            {
                JSONArray jsonObject1 = patentarry.getJSONArray(i);
                countpatent++;

                Log.e("1st" , jsonObject1.get(1).toString() + " " + jsonObject1.get(2).toString());

                listitems1.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), (Integer) jsonObject1.get(0)));
                listofitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim(), (Integer) jsonObject1.get(0)));
            }


            for(int i= 0;i < projectarry.length() ;i ++)
            {
                JSONArray jsonObject1 = projectarry.getJSONArray(i);

                countproject++;
                listitems2.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim() , (Integer) jsonObject1.get(0)));
                listofitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , jsonObject1.get(2).toString().trim() , (Integer) jsonObject1.get(0)));
            }


            for(int i= 0;i < honorarry.length() ;i ++)
            {
                JSONArray jsonObject1 = honorarry.getJSONArray(i);
                counthonor++;

                long date1 = Long.parseLong(jsonObject1.get(2).toString().trim());

                DateFormat simple = new SimpleDateFormat("dd MM yyyy");

                Date result1 = new Date(date1);

                String hdate = simple.format(result1);

                listitems3.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate, (Integer)jsonObject1.get(0)));
                listofitems.add(new SubjectData(jsonObject1.get(1).toString().trim() , hdate, (Integer)jsonObject1.get(0)));

            }



        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
