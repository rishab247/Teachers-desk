package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ApiLogin  extends AsyncTask<Void, Void ,String> {
    public AsyncLoginResponse asyncLoginResponse = null;




    String username , password;
    String res = "";
    ProgressDialog d;
    Context scontext;


    public ApiLogin(Context context , String username , String password)
    {
        this.username = username;
        this.password = password;
        scontext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        d = new ProgressDialog(this.scontext);
        d.setMessage("Please wait...");
        Log.e("Inside Pre execute","call made and loader shown");
        d.setIndeterminate(true);
        d.setCancelable(false);
        d.show();

    }

    @Override
        protected String doInBackground(Void... strings) {
        Log.e("EXCEPTION123" , "class not found");

        try {
            res = testFetchOK();
        } catch (Exception e) {
            Log.e("Login Error" , e.getMessage());
        }
        return res;
    }


    @Override
    protected void onPostExecute(String s) {
        if(d.isShowing()){
            d.cancel();
        }
        asyncLoginResponse.processLoginFinish(s);
    }

    private static OkHttpClient createAuthenticatedClient(final String username,
                                                          final String password) {
        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                if (responseCount(response) >= 3) {
                    return null;
                }
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return httpClient;
    }

    private static String doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();

        String token;
        if (!response.isSuccessful()) {
            token = String.valueOf(response.code());
        }
        else
        {
            JSONObject jsonObject = new JSONObject(response.body().string());

            token = jsonObject.getString("token");
        }

        return token;
    }


    public static String fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);

        String st = doRequest(httpClient , url);

        return st;

    }

    public String testFetchOK() throws Exception {
        String url = "https://apitims1.azurewebsites.net/login";

        Log.e("Printhelper",username + password);


        String token = ApiLogin.fetch(url, username, password);
        return token;
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}