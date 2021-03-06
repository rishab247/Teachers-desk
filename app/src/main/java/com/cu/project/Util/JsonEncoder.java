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

 public String jsonify(String[] valuearr){

     String information = null;

     try{


         JSONObject jsonObject = new JSONObject();

         for(int i =0;i < keyarr.length ; i ++)
         {
             jsonObject.put(keyarr[i] , valuearr[i]);
         }

         jsonObject.put("type" , valuearr[valuearr.length - 1]);

         String type;

         if(valuearr[valuearr.length - 1].equals("Faculty"))
         {
             type = "0";
         }
         else
         {
             type = "1";
         }

         jsonObject.put("type" , type);

         information = jsonObject.toString();




     }catch (Exception e)
     {
         System.out.println(e);
     }

     return information;
 }

 public String jsonify_honor(String[] str)
 {

     String information = null;

     try{

         if (h_arr.length != str.length) {
             throw new Exception();
         }


         JSONObject jsonObject = new JSONObject();

         for(int i =0;i < h_arr.length ; i ++)
         {
             jsonObject.put(h_arr[i] , str[i]);
         }
         information = jsonObject.toString();
     }catch (Exception e)
     {
         System.out.println(e.getMessage());
     }

     return information;
 }


    public String jsonify_pub(String[] str , JSONArray jsonArrayofauth , int size) {

        String information = null;
        JSONObject jsonObject = new JSONObject();
        try {


            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < size ; i ++)
                jsonArray.put(1);

            JSONArray jsonArray2 = new JSONArray();

            String size1 = String.valueOf(size);




            jsonObject.put(pub_arr[0] , size1);
            jsonObject.put(pub_arr[1] , jsonArray);
            jsonObject.put(pub_arr[2] , jsonArray2);
            jsonObject.put(pub_arr[3] , str[0]);
            jsonObject.put(pub_arr[4] , str[1]);
            jsonObject.put(pub_arr[5] , str[2]);
            jsonObject.put(pub_arr[6] , str[3]);
            jsonObject.put(pub_arr[7] , str[4]);
            jsonObject.put("type", str[5]);
            jsonObject.put(pub_arr[8] , jsonArrayofauth);

            information = jsonObject.toString();

            Log.e("TAG",information);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return information;


    }

    public String jsonify_patent(String[] str , JSONArray jsonArrayfromback , int size)
    {
        String information = null;
        JSONObject jsonObject = new JSONObject();

        String size1 = String.valueOf(size);

        try {

            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < size ; i ++)
                jsonArray.put(1);

            JSONArray jsonArray2 = new JSONArray();



            jsonObject.put(pat_arr[0] , size1);
            jsonObject.put(pat_arr[1] , jsonArray);
            jsonObject.put(pat_arr[2] , jsonArray2);
            jsonObject.put(pat_arr[3] , str[0]);
            jsonObject.put(pat_arr[4] , str[1]);
            jsonObject.put(pat_arr[5] , str[2]);
            jsonObject.put(pat_arr[6] , str[3]);
            jsonObject.put(pat_arr[7] , str[4]);
            jsonObject.put(pat_arr[8] , str[5]);
            jsonObject.put(pat_arr[9] , jsonArrayfromback);

            information = jsonObject.toString();

            Log.e("JSON FILE FOR PATENT" , information);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return information;
    }


    public String jsonify_project(String[] str , JSONArray jsonArrayfromback , int size)
    {
        JSONObject jsonObject = new JSONObject();

        String information = null;
        try {

            JSONArray jsonArray = new JSONArray();

            List<Integer> list1 = new ArrayList<>();
            list1.add(1);

            for(int i =0;i < size ; i ++)
                jsonArray.put(1);

            String size1 = String.valueOf(size);

            JSONArray jsonArray2 = new JSONArray();




            jsonObject.put(pro_arr[0] , size1);
            jsonObject.put(pro_arr[1] , jsonArray);
            jsonObject.put(pro_arr[2] , jsonArray2);
            jsonObject.put(pro_arr[3] , str[0] );
            jsonObject.put(pro_arr[4] , "project");
            jsonObject.put(pro_arr[5] , str[1] );
            jsonObject.put(pro_arr[6] , str[2] );
            jsonObject.put(pro_arr[7] , str[3] );
            jsonObject.put(pro_arr[8] , jsonArrayfromback);


            information = jsonObject.toString();


        }catch (JSONException e){
            e.printStackTrace();
        }

        return information;


    }


}
