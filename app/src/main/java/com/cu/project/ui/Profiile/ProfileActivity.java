package com.cu.project.ui.Profiile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cu.project.APIHelper.ApiVerify;
import com.cu.project.APIHelper.AsyncVerifyResponse;
import com.cu.project.HOD_fragment;
import com.cu.project.ProfileEdit;
import com.cu.project.R;
import com.cu.project.ui.Authorclass;
import com.cu.project.ui.DownloadReport;
import com.cu.project.ui.Setting.SettingActivity;
import com.cu.project.ui.Splash.logoutsplash;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cu.project.ui.login.loginActivity;
import com.itextpdf.text.Image;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements ProfileMvpView, AsyncVerifyResponse {
    private static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    int LAUNCH_SECOND_ACTIVITY = 1;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionsMenu menu;
    PageAdapter pagerAdapter;
    TabItem tabprofile;
    TabItem tabview;
    TabItem tabinfo;
    ConstraintLayout constraintLayout;

    ImageView verified;

    ImageView signout;

    TextView quali , depart , pnotext, uni , eidtext;


    CircleImageView pro_img;

    FloatingActionButton btn1 , btn2;

    String type = "false";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ApiVerify apiVerify = new ApiVerify(this);
        apiVerify.execute();
        Bundle bundle = getIntent().getExtras();
        sharedpreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();


        verified = findViewById(R.id.imageView3);

        pro_img = findViewById(R.id.profile_img);



        String name = bundle.getString("name_");
        String email = bundle.getString("email_");
        String pno = bundle.getString("p_no");
        String depart = bundle.getString("depart_");
        String doj = bundle.getString("doj_");
        String quali = bundle.getString("quali_");
        String uni = bundle.getString("uni_");
        String dob = bundle.getString("dob_");
        String eid = bundle.getString("e_code");
        String imgstr = bundle.getString("imagestr");
        String verifystr = bundle.getString("verify");

        editor.putString("name_",bundle.getString("name_")).apply();
        editor.putString("email_", bundle.getString("email_") ).apply();
        editor.putString("p_no",bundle.getString("p_no") ).apply();



        eidtext = findViewById(R.id.textView11);
        eidtext.setText(eid);




        if(imgstr.equals("noimage"))
        {

        }
        else
        {
            StringBuilder stringBuilder = new StringBuilder();

            for(int i=0;i < imgstr.length() ; i ++)
            {
                if(imgstr.charAt(i) == '\\' && imgstr.charAt(i + 1) == 'n')
                {
                    stringBuilder.append("\n");
                    i = i + 1;
                }
                else
                    stringBuilder.append(imgstr.charAt(i));

            }


            imgstr = String.valueOf(stringBuilder);
            pro_img.setImageBitmap(StringToBitMap(String.valueOf(stringBuilder)));
        }
        final String[] datarray = new String[]{name , email , pno , depart , doj , quali , uni , dob , eid , imgstr};


        tabLayout = findViewById(R.id.tab_layout);
//        tabprofile = findViewById(R.id.profile_tab);
//        tabview = findViewById(R.id.view_tab);
//        tabinfo = findViewById(R.id.info_tab);
        viewPager = findViewById(R.id.view_pager);

        assert verifystr != null;
        if(verifystr.equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(getResources().getDrawable(R.drawable.profile_img)));
            tabLayout.addTab(tabLayout.newTab().setText("Accomplishment").setIcon(getResources().getDrawable(R.drawable.baseline_emoji_events_black_24dp)));
            tabLayout.addTab(tabLayout.newTab().setText("Information"));
        }
        else{
            tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(getResources().getDrawable(R.drawable.profile_img)));
            tabLayout.addTab(tabLayout.newTab().setText("Accomplishment").setIcon(getResources().getDrawable(R.drawable.baseline_emoji_events_black_24dp)));
        }
        pagerAdapter = new PageAdapter(getSupportFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        constraintLayout = findViewById(R.id.constraintLayout2);
        menu = findViewById(R.id.f_btn);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                 Log.w("TAG" , "onTabSelected: " + tab.getPosition());
                if(tab.getPosition() == 0) {

                    constraintLayout.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);
                 }
                else if(tab.getPosition() == 1){
                     constraintLayout.setVisibility(View.GONE);
                  menu.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 2){

                    constraintLayout.setVisibility(View.GONE);
                    menu.setVisibility(View.GONE);
                }

                else{
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



        // for downloading the report

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this , DownloadReport.class);
                startActivity(intent);
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

                if(res[4].equals("noimage"))
                {

                }
                else
                {
                  //  Log.e("DFSDFDSFSDFFSfsf "  ,  res[4]);
                    pro_img.setImageBitmap(StringToBitMap(res[4]));
                }
            }
        }
    }

    @Override
    public void processVerifyFinish(String[] output) {
         editor.putString("Verify",output[0]);
//         editor.putString("HOD" , output[1]);
        editor.commit();

        Log.e(  "processVerifyFinish: ",sharedpreferences.getString("Verify","") );
    //    Log.e(  "processVerifyFinish: ",sharedpreferences.getString("HOD","") );
        Log.e(  "processVerifyFinish: ",sharedpreferences.getString("email_","") );
        Log.e(  "processVerifyFinish: ",sharedpreferences.getString("name_","") );
        Log.e(  "processVerifyFinish: ",sharedpreferences.getString("p_no","") );

        if(sharedpreferences.getString("Verify" , "").equals("true")){
            verified.setVisibility(View.VISIBLE);
        }

        if(sharedpreferences.getString("HOD" , "").equals("true")){
            type = "true";
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you want to Exit?")
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);                    }
                })

                 .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}

