package com.cu.project.ui.Profiile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.cu.project.ProfileEdit;
import com.cu.project.R;
import com.cu.project.ui.Authorclass;
import com.cu.project.ui.Setting.SettingActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cu.project.ui.login.loginActivity;
public class ProfileActivity extends AppCompatActivity implements ProfileMvpView {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionsMenu menu;
    PageAdapter pagerAdapter;
    TabItem tabprofile;
    TabItem tabview;
    ConstraintLayout constraintLayout;
    Spinner setting;

    ImageView signout;

    TextView usertext , emailtext , pnotext , eidtext;

    Bundle bundle = new Bundle();

    FloatingActionButton btn1 , btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();


                /*
                        intent_name.setClass(sContext,ProfileActivity.class);
        intent_name.putExtra("e_code" , strings[0]);
        intent_name.putExtra("name_" , strings[1]);
        intent_name.putExtra("email_" , strings[2]);
        intent_name.putExtra("p_no" , strings[3]);
        intent_name.putExtra("depart_" , strings[4]);
        intent_name.putExtra("doj_" , dojdate);
        intent_name.putExtra("quali_" , strings[6]);
        intent_name.putExtra("uni_" , strings[7]);
        intent_name.putExtra("dob_" , dobdate);
                 */

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

//        usertext = findViewById(R.id.textView4);
//        emailtext = findViewById(R.id.textView8);
//        pnotext = findViewById(R.id.textView10);
        eidtext = findViewById(R.id.textView11);


//        usertext.setText(name);
//        emailtext.setText(email);
//        pnotext.setText(pno);
        eidtext.setText(eid);


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
        menu = findViewById(R.id.f_btn);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0) {

                   // slideDown(constraintLayout);

                    constraintLayout.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);

                }
                else if(tab.getPosition() == 1){

                  //slideUp(constraintLayout);
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

                startActivity(intent);
            }
        });



        signout = findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , loginActivity.class);
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

//                switch (item.getItemId())
//                {
//                    case R.id.aboutus:
//                        Toast.makeText(getApplicationContext()  , "TBA" , Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.terms_id:
//                        Toast.makeText(getApplicationContext()  , "TBA" , Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }

                return false;
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

