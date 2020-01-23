package com.cu.project.Util;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import com.cu.project.APIHelper.ApiPOST;
import com.cu.project.APIHelper.ApiPostPatent;
import com.cu.project.APIHelper.ApiPostProject;
import com.cu.project.APIHelper.ApiPostPub;
import com.cu.project.APIHelper.RegisterAPIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonEncoder {


    private String[] keyarr = util.keyarr;
    public static Context scontext;
    private String[] h_arr = util.honor_key;

    private String[] pub_arr = util.pub_key;

    private String[] pat_arr = util.patent_key;

    private String[] pro_arr = util.project_key;


    public JsonEncoder(Context context)
    {
        scontext = context;
    }

 public void jsonify(String[] valuearr){




     try{

         if (keyarr.length != valuearr.length) {
             throw new Exception();
         }


         JSONObject jsonObject = new JSONObject();

         for(int i =0;i < keyarr.length ; i ++)
         {
             jsonObject.put(keyarr[i] , valuearr[i]);
         }



         String information = jsonObject.toString();

         RegisterAPIHelper apiHelper = new RegisterAPIHelper();

         apiHelper.execute(information , util.url);


         Log.v("jsonarray" , information);



     }catch (Exception e)
     {
         System.out.println(e);
     }
 }

 public void jsonify_honor(String[] str)
 {


     try{

         if (h_arr.length != str.length) {
             throw new Exception();
         }


         JSONObject jsonObject = new JSONObject();

         for(int i =0;i < h_arr.length ; i ++)
         {
             jsonObject.put(h_arr[i] , str[i]);
         }



         String information = jsonObject.toString();

         ApiPOST apiHelper = new ApiPOST(scontext);

         apiHelper.execute(information);


         Log.v("jsonarray_honor" , information);



     }catch (Exception e)
     {
         System.out.println(e);
     }

 }


    public void jsonify_pub(String[] str) {

        JSONObject jsonObject = new JSONObject();
        try {

            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < list1.size() ; i ++)
                jsonArray.put(list1.get(i));

            JSONArray jsonArray2 = new JSONArray();
//
//            List<Integer> list2 = new ArrayList<>();
//            list2.add(1);
//
//            for(int i =0;i < list2.size() ; i ++)
//                jsonArray2.put(list2.get(i));

            JSONArray jsonArray3 = new JSONArray();

            List<String> list3 = new ArrayList<>();
            list3.add("Tushar");
            list3.add("email");
            list3.add("pnumber");

            for(int i =0;i < list3.size() ; i ++)
                jsonArray3.put(list3.get(i));

            JSONArray jsonArray1 = new JSONArray();
            jsonArray1.put(jsonArray3);


            jsonObject.put(pub_arr[0] , "1");
            jsonObject.put(pub_arr[1] , jsonArray);
            jsonObject.put(pub_arr[2] , jsonArray2);
            jsonObject.put(pub_arr[3] , str[0]);
            jsonObject.put(pub_arr[4] , str[1]);
            jsonObject.put(pub_arr[5] , str[2]);
            jsonObject.put(pub_arr[6] , str[3]);
            jsonObject.put(pub_arr[7] , str[4]);
            jsonObject.put(pub_arr[8] , jsonArray1);

            String information = jsonObject.toString();

            Log.e("JSON FILE" , information);

            ApiPostPub apiPostPub = new ApiPostPub();
            apiPostPub.execute(information);





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void jsonify_patent(String[] str)
    {
        JSONObject jsonObject = new JSONObject();

        try {

            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < list1.size() ; i ++)
                jsonArray.put(list1.get(i));

            JSONArray jsonArray2 = new JSONArray();
//
//            List<Integer> list2 = new ArrayList<>();
//            list2.add(1);
//
//            for(int i =0;i < list2.size() ; i ++)
//                jsonArray2.put(list2.get(i));

            JSONArray jsonArray3 = new JSONArray();

            List<String> list3 = new ArrayList<>();
            list3.add("Tushar");
            list3.add("email");
            list3.add("pnumber");

            for(int i =0;i < list3.size() ; i ++)
                jsonArray3.put(list3.get(i));

            JSONArray jsonArray1 = new JSONArray();
            jsonArray1.put(jsonArray3);


            jsonObject.put(pat_arr[0] , "1");
            jsonObject.put(pat_arr[1] , jsonArray);
            jsonObject.put(pat_arr[2] , jsonArray2);
            jsonObject.put(pat_arr[3] , str[0]);
            jsonObject.put(pat_arr[4] , str[1]);
            jsonObject.put(pat_arr[5] , str[2]);
            jsonObject.put(pat_arr[6] , str[3]);
            jsonObject.put(pat_arr[7] , str[4]);
            jsonObject.put(pat_arr[8] , str[5]);
            jsonObject.put(pat_arr[9] , jsonArray1);

            String information = jsonObject.toString();

            Log.e("JSON FILE FOR PATENT" , information);

            ApiPostPatent apiPostPatent = new ApiPostPatent();
            apiPostPatent.execute(information);


        }catch (JSONException e){
            e.printStackTrace();
        }


    }


    public void jsonify_project(String[] str)
    {
        JSONObject jsonObject = new JSONObject();

        try {

            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < list1.size() ; i ++)
                jsonArray.put(list1.get(i));

            JSONArray jsonArray2 = new JSONArray();
//
//            List<Integer> list2 = new ArrayList<>();
//            list2.add(1);
//
//            for(int i =0;i < list2.size() ; i ++)
//                jsonArray2.put(list2.get(i));

            JSONArray jsonArray3 = new JSONArray();

            List<String> list3 = new ArrayList<>();
            list3.add("Tushar");
            list3.add("email");
            list3.add("pnumber");

            for(int i =0;i < list3.size() ; i ++)
                jsonArray3.put(list3.get(i));

            JSONArray jsonArray1 = new JSONArray();
            jsonArray1.put(jsonArray3);


            jsonObject.put(pro_arr[0] , "1");
            jsonObject.put(pro_arr[1] , jsonArray);
            jsonObject.put(pro_arr[2] , jsonArray2);
            jsonObject.put(pro_arr[3] , str[0] );
            jsonObject.put(pro_arr[4] , "project");
            jsonObject.put(pro_arr[5] , str[1] );
            jsonObject.put(pro_arr[6] , str[2] );
            jsonObject.put(pro_arr[7] , str[3] );
            jsonObject.put(pro_arr[8] , jsonArray1);


            String information = jsonObject.toString();
            ApiPostProject apiPostProject = new ApiPostProject();
            apiPostProject.execute(information);



        }catch (JSONException e){
            e.printStackTrace();
        }



    }


}
