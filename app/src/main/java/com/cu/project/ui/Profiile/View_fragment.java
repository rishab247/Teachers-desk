package com.cu.project.ui.Profiile;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.ui.login.loginActivity;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;


import com.cu.project.R;
import com.cu.project.Util.ApiLogin;
import com.cu.project.ui.Upload.UploadActivity;

import java.util.ArrayList;
import java.util.List;


public class View_fragment extends Fragment {
    Button upload_btn;
    Toolbar toolbar;

    String token = loginActivity.gettoken();


    loginActivity activity;
    View v;



    private RecyclerView recyclerView;
    List<Achievements> lachievements;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        v = inflater.inflate(R.layout.fragment_view_fragment, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext() , lachievements);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ApigetPaper apigetPaper = new ApigetPaper();



                        apigetPaper.execute(token);


                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        upload_btn = v.findViewById(R.id.uploadbtn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , UploadActivity.class);
                startActivity(intent);
            }
        });




        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        lachievements = new ArrayList<>();
        lachievements.add(new Achievements("Paper1","date1"));
        lachievements.add(new Achievements("Paper2","date2"));
        lachievements.add(new Achievements("Paper3","date3"));
        lachievements.add(new Achievements("Paper4","date4"));
        lachievements.add(new Achievements("Paper5","date5"));
        lachievements.add(new Achievements("Paper6","date6"));
        lachievements.add(new Achievements("Paper7","date7"));
        lachievements.add(new Achievements("Paper8","date8"));
        lachievements.add(new Achievements("Paper9","date9"));
        lachievements.add(new Achievements("Paper10","date10"));
        lachievements.add(new Achievements("Paper11","date11"));
        lachievements.add(new Achievements("Paper12","date12"));
        lachievements.add(new Achievements("Paper13","date13"));
        lachievements.add(new Achievements("Paper14","date14"));
        lachievements.add(new Achievements("Paper15","date15"));
        lachievements.add(new Achievements("Paper16","date16"));
        lachievements.add(new Achievements("Paper17","date17"));
        lachievements.add(new Achievements("Paper18","date18"));



    }



}