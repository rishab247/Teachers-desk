package com.cu.project.ui.Profiile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.cu.project.APIHelper.ApiVerify;
import com.cu.project.ProfileEdit;
import com.cu.project.R;
import com.cu.project.ui.Authorclass;
import com.cu.project.ui.Setting.SettingActivity;
import com.cu.project.ui.Splash.logoutsplash;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cu.project.ui.login.loginActivity;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity implements ProfileMvpView {
    private static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    int LAUNCH_SECOND_ACTIVITY = 1;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionsMenu menu;
    PageAdapter pagerAdapter;
    TabItem tabprofile;
    TabItem tabview;
    ConstraintLayout constraintLayout;

    ImageView verified;

    ImageView signout;

    TextView quali , depart , pnotext, uni , eidtext;

    Bundle bundle = new Bundle();


    FloatingActionButton btn1 , btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        sharedpreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();


        verified = findViewById(R.id.imageView3);


        String name = bundle.getString("name_");

        String email = bundle.getString("email_");

        String pno = bundle.getString("p_no");

        String depart = bundle.getString("depart_");

        String doj = bundle.getString("doj_");

        String quali = bundle.getString("quali_");

        String uni = bundle.getString("uni_");

        String dob = bundle.getString("dob_");

        String eid = bundle.getString("e_code");

        final String[] datarray = new String[]{name , email , pno , depart , doj , quali , uni , dob , eid};


        eidtext = findViewById(R.id.textView11);
        eidtext.setText(eid);


        tabLayout = findViewById(R.id.tab_layout);
        tabprofile = findViewById(R.id.profile_tab);
        tabview = findViewById(R.id.view_tab);
        viewPager = findViewById(R.id.view_pager);


        pagerAdapter = new PageAdapter(getSupportFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        constraintLayout = findViewById(R.id.constraintLayout2);
        menu = findViewById(R.id.f_btn);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0) {
                    constraintLayout.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);

                }
                else if(tab.getPosition() == 1){
                    constraintLayout.setVisibility(View.GONE);
                  menu.setVisibility(View.GONE);
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

        btn1 = findViewById(R.id.edit_profile);
        btn2 = findViewById(R.id.down_id);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , ProfileEdit.class);
                intent.putExtra("data" , datarray);
                startActivityForResult(intent , 1);
            }
        });



        signout = findViewById(R.id.signout);
                signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.clear();

                Intent intent = new Intent(ProfileActivity.this , logoutsplash.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.setting_menu, popup.getMenu());
        popup.show();


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(getApplicationContext()  , "TBA" , Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == LAUNCH_SECOND_ACTIVITY)
        {
            if(resultCode == RESULT_OK)
            {
                String[] res = data.getStringArrayExtra("result");

                pnotext = findViewById(R.id.phonenumber_id);
                depart = findViewById(R.id.department_id);
                quali = findViewById(R.id.qualification_id);
                uni = findViewById(R.id.university_id);
                pnotext.setText(res[0]);
                depart.setText(res[1]);
                quali.setText(res[2]);
                uni.setText(res[3]);
            }
        }
    }
}

