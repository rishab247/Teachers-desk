package com.cu.project.ui.Profiile;




import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;


import com.cu.project.R;
import com.cu.project.ui.Upload.UploadActivity;

import java.util.ArrayList;
import java.util.List;


public class View_fragment extends Fragment {
    Button upload_btn;
    Toolbar toolbar;

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
        lachievements.add(new Achievements("Paper1","Auth 1","date1"));
        lachievements.add(new Achievements("Paper2","Auth 2","date2"));
        lachievements.add(new Achievements("Paper3","Auth 3","date3"));
        lachievements.add(new Achievements("Paper4","Auth 4","date4"));
        lachievements.add(new Achievements("Paper5","Auth 5","date5"));
        lachievements.add(new Achievements("Paper6","Auth 6","date6"));
        lachievements.add(new Achievements("Paper7","Auth 7","date7"));
        lachievements.add(new Achievements("Paper8","Auth 8","date8"));
        lachievements.add(new Achievements("Paper9","Auth 9","date9"));
        lachievements.add(new Achievements("Paper10","Auth 10","date10"));
        lachievements.add(new Achievements("Paper11","Auth 11","date11"));
        lachievements.add(new Achievements("Paper12","Auth 12","date12"));
        lachievements.add(new Achievements("Paper13","Auth 13","date13"));
        lachievements.add(new Achievements("Paper14","Auth 14","date14"));
        lachievements.add(new Achievements("Paper15","Auth 15","date15"));
        lachievements.add(new Achievements("Paper16","Auth 16","date16"));
        lachievements.add(new Achievements("Paper17","Auth 17","date17"));
        lachievements.add(new Achievements("Paper18","Auth 18","date18"));
        lachievements.add(new Achievements("Paper19","Auth 19","date19"));
        lachievements.add(new Achievements("Paper20","Auth 20","date20"));

    }
}