package com.cu.project.APIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ApiLogin  extends AsyncTask<Void, Void ,String> {
    public AsyncLoginResponse asyncLoginResponse = null;




    private String username , password;
    private String res = "";
    private ProgressDialog d;
    private WeakReference<Context> contextRef;


    public ApiLogin(Context context , String username , String password)
    {
        this.username = username;
        this.password = password;
        contextRef =new WeakReference<> (context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context scontext = contextRef.get();
        d = new ProgressDialog(scontext);
        d.setMessage("Please wait...");
        d.setIndeterminate(true);
        d.setCancelable(false);
        d.show();

    }

    @Override
        protected String doInBackground(Void... strings) {

        try {
            res = testFetchOK();
        } catch (Exception e) {
            Log.e("Login Error" , Objects.requireNonNull(e.getMessage()));
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
        OkHttpClient.Builder builder = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, @NotNull Response response) {
                String credential = Credentials.basic(username, password);
                if (responseCount(response) >= 3) {
                    return null;
                }
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES);

        return builder.build();
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
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());

            Log.e("Api Login data" , jsonObject.toString());

            token = jsonObject.getString("token");
        }

        return token;
    }


    private static String fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);

        return doRequest(httpClient , url);

    }

    private String testFetchOK() throws Exception {
        String url = "https://apitims1.azurewebsites.net/login";

        Log.e("Printhelper",username + password);


        return ApiLogin.fetch(url, username, password);
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}