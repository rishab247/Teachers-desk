package com.cu.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.cu.project.APIHelper.ApigetIndividual;

import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HODlistAdapter  extends ArrayAdapter<SubjectDataHod>
{
        ArrayList<SubjectDataHod> customers, tempCustomer, suggestions;
    private SparseBooleanArray mSelectedItemsIds;

        public HODlistAdapter(Context context, ArrayList<SubjectDataHod> objects) {
        super(context, R.layout.hodlist_row, R.id.name_emp, objects);
        this.customers = objects;
        this.tempCustomer = new ArrayList<SubjectDataHod>(objects);
        this.suggestions = new ArrayList<SubjectDataHod>(objects);

            mSelectedItemsIds = new SparseBooleanArray();
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
        }


@SuppressLint("NewApi")
private View initView(int position, View convertView, ViewGroup parent) {
    SubjectDataHod customer = getItem(position);
        if (convertView == null) {
        if (parent == null)
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.hodlist_row, null);
        else
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.hodlist_row, parent, false);
        }
        TextView txtname = (TextView) convertView.findViewById(R.id.name_emp);
        TextView txteid = convertView.findViewById(R.id.eid_emp);
        ImageView img = convertView.findViewById(R.id.verified_Img);

        if (txtname != null) {
            assert customer != null;
            txtname.setText(customer.getName());
        }

        if(txteid != null){
            txteid.setText(customer.getEid());
        }


    Boolean verify = customer.getVerify();

    if(verify){
        img.setImageDrawable(convertView.getResources().getDrawable(R.drawable.verified));
    }
    else{
        img.setImageDrawable(convertView.getResources().getDrawable(R.drawable.cross));
    }


//        // Now assign alternate color for rows
//        if (position % 2 == 0)
//        convertView.setBackgroundColor(getContext().getColor(R.color.odd));
//        else
//        convertView.setBackgroundColor(getContext().getColor(R.color.even));

        return convertView;
        }

@Override
public Filter getFilter() {
        return myFilter;
        }
        Filter myFilter =new Filter() {
@Override
public CharSequence convertResultToString(Object resultValue) {
        HodData customer =(HodData) resultValue ;
        return customer.getEid();
        }

@Override
protected FilterResults performFiltering(CharSequence constraint) {
        if (constraint != null) {
        suggestions.clear();
        for (SubjectDataHod cust : tempCustomer) {
        if (cust.getEid().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
        suggestions.add(cust);
        }
        }

        FilterResults filterResults = new FilterResults();
        filterResults.values = suggestions;
        filterResults.count = suggestions.size();
        return filterResults;
        } else {
        return new FilterResults();
        }
        }

@Override
protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayList<SubjectDataHod> c =  (ArrayList<SubjectDataHod> )results.values ;
        if (results != null && results.count > 0) {
        clear();
        for (SubjectDataHod cust : c) {
        add(cust);
        notifyDataSetChanged();
        }
        }
        else{
        clear();
        notifyDataSetChanged();
        }
        }
        };

    @Override
    public void remove(SubjectDataHod object) {
        customers.remove(object);
        notifyDataSetChanged();
    }

    public List<SubjectDataHod> getWorldPopulation() {
        return customers;
    }

    public void toggleSelection(int position) {
        Log.e(TAG , "toggleSelection: " +mSelectedItemsIds.get(position) );
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}