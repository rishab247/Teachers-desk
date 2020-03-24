package com.cu.project;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.cu.project.APIHelper.ApigetIndividual;

import java.util.ArrayList;

public class HODlistAdapter implements ListAdapter {

    Context context;
    private ArrayList<SubjectDataHod> arrayList;

    public HODlistAdapter(Context context, ArrayList<SubjectDataHod> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubjectDataHod hodData = arrayList.get(position);

        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.hodlist_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String eid = arrayList.get(position).getEid();
                    boolean verify = arrayList.get(position).getVerify();

                    ApigetIndividual apigetIndividual = new ApigetIndividual(context , eid , verify);

                        apigetIndividual.execute();

                }
            });


            TextView tittle=convertView.findViewById(R.id.name_emp);
            TextView date = convertView.findViewById(R.id.eid_emp);
            tittle.setText(hodData.getName());
            date.setText(hodData.getEid());

            ImageView verified_img = convertView.findViewById(R.id.verified_Img);

            Boolean verify = hodData.getVerify();

            if(verify){
                verified_img.setImageDrawable(convertView.getResources().getDrawable(R.drawable.verified));
            }
            else{
                verified_img.setImageDrawable(convertView.getResources().getDrawable(R.drawable.cross));
            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
