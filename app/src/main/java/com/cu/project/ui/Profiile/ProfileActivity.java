package com.cu.project.ui.Profiile;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.cu.project.R;
import com.cu.project.ui.Setting.SettingActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cu.project.ui.login.loginActivity;
public class ProfileActivity extends AppCompatActivity implements ProfileMvpView {

    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pagerAdapter;
    TabItem tabprofile;
    TabItem tabview;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);



        tabLayout = findViewById(R.id.tab_layout);
        tabprofile = findViewById(R.id.profile_tab);
        tabview = findViewById(R.id.view_tab);
        viewPager = findViewById(R.id.view_pager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0){
                    toolbar.setTitle("Profile");
                    tabLayout.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorAccent));
                }
                else if(tab.getPosition() == 1){
                    toolbar.setTitle("Achievements");
                    tabLayout.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorPrimary));
                }
                else{
                    tabLayout.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorAccent));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.setting_id:
                Intent intent = new Intent(ProfileActivity.this , SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.Sign_Outid:
                Intent myintent = new Intent(this , loginActivity.class);
                startActivity(myintent);
                break;
        }
        return true;
    }
}

