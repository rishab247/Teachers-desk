package com.cu.project;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cu.project.APIHelper.APIVerifyIndividual;
import com.cu.project.APIHelper.ApiDownloadHod;
import com.cu.project.APIHelper.ApiHodData;
import com.cu.project.APIHelper.ApigetIndividual;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.AsynHodResponse;
import com.cu.project.ui.AddWork.AddPublication;
import com.cu.project.ui.DownloadReport;
import com.cu.project.ui.Profiile.SubjectData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.view.ViewGroup.getChildMeasureSpec;
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


    // AlertDialog for downloading the information or data of the employees
    View popupInputDialogView;
    EditText startdate, enddate;
    Spinner typespinner;
    Button download, Dfull;


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
                    if(hoDlistAdapter != null){
                        hoDlistAdapter.getFilter().filter(s);
                        hoDlistAdapter.notifyDataSetChanged();
                    }

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
                SparseBooleanArray selected = hoDlistAdapter
                        .getSelectedIds();

                final String[] eid = new String[selected.size()];
                int pos = 0;


                for (int i = (selected.size() - 1); i >= 0; i--) {
                    if (selected.valueAt(i)) {
                        SubjectDataHod selecteditem = hoDlistAdapter
                                .getItem(selected.keyAt(i));

                        // add the selected item in the string array
                        assert selecteditem != null;
                        eid[pos] = selecteditem.getEid();

                        pos ++;

                    }

                }
                switch (item.getItemId()) {
                    case R.id.verify_id:
                        // Calls getSelectedIds method from ListViewAdapter Class


                        // Close CAB

                        APIVerifyIndividual apiVerifyIndividual = new APIVerifyIndividual(getContext(), eid,2);
                        apiVerifyIndividual.execute();
                        mode.finish();
                        return true;

                    case R.id.download_id:

                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        builder.setCancelable(true);

                        initPopupViewControls();

                        builder.setView(popupInputDialogView);

                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        startdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int mYear, mMonth, mDay;
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        startdate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                                    }
                                }, mYear, mMonth, mDay);

                                datePickerDialog.show();
                            }
                        });

                        enddate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int mYear, mMonth, mDay;
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        enddate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                                    }
                                }, mYear, mMonth, mDay);

                                datePickerDialog.show();
                            }
                        });


                        download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String fromdate1 = startdate.getText().toString().trim();
                                String todadte1 = enddate.getText().toString().trim();
                                final String type = typespinner.getSelectedItem().toString().trim();


                                int flag = 0;

                                if (fromdate1.equals("")) {
                                    startdate.setError("Field cannot be empty!");
                                    flag = 1;
                                }
                                if (todadte1.equals("")) {
                                    enddate.setError("Field cannot be empty!");
                                    flag = 1;
                                }

                                if (flag == 0) {
                                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                                    Date fromdate = null;

                                    try {
                                        fromdate = date1.parse(startdate.getText().toString().trim());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    long time1 = fromdate.getTime();

                                    final String datestart = String.valueOf(time1);


                                    final SimpleDateFormat date2 = new SimpleDateFormat("yyyy/MM/dd");

                                    Date todate = null;

                                    try {
                                        todate = date2.parse(enddate.getText().toString().trim());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    long time2 = todate.getTime();

                                    final String dateend = String.valueOf(time2);

                                    ApiDownloadHod apiDownloadHod = new ApiDownloadHod(getContext(), eid, datestart,dateend, typespinner.getSelectedItem().toString().trim());
                                    apiDownloadHod.execute();

                                    alertDialog.cancel();


                                }
                            }
                        });

                        Dfull.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                            }
                        });

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


    private void initPopupViewControls(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        popupInputDialogView = layoutInflater.inflate(R.layout.download_hod_data, null);


        startdate = popupInputDialogView.findViewById(R.id.fromdate_);
        enddate = popupInputDialogView.findViewById(R.id.todate_);
        typespinner = popupInputDialogView.findViewById(R.id.spinner__);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.download_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(adapter);

        download = popupInputDialogView.findViewById(R.id.button_);
        Dfull = popupInputDialogView.findViewById(R.id.downloadfull_);

    }
}

