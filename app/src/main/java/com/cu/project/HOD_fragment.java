package com.cu.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cu.project.APIHelper.ApiHodData;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.AsynHodResponse;

import java.util.ArrayList;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HOD_fragment extends Fragment implements AsynHodResponse {

    ListView listView;

    SwipeRefreshLayout swipeRefreshLayout;
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    ApiHodData apiHodData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hod,container,false);

        listView = v.findViewById(R.id.info_list);


        swipeRefreshLayout = v.findViewById(R.id.swipe__);

        apiHodData = new ApiHodData("all" , Objects.requireNonNull(getActivity()).getApplicationContext());
        apiHodData.asynHodResponse = this;
        listView.setAdapter(null);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiHodData.execute();
            }
        });
        return v;
    }

    @Override
    public void asyncresponsefinish(ArrayList<SubjectDataHod> output) {


        ArrayList<SubjectDataHod> subjectDataHods = output;

        HODlistAdapter hoDlistAdapter = new HODlistAdapter(getActivity().getApplicationContext() , subjectDataHods);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);


        listView.setAdapter(hoDlistAdapter);



        swipeRefreshLayout.setRefreshing(false);

        apiHodData = new ApiHodData("all", getActivity().getApplicationContext());
        apiHodData.asynHodResponse = this;
    }
}
