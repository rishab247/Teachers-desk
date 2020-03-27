package com.cu.project;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cu.project.APIHelper.APIVerifyIndividual;
import com.cu.project.APIHelper.ApiHodData;
import com.cu.project.APIHelper.ApigetIndividual;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.AsynHodResponse;
import com.cu.project.ui.Profiile.SubjectData;

import java.util.ArrayList;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HOD_fragment extends Fragment implements AsynHodResponse , AdapterView.OnItemClickListener {

    ListView listView;

    SwipeRefreshLayout swipeRefreshLayout;
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    ApiHodData apiHodData;

    EditText inputSearch;

    HODlistAdapter hoDlistAdapter;


    SearchView mySearchView = null ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hod,container,false);

        listView = v.findViewById(R.id.info_list);

        mySearchView = v.findViewById(R.id.inputSearch);

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

        listView.setOnItemClickListener(this);


            mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // Here implement search logic
                        hoDlistAdapter.getFilter().filter(s);
                        hoDlistAdapter.notifyDataSetChanged();
                    return false;
                }
            });



        return v;
    }

    @Override
    public void asyncresponsefinish(ArrayList<SubjectDataHod> output) {


        ArrayList<SubjectDataHod> subjectDataHods = output;

        if(subjectDataHods.size() == 0){
            mySearchView.setVisibility(View.GONE);
        }
        else
            mySearchView.setVisibility(View.VISIBLE);

        hoDlistAdapter = new HODlistAdapter(getContext() , subjectDataHods);


        listView.setAdapter(hoDlistAdapter);

        listView.setOnItemClickListener(this);



        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB  title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                hoDlistAdapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.verify_id:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = hoDlistAdapter
                                .getSelectedIds();
                        Log.e("SPARSE ARRAY", selected.toString());



                        String[] eid = new String[selected.size()];
                        int pos = 0;


                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                SubjectDataHod selecteditem = hoDlistAdapter
                                        .getItem(selected.keyAt(i));

                                // add the selected item in the string array
                                assert selecteditem != null;
                                eid[pos] = selecteditem.getEid();

                                // Remove selected items following the ids
                           //     hoDlistAdapter.remove(selecteditem);
                                pos ++;

                            }

                        }
                        // Close CAB

                        APIVerifyIndividual apiVerifyIndividual = new APIVerifyIndividual(getContext(), eid,2);
                        apiVerifyIndividual.execute();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.hod_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                hoDlistAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });







        swipeRefreshLayout.setRefreshing(false);

        apiHodData = new ApiHodData("all", getActivity().getApplicationContext());
        apiHodData.asynHodResponse = this;

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SubjectDataHod arrayList = hoDlistAdapter.getItem(position);
        String eid = arrayList.getEid();
        boolean verify = arrayList.getVerify();

        ApigetIndividual apigetIndividual = new ApigetIndividual(getContext() , eid , verify);

        apigetIndividual.execute();
    }


}
