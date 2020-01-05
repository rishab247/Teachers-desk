package com.cu.project.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class util {

        public static String keyarr[] = {"Euid" , "Name" , "Email" , "Password" , "Phone_No" , "Department_Name" , "DOJ" , "Qualifications" , "University" ,
            "DOB" , "Hod_Department"};

        public static final String BASE_URL = "https://apitims1.azurewebsites.net";


        public static JSONObject getObject(String tagname, JSONObject jsonObject) throws JSONException {
            JSONObject jobject = jsonObject.getJSONObject(tagname);
            return jobject;
        }


}
