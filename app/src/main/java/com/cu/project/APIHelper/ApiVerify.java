package com.cu.project.APIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.Upload.UploadActivity;
import com.cu.project.ui.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiVerify extends AsyncTask<Void , Void , String[]> {

    String url = "https://apitims1.azurewebsites.net/Verify?token=";

    String[] statusarr = {""};
    private WeakReference<Context> contextRef;
    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
//    Context scontext;
AsyncVerifyResponse asyncVerifyResponse;
    ProgressDialog dialog;
//     String token = loginActivity.gettoken();
public ApiVerify(Context context){
    contextRef =new WeakReference<> (context);
    asyncVerifyResponse = (AsyncVerifyResponse) context;
}


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
        sharedpreferences = scontext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        dialog = new ProgressDialog(scontext);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected String[] doInBackground(Void... voids) {
        if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
            editor.clear();
        String token = sharedpreferences.getString("Token", "");
        if(token.equals("")){
            Log.e(TAG, "gettoken: token doesnot exists or expired " );
        }
        url = url + token;

        Log.e("URL VERIFY" , url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();

        Response response = null;

        try {
            response = client.newCall(request).execute();



            String jsondata = response.body().string();

            JSONObject jsonObject = new JSONObject(jsondata);

            Boolean status = jsonObject.getBoolean("Status");
            Boolean HODstatus = jsonObject.getBoolean("Hod");

            statusarr = new String[]{status.toString(), HODstatus.toString()};

   //         Log.e("INSIDE VERIFY" , statusarr.toString());

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return statusarr;
    }

    @Override
    protected void onPostExecute(String[] aVoid) {
        super.onPostExecute(aVoid);
        if(dialog.isShowing())
        {//        Context scontext = contextRef.get();

            dialog.cancel();
        }
        asyncVerifyResponse.processVerifyFinish(aVoid);

    }

}
