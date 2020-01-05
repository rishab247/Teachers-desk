package com.cu.project.Util;

import android.util.Log;

import org.json.JSONObject;

public class JsonEncoder {

    private String[] keyarr = util.keyarr;

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

         Log.v("jsonarray" , information);



     }catch (Exception e)
     {
         System.out.println(e);
     }
 }

}
