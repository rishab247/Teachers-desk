package com.cu.project.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cu.project.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Authorclass  extends AppCompatActivity {
    MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.author);


        Toolbar toolbar = findViewById(R.id.toolbar__);
        setSupportActionBar(toolbar);


        materialSearchView = findViewById(R.id.searchview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_update , menu);
        MenuItem item = menu.findItem(R.id.searchicon);


        materialSearchView.setMenuItem(item);

        return true;



    }
}
