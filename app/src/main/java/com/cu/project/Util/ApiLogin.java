package com.cu.project.Util;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.TestOnly;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ApiLogin  extends AsyncTask<String, String ,Void> {

    @Override
    protected Void doInBackground(String... params) {

        Log.v("userandpass", params[0]+params[1]);

        String url = "https://apitims1.azurewebsites.net/login";
        try {
            fetch1(url,params[0],params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

//    private static OkHttpClient createAuthenticatedClient(final String username,
//                                                          final String password) {
//        // build client with authentication information.
//        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
//            public Request authenticate(Route route, Response response) throws IOException {
//                String credential = Credentials.basic(username, password);
//                return response.request().newBuilder().header("Authorization", credential).build();
//            }
//        }).build();
//        return httpClient;
//    }

//    private static Response doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
//        Request request = new Request.Builder().url(anyURL).build();
//        Response response = httpClient.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Unexpected code " + response);
//        }
//        System.out.println(response.body().string());
//        return response;
//    }

//    public static Response fetch(String url, String username, String password) throws Exception {
//
//        OkHttpClient httpClient = createAuthenticatedClient(username, password);
//        // execute request
//        return doRequest(httpClient, url);
//
//    }
    public static void fetch1(String url, final String username, final String password) throws Exception {

        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();


        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        System.out.println(response.body().string());
       // return response;

    }
//
//    public void testFetchOK(String username , String password) throws Exception {
//        String url = "https://apitims1.azurewebsites.net/login";
//            ApiLogin.fetch1(url, username, password);
//    }
}
