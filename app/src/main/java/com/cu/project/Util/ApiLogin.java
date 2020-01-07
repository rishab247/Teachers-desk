package com.cu.project.Util;

import android.os.AsyncTask;
import android.util.Log;

import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.TestOnly;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ApiLogin  extends AsyncTask<Void, Void ,Void> {

    String username , password;


    public ApiLogin(String username , String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... strings) {

        try {
            testFetchOK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private static Response doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        System.out.println(response.body().string());
        return response;
    }


    public static Response fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);

        return doRequest(httpClient, url);

    }

    public void testFetchOK() throws Exception {
        String url = "https://apitims1.azurewebsites.net/login";

        Log.e("Printhelper",username + password);


        ApiLogin.fetch(url, username, password);
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}