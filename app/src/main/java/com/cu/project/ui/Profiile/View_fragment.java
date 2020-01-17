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
import android.util.Log;
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
import java.util.concurrent.ExecutionException;


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


        swipeRefreshLayout = v.findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ApigetPaper apigetPaper = new ApigetPaper();

                        try {
                            Log.e("TOKEN  GENE" , token);
                            lachievements = apigetPaper.execute(token).get();

                            recyclerView = v.findViewById(R.id.recycler_view);
                            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext() , lachievements);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(recyclerViewAdapter);


                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


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

    }



}