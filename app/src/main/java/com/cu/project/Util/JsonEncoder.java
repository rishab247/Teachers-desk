package com.cu.project.Util;

import android.util.Log;

import com.cu.project.APIHelper.ApiPOST;

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

         ApiPOST apiPOST = new ApiPOST();

         apiPOST.execute(information);

         Log.v("jsonarray" , information);



     }catch (Exception e)
     {
         System.out.println(e);
     }
 }

}
