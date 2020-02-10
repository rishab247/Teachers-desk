package com.cu.project.ui.AddWork;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.ApiPostProject;
import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Authorclass;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddProject extends AppCompatActivity{

    ImageView imageView;
    ListView listView;

    EditText title , date, url , des;

    Button btn;

    private View popupInputDialogView, popupInputDialogViewdelete;

    EditText authname, authemail, authpno;
    Button addbtn, canclebtn;

    EditText authupname, authupemail, authuppno;
    Button delete, update;

    List<String> names = new ArrayList<>();
    List<String> emails = new ArrayList<>();
    List<String> pnos = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);


        listView = findViewById(R.id.authorslist_id);

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);
        listView.setAdapter(adapter1);



        title = findViewById(R.id.protitle_id);
        date = findViewById(R.id.Projectdate_id);
        url = findViewById(R.id.Projecturl_id);
        des = findViewById(R.id.PRODescription_id);



        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProject.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        btn = findViewById(R.id.prosavebtn_id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titletext = title.getText().toString().trim();
                String datetext = date.getText().toString().trim();
                String urltext = url.getText().toString().trim();
                String destext = des.getText().toString().trim();


                int flag = 0;

                if(titletext.equals(""))
                {
                    title.setError("Field cannot be empty");
                    flag = 1;
                }


                if(datetext.equals(""))
                {
                    date.setError("Field cannot be empty");
                    flag = 1;
                }


                if(urltext.equals(""))
                {
                    url.setError("Field cannot be empty");
                    flag = 1;
                }


                if(destext.equals(""))
                {
                    des.setError("Field cannot be empty");
                    flag = 1;
                }

                if(flag == 0)
                {

                    JSONArray jsonArray1 = new JSONArray();

                    if (!names.isEmpty()) {
                        for (int i = 0; i < names.size(); i++) {
                            JSONArray jsonArray = new JSONArray();

                            jsonArray.put(names.get(i));
                            jsonArray.put(emails.get(i));
                            jsonArray.put(pnos.get(i));


                            jsonArray1.put(jsonArray);
                        }
                    }



                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date hdate = null;

                    try {
                        hdate = date1.parse(date.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = hdate.getTime();

                    String dateofpro = String.valueOf(time1);

                    String[] info = {titletext , urltext , dateofpro , destext};
                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    String information = jsonEncoder.jsonify_project(info, jsonArray1 , names.size());


                    ApiPostProject apiPostProject = new ApiPostProject(AddProject.this);
                    apiPostProject.execute(information);
                }
            }
        });


        imageView = findViewById(R.id.addauthbtn_id);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddProject.this);
                builder.setCancelable(false);

                initPopupViewControls();

                builder.setView(popupInputDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean bool=true;
                        if(authname.getText().toString().trim().equals(""))
                        { bool=false;
                            authname.setError( "This field is Required");}
                        if(authemail.getText().toString().trim().equals("") )
                        {  bool=false;
                            authemail.setError("This field is Required" );}
                        if(authpno.getText().toString().trim().equals(""))
                        {  bool=false;
                            authpno.setError("This field is Required");}



                        if(emails.contains(authemail.getText().toString().trim()))
                        {  bool=false;
                            authemail.setError("Duplicate entry" );}
                        if(pnos.contains(authpno.getText().toString().trim()) )
                        {  bool=false;
                            authpno.setError("Duplicate entry");}


                        if(bool)
                        {

                            names.add(authname.getText().toString().trim());
                            emails.add(authemail.getText().toString().trim());
                            pnos.add(authpno.getText().toString().trim());
                            adapter1.notifyDataSetChanged();
                            setDynamicHeight(listView);
                            alertDialog.cancel();
                        }
                    }
                });

                canclebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(AddProject.this);

                initPopupViewControlsdelete();

                builder.setView(popupInputDialogViewdelete);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final int pos = position;

                authupname.setText(names.get(position));
                authupemail.setText(emails.get(position));
                authuppno.setText(pnos.get(position));


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        names.remove(pos);
                        emails.remove(pos);
                        pnos.remove(pos);

                        listView.setAdapter(null);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProject.this , android.R.layout.simple_list_item_1, android.R.id.text1 , names);

                        listView.setAdapter(adapter);

                        setDynamicHeight(listView);

                        alertDialog.cancel();

                    }
                });

                final String oldname = authname.getText().toString().trim();



                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean bool=true;


                        if(authupname.getText().toString().trim().equals(""))
                        { bool=false;
                            authupname.setError( "This field is Required");}
                        if(authupemail.getText().toString().trim().equals("") )
                        {  bool=false;
                            authupemail.setError("This field is Required" );}
                        if(authuppno.getText().toString().trim().equals(""))
                        {  bool=false;
                            authuppno.setError("This field is Required");}



                        if(emails.contains(authupemail.getText().toString().trim()))
                        {  bool=false;
                            authupemail.setError("Duplicate entry" );}
                        if(pnos.contains(authuppno.getText().toString().trim()) )
                        {  bool=false;
                            authuppno.setError("Duplicate entry");}


                        if(authupname.getText().toString().trim().equals(oldname))
                        {
                            bool = false;
                        }
                        else
                        {
                            bool = true;
                        }

                        if(bool)
                        {
                            names.set(pos , authupname.getText().toString().trim());
                            emails.set(pos, authupemail.getText().toString().trim());
                            pnos.set(pos , authuppno.getText().toString().trim());
                            adapter1.notifyDataSetChanged();
                            setDynamicHeight(listView);
                            alertDialog.cancel();
                        }

                    }
                });

            }
        });


    }



    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(AddProject.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.authordetail, null);

        authname = popupInputDialogView.findViewById(R.id.authname);
        authemail = popupInputDialogView.findViewById(R.id.authemail);
        authpno = popupInputDialogView.findViewById(R.id.authpno);

        addbtn = popupInputDialogView.findViewById(R.id.addbtn);
        canclebtn = popupInputDialogView.findViewById(R.id.canclebtn);


    }

    private void initPopupViewControlsdelete() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(AddProject.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogViewdelete = layoutInflater.inflate(R.layout.authordetaildelete, null);

        authupname = popupInputDialogViewdelete.findViewById(R.id.authupdatename);
        authupemail = popupInputDialogViewdelete.findViewById(R.id.authupdateemail);
        authuppno = popupInputDialogViewdelete.findViewById(R.id.authupdatepno);

        update = popupInputDialogViewdelete.findViewById(R.id.updatebtn);
        delete = popupInputDialogViewdelete.findViewById(R.id.deletebtn);

    }
    private static void setDynamicHeight(ListView mListView ) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        Log.e("Daynamic height testing",mListView.getWidth()+" "+mListView.getDividerHeight());
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }
}