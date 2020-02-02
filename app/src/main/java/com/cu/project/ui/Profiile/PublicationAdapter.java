package com.cu.project.ui.Profiile;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.Apigetdetails;
import com.cu.project.R;
import com.cu.project.ui.detailclass;

import java.util.ArrayList;
import java.util.List;
class PublicationAdapter implements ListAdapter {
    ArrayList<SubjectData> arrayList;
    Context context;


    public PublicationAdapter(Context context, ArrayList<SubjectData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
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
        SubjectData subjectData=arrayList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = arrayList.get(position).id;
                    Log.e("ID OD", String.valueOf(id));


                    Apigetdetails apigetdetails = new Apigetdetails(context);
                    apigetdetails.execute(String.valueOf(id) , "Publication");
                }
            });
            TextView tittle=convertView.findViewById(R.id.maintextview_id);
            TextView date = convertView.findViewById(R.id.datetextview_id);
            tittle.setText(subjectData.title);
            date.setText(subjectData.date);
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