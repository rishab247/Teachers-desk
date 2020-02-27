package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.LoginFilter;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Profiile.Author;
import com.cu.project.ui.Profiile.Pdfmaterial;
import com.cu.project.ui.Profiile.SubjectData;
import com.cu.project.ui.Profiile.patentpdf;
import com.cu.project.ui.Profiile.projectpdf;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.PdfPTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiDownload extends AsyncTask<String , Void , HashMap<String , ArrayList>> {

    private ArrayList<Pdfmaterial> listitems =  new ArrayList<>();
    private ArrayList<patentpdf> listitems1 =  new ArrayList<>();
    private ArrayList<projectpdf> listitems2 =  new ArrayList<>();
    private ArrayList<Pdfmaterial> listitems3 =  new ArrayList<>();

    HashMap<String , ArrayList> map = new HashMap<>();

    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;
    private WeakReference<Context> contextRef;
    ProgressDialog dialog;

    String url = "https://apitims1.azurewebsites.net/report/download?token=";

    public ApiDownload(Context context) {
        contextRef = new WeakReference<>(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected HashMap<String , ArrayList> doInBackground(String... voids) {

        String mMessage = null;




        String startdate = voids[0];
        String enddate = voids[1];
        String type = voids[2];

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        Context scontext = contextRef.get();

        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if (sharedpreferences.getLong("Exp_time", 0) < System.currentTimeMillis())
            editor.clear();
        String token = sharedpreferences.getString("Token", "");


        url = url + token;


        Log.e("URL DOWNLOAD", url);

        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("datestart", startdate);
            jsonObject1.put("dateend", enddate);
            jsonObject1.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String str = jsonObject1.toString();
        Log.e("JSON STR " , str );
        RequestBody body = RequestBody.create(MEDIA_TYPE, str);

        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = null;


        try {
            response = client.newCall(request).execute();


            mMessage = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(mMessage);


            JSONArray pubarray = null;
            JSONArray patentarray = null;
            JSONArray projectarray = null;
            JSONArray honorarray = null;


            if(type.equals("Publication")){
                pubarray = jsonObject.getJSONArray("Publication");


                ArrayList<Author> author = new ArrayList<>();
int k=0;
                for (int i = 0;i < pubarray.length() - 1; i ++ ){

                    JSONArray jsonArray = pubarray.getJSONArray(i);

                    if(!jsonArray.get(0).toString().equals(pubarray.getJSONArray(i + 1).get(0).toString())){
                        Log.e(TAG, "doInBackground: id"+ jsonArray.get(0).toString());

                        k++;
                        author.add(new Author(jsonArray.get(6).toString() , jsonArray.get(7).toString() , jsonArray.get(8).toString()));

                        listitems.add(new Pdfmaterial(jsonArray.get(1).toString(),
                                jsonArray.get(2).toString(),
                                jsonArray.get(3).toString(),
                                jsonArray.get(4).toString(),
                                jsonArray.get(5).toString(),
                                author));




                        Log.e(TAG, "doInBackground: k"+k );
                        author=new ArrayList<>();
                     }
                    else {
                         author.add(new Author(jsonArray.get(6).toString(), jsonArray.get(7).toString(), jsonArray.get(8).toString()));
                    }
                }

//                author.clear();


                JSONArray jsonArray = pubarray.getJSONArray(pubarray.length() - 1);

                author.add(new Author(jsonArray.get(6).toString() , jsonArray.get(7).toString() , jsonArray.get(8).toString()));

                listitems.add(new Pdfmaterial(jsonArray.get(1).toString() ,
                        jsonArray.get(2).toString() ,
                        jsonArray.get(3).toString(),
                        jsonArray.get(4).toString(),
                        jsonArray.get(5).toString(),
                        author));

                //author.clear();


                Log.e("SIZE OF LIST" , String.valueOf(listitems.size()));

                // printing all the values of the patent titles
                for(int i = 0;i < listitems.size();i ++){

                    Pdfmaterial pdfmaterial = listitems.get(i);


                    Log.e("Publications " , listitems.get(i).getTitle());


                    Log.e("AUTHORS SIZE " , String.valueOf(pdfmaterial.getAuthor().size()));

                    ArrayList<Author> author2 = pdfmaterial.getAuthor();

                    for(int j =0;j < author2.size() ; j++){
                        Author author1 = author2.get(j);

                       Log.e("TAG" , author1.getName() + " " + author1.getEmail() + " " + author1.getPno());
                    }
                }

                map.put("1" , listitems);

            }
//
//            else if(type.equals("Patent")){
//                patentarray = jsonObject.getJSONArray("Patent");
//
//                ArrayList<patentpdf> temp = new ArrayList<>();
//
//                for (int i = 0;i < patentarray.length() - 1; i ++ ){
//
//                    JSONArray jsonArray = patentarray.getJSONArray(i);
//
//                    if(!jsonArray.get(0).toString().equals(patentarray.getJSONArray(i + 1).get(0).toString())){
//
//                        author.add(new Author(jsonArray.get(7).toString() , jsonArray.get(8).toString() , jsonArray.get(9).toString()));
//
//                        temp.add(new patentpdf(jsonArray.get(1).toString() ,
//                                jsonArray.get(2).toString() ,
//                                jsonArray.get(3).toString(),
//                                jsonArray.get(4).toString(),
//                                jsonArray.get(5).toString(),
//                                jsonArray.get(6).toString(),
//                                author));
//
//                        author.clear();
//                    }
//
//                    author.add(new Author(jsonArray.get(7).toString() , jsonArray.get(8).toString() , jsonArray.get(9).toString()));
//
//                }
//
//                JSONArray jsonArray = patentarray.getJSONArray(patentarray.length() - 1);
//
//                author.add(new Author(jsonArray.get(7).toString() , jsonArray.get(8).toString() , jsonArray.get(9).toString()));
//
//                temp.add(new patentpdf(jsonArray.get(1).toString() ,
//                        jsonArray.get(2).toString() ,
//                        jsonArray.get(3).toString(),
//                        jsonArray.get(4).toString(),
//                        jsonArray.get(5).toString(),
//                        jsonArray.get(6).toString(),
//                        author));
//
//                for(int i = 0;i < author.size() ; i ++){
//                    Author author1 = author.get(i);
//                    Log.e("AUTHORS " , author1.toString());
//                }
//
//
//
//                author.clear();
//
//
//                listitems1 = temp;
//
//                // printing all the values of the patent titles
//                for(int i = 0;i < listitems1.size();i ++){
//
//
//
//                    patentpdf pdfmaterial = listitems1.get(i);
//
//                        Log.e("Patents " , pdfmaterial.getTitle() + pdfmaterial.getArrayList().size());
//                }
//
//                map.put("2" , listitems1);
//            }
//
//            else if(type.equals("Project")){
//                projectarray = jsonObject.getJSONArray("Project");
//
//
//                ArrayList<projectpdf> temp2 = new ArrayList<>();
//
//                for (int i = 0;i < projectarray.length() - 1; i ++ ){
//
//                    JSONArray jsonArray2 = projectarray.getJSONArray(i);
//
//                    if(!jsonArray2.get(0).toString().equals(projectarray.getJSONArray(i + 1).get(0).toString())){
//
//                        author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                        temp2.add(new projectpdf(jsonArray2.get(1).toString() ,
//                                jsonArray2.get(2).toString() ,
//                                jsonArray2.get(3).toString(),
//                                jsonArray2.get(4).toString(),
//                                author));
//
//                        author.clear();
//                    }
//
//                    author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                }
//
//                JSONArray jsonArray2 = projectarray.getJSONArray(projectarray.length() - 1);
//
//                author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                temp2.add(new projectpdf(jsonArray2.get(1).toString() ,
//                        jsonArray2.get(2).toString() ,
//                        jsonArray2.get(3).toString(),
//                        jsonArray2.get(4).toString(),
//                        author));
//
//
//
//                author.clear();
//
//
//                listitems2 = temp2;
//
//                // printing all the values of the patent titles
//                for(int i = 0;i < listitems2.size();i ++){
//
//
//
//                    projectpdf pdfmaterial = listitems2.get(i);
//
//                    Log.e("Projects " , pdfmaterial.getTitle());
//                }
//
//                map.put("3" , listitems2);
//
//            }
//            else if(type.equals("HonorsandAward")){
//                honorarray = jsonObject.getJSONArray("Honors_and_Award");
//
//                Log.e("RES " , mMessage);
//
//                for(int  i= 0 ;i < honorarray.length() ; i++){
//                    JSONArray jsonArray = honorarray.getJSONArray(i);
//
//                    Log.e("HONOR " , jsonArray.get(0).toString() + jsonArray.get(1).toString() + jsonArray.get(2).toString() + jsonArray.get(3).toString());
//
//
//                }
//
//                map.put("4" , listitems3);
//            }
//            else {
//                pubarray = jsonObject.getJSONArray("Publication");
//                patentarray = jsonObject.getJSONArray("Patent");
//                projectarray = jsonObject.getJSONArray("Project");
//                honorarray = jsonObject.getJSONArray("Honor_and_Award");
//
//
//                ArrayList<Pdfmaterial> temp = new ArrayList<>();
//                Log.e(TAG, "doInBackground: "+pubarray.length()  );
//                for (int i = 0;i < pubarray.length() - 1; i ++ ){
//
//                    JSONArray jsonArray = pubarray.getJSONArray(i);
//
//                    if(!jsonArray.get(0).toString().equals(pubarray.getJSONArray(i + 1).get(0).toString())){
//
//                        author.add(new Author(jsonArray.get(6).toString() , jsonArray.get(7).toString() , jsonArray.get(8).toString()));
//
//                        temp.add(new Pdfmaterial(jsonArray.get(1).toString() ,
//                                jsonArray.get(2).toString() ,
//                                jsonArray.get(3).toString(),
//                                jsonArray.get(4).toString(),
//                                jsonArray.get(5).toString(),
//                                author));
//                        author.clear();
//                    }
//
//                    author.add(new Author(jsonArray.get(6).toString() , jsonArray.get(7).toString() , jsonArray.get(8).toString()));
//
//                }
//
//                JSONArray jsonArray = pubarray.getJSONArray(pubarray.length() - 1);
//
//                author.add(new Author(jsonArray.get(6).toString() , jsonArray.get(7).toString() , jsonArray.get(8).toString()));
//
//                temp.add(new Pdfmaterial(jsonArray.get(1).toString() ,
//                        jsonArray.get(2).toString() ,
//                        jsonArray.get(3).toString(),
//                        jsonArray.get(4).toString(),
//                        jsonArray.get(5).toString(),
//                        author));
//
//
//
//                author.clear();
//
//
//                listitems = temp;
//
//                // printing all the values of the patent titles
//                for(int i = 0;i < listitems.size();i ++){
//
//
//
//                    Pdfmaterial pdfmaterial = listitems.get(i);
//
//                    Log.e("Publications " , pdfmaterial.getTitle());
//                }
//
//                ArrayList<patentpdf> temp1 = new ArrayList<>();
//
//                for (int i = 0;i < patentarray.length() - 1; i ++ ){
//
//                    JSONArray jsonArray1 = patentarray.getJSONArray(i);
//
//                    if(!jsonArray1.get(0).toString().equals(patentarray.getJSONArray(i + 1).get(0).toString())){
//
//                        author.add(new Author(jsonArray1.get(7).toString() , jsonArray1.get(8).toString() , jsonArray1.get(9).toString()));
//
//                        temp1.add(new patentpdf(jsonArray1.get(1).toString() ,
//                                jsonArray1.get(2).toString() ,
//                                jsonArray1.get(3).toString(),
//                                jsonArray1.get(4).toString(),
//                                jsonArray1.get(5).toString(),
//                                jsonArray1.get(6).toString(),
//                                author));
//
//                        author.clear();
//                    }
//
//                    author.add(new Author(jsonArray1.get(7).toString() , jsonArray1.get(8).toString() , jsonArray1.get(9).toString()));
//
//                }
//
//                JSONArray jsonArray1 = patentarray.getJSONArray(patentarray.length() - 1);
//
//                author.add(new Author(jsonArray1.get(7).toString() , jsonArray1.get(8).toString() , jsonArray1.get(9).toString()));
//
//                temp1.add(new patentpdf(jsonArray1.get(1).toString() ,
//                        jsonArray1.get(2).toString() ,
//                        jsonArray1.get(3).toString(),
//                        jsonArray1.get(4).toString(),
//                        jsonArray1.get(5).toString(),
//                        jsonArray1.get(6).toString(),
//                        author));
//
//
//
//                author.clear();
//
//
//                listitems1 = temp1;
//
//                // printing all the values of the patent titles
//                for(int i = 0;i < listitems1.size();i ++){
//
//
//
//                    patentpdf pdfmaterial = listitems1.get(i);
//
//                    Log.e("Patents " , pdfmaterial.getTitle());
//                }
//
//
//                ArrayList<projectpdf> temp2 = new ArrayList<>();
//
//                for (int i = 0;i < projectarray.length() - 1; i ++ ){
//
//                    JSONArray jsonArray2 = projectarray.getJSONArray(i);
//
//                    if(!jsonArray2.get(0).toString().equals(projectarray.getJSONArray(i + 1).get(0).toString())){
//
//                        author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                        temp2.add(new projectpdf(jsonArray2.get(1).toString() ,
//                                jsonArray2.get(2).toString() ,
//                                jsonArray2.get(3).toString(),
//                                jsonArray2.get(4).toString(),
//                                author));
//
//                        author.clear();
//                    }
//
//                    author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                }
//
//                JSONArray jsonArray2 = projectarray.getJSONArray(projectarray.length() - 1);
//
//                author.add(new Author(jsonArray2.get(5).toString() , jsonArray2.get(6).toString() , jsonArray2.get(7).toString()));
//
//                temp2.add(new projectpdf(jsonArray2.get(1).toString() ,
//                        jsonArray2.get(2).toString() ,
//                        jsonArray2.get(3).toString(),
//                        jsonArray2.get(4).toString(),
//                        author));
//
//
//
//                author.clear();
//
//
//                listitems2 = temp2;
//
//                // printing all the values of the patent titles
//                for(int i = 0;i < listitems2.size();i ++){
//
//
//
//                    projectpdf pdfmaterial = listitems2.get(i);
//
//                    Log.e("Projects " , pdfmaterial.getTitle());
//                }
//
//
//                map.put("1" , listitems);
//                map.put("2" , listitems1);
//                map.put("3" , listitems2);
//                map.put("4" , listitems3);

//            }




        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String , ArrayList> mp) {
        super.onPostExecute(mp);

        if (dialog.isShowing()) {
            dialog.cancel();
        }

    }


}
