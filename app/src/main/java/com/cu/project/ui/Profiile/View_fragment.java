package com.cu.project.ui.Profiile;




import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;


import com.cu.project.R;
import com.cu.project.ui.Upload.UploadActivity;


public class View_fragment extends Fragment {
    Button upload_btn;
    Toolbar toolbar;


    @Override
    public View onCreateView(final LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_view_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        

        upload_btn = getView().findViewById(R.id.uploadbtn);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , UploadActivity.class);
                startActivity(intent);
            }
        });
    }
/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main , menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

 */



}
