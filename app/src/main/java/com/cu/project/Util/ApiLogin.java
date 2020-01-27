package com.cu.project.Util;

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




    String username , password;
    String res = "";
    ProgressDialog d;
    Context scontext;


    public ApiLogin(Context context , String username , String password)
    {
        this.username = username;
        this.password = password;
//        scontext = context;

        scontext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        d = new ProgressDialog(this.scontext);
        d.setMessage("Please wait...");
        d.setIndeterminate(true);
        d.setCancelable(false);
        d.show();

    }

    @Override
        protected String doInBackground(Void... strings) {

        try {
            res = testFetchOK();

            Log.v("RES" , res);
        } catch (Exception e) {
            Log.e("EXCEPTION" , "class not found");
            e.printStackTrace();
        }
        return res;
    }

    private static OkHttpClient createAuthenticatedClient(final String username,
                                                          final String password) {
        // build client with authentication information.
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
        //System.out.println(response.body().string());
        else
        {
            JSONObject jsonObject = new JSONObject(response.body().string());

            token = jsonObject.getString("token");
            Log.v("TOKEN GENERATED" , token);
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        d.hide();
    }
}