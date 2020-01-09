package com.cu.project.ui.Profiile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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
    ConstraintLayout constraintLayout;
    ImageView setting, power  , profile_imgupdate ;

    TextView usertext;

    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name_");

        usertext = findViewById(R.id.textView4);

        usertext.setText(name);


        //toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile");
        //setSupportActionBar(toolbar);



        tabLayout = findViewById(R.id.tab_layout);
        tabprofile = findViewById(R.id.profile_tab);
        tabview = findViewById(R.id.view_tab);
        viewPager = findViewById(R.id.view_pager);


        pagerAdapter = new PageAdapter(getSupportFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        constraintLayout = findViewById(R.id.constraintLayout2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0) {

                   // slideDown(constraintLayout);

                    constraintLayout.setVisibility(View.VISIBLE);

                }
                else if(tab.getPosition() == 1){

                  //slideUp(constraintLayout);
                  constraintLayout.setVisibility(View.GONE);
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









        setting = findViewById(R.id.settings);
        power = findViewById(R.id.signout);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , SettingActivity.class);
                startActivity(intent);
            }
        });

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , loginActivity.class);
                startActivity(intent);
            }
        });


        profile_imgupdate = findViewById(R.id.profile_img);

        profile_imgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(ProfileActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.profile_update, popup.getMenu());
                popup.show();
            }
        });




    }

    public void slideUp(View view){
        view.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


}

