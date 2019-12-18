package com.cu.project;

import android.app.Application;

import com.cu.project.data.DataManager;
import com.cu.project.data.SharedPrefHelper;


public class MvpApp extends Application {

    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefHelper sharedPrefsHelper = new SharedPrefHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);

    }

    public DataManager getDataManager() {
        return dataManager;
    }

}

