package com.cu.project.ui.Profiile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 import android.widget.Toolbar;

import com.cu.project.APIHelper.ApiVerify;
import com.cu.project.APIHelper.Apiget;
import com.cu.project.APIHelper.AsyncLoginResponse;
import com.cu.project.APIHelper.AsyncResponse;
import com.cu.project.R;
import com.cu.project.ui.login.loginActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class profile_fragement extends Fragment{

    SwipeRefreshLayout swipeRefreshLayout;

    public TextView name;
    static TextView ecode;
    static TextView email;
    static TextView pno;
    static TextView depart;
    static TextView quali;
    static TextView uni;
    static TextView dob;
    static TextView doj;

    ImageView verified;


    String text1 , text2 , text3, text4;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);

        View v;

        v =  inflater.inflate(R.layout.fragment_profile_fragement,container,false);

        Bundle extras = getActivity().getIntent().getExtras();
        String userName = extras.getString("name_").trim();
        String e_code = extras.getString("e_code").trim();
        String email_ = extras.getString("email_").trim();
        String p_no = extras.getString("p_no").trim();
        String depart_ = extras.getString("depart_").trim();
        String quali_ = extras.getString("quali_").trim();
        String uni_ = extras.getString("uni_").trim();
        String dob_ = extras.getString("dob_").trim();
        String doj_ = extras.getString("doj_").trim();

        text1 = e_code;
        text2 = userName;
        text3 = email_;
        text4 = p_no;



        name = v.findViewById(R.id.user_nameid);
        ecode = v.findViewById(R.id.ecode_id);
        email = v.findViewById(R.id.email_id);
        pno = v.findViewById(R.id.phonenumber_id);
        depart = v.findViewById(R.id.department_id);
        quali = v.findViewById(R.id.qualification_id);
        uni = v.findViewById(R.id.university_id);
        dob = v.findViewById(R.id.dob_id);
        doj = v.findViewById(R.id.doj_id);

        name.setText(userName.trim());
        ecode.setText(e_code);
        email.setText(email_);
        pno.setText(p_no);
        depart.setText(depart_);
        quali.setText(quali_);
        uni.setText(uni_);
        dob.setText(dob_);
        doj.setText(doj_);


        return v;
    }

}

