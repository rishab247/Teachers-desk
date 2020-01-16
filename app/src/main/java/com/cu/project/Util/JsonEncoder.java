package com.cu.project.Util;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import com.cu.project.APIHelper.RegisterAPIHelper;

import org.json.JSONObject;

public class JsonEncoder {

    private String[] keyarr = util.keyarr;
    Context scontext;

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

}
