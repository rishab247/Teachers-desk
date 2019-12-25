package com.cu.project.ui.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.widget.Toolbar;
import com.cu.project.ui.login.loginActivity;
import com.cu.project.R;
import com.cu.project.ui.Profiile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btn;
    TextView nametext;
    ListView listView;
    ListView listView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this , ProfileActivity.class);
                startActivity(intent);
            }
        });


        this.arrayAdapterListView();


    }

    private void arrayAdapterListView()
    {
        List<String> dataList = new ArrayList<>();
        dataList.add("Name");
        dataList.add("E-mail");
        dataList.add("Password");
        dataList.add("Phone No.");
        dataList.add("Qualification and University");

        ListView listView = findViewById(R.id.settinglist_id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                Toast.makeText(getApplicationContext(), "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        List<String> dataList1 = new ArrayList<>();
        dataList1.add("Terms of Services");
        dataList1.add("Data Policies");
        dataList1.add("Help and Support");
        dataList1.add("About us");

        ListView listView2 = findViewById(R.id.legal_list);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , dataList1);
        listView2.setAdapter(arrayAdapter1);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.setting_menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this , loginActivity.class);
        startActivity(intent);
        finish();

        return true;
    }
}