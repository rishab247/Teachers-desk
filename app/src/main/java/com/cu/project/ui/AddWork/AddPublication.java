package com.cu.project.ui.AddWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Authorclass;
import com.cu.project.ui.detailclass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddPublication extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    EditText pubtitle, publisher, pubdate, puburl, pubdes;
    Button savebtn;
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
        setContentView(R.layout.activity_addpublication);


        pubtitle = findViewById(R.id.title_id);
        publisher = findViewById(R.id.Publication_id);
        pubdate = findViewById(R.id.Publicationdate_id);
        puburl = findViewById(R.id.Publicationurl_id);
        pubdes = findViewById(R.id.Description_id);


        pubdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
//                c.get(Calendar.MILLISECOND);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPublication.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        pubdate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        savebtn = findViewById(R.id.pubsave_id);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = pubtitle.getText().toString();
                String publishertext = publisher.getText().toString();
                String date = pubdate.getText().toString();
                String url = puburl.getText().toString();
                String des = pubdes.getText().toString();

                if (title.equals("")) {
                    pubtitle.setError("Field cannot be empty");
                }

                if (des.equals("")) {
                    pubdes.setError("Field cannot be empty");
                }

                if (url.equals("")) {
                    puburl.setError("Field cannot be empty");
                }

                if (date.equals("")) {
                    pubdate.setError("Field cannot be empty");
                }

                if (publishertext.equals("")) {
                    publisher.setError("Field cannot be empty");
                } else {
                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date hdate = null;

                    try {
                        hdate = date1.parse(pubdate.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = hdate.getTime();

                    String dateofpub = String.valueOf(time1);

                    String[] info = {title, publishertext, url, dateofpub, des};
                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    jsonEncoder.jsonify_pub(info);


                    Log.e("AUTHOR DATA" , names.toString() + " " + emails.toString() + " " + pnos.toString());
                }

            }
        });


        imageView = findViewById(R.id.addauthbtn_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(AddPublication.this);
                alertDialogBuilder.setTitle("Search For Authors or Add");
                alertDialogBuilder.setCancelable(false);

                initPopupViewControls();

                alertDialogBuilder.setView(popupInputDialogView);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String name = authname.getText().toString().trim();
                        String pno = authpno.getText().toString().trim();
                        String email = authemail.getText().toString().trim();


                        if (name.equals("")) {
                            authname.setError("Field cannot be empty");
                        }
                        if (pno.equals("")) {
                            authpno.setError("Field cannot be empty");
                        }
                        if (email.equals("")) {
                            authemail.setError("Field cannot be empty");
                        } else {
                            names.add(name);
                            emails.add(email);
                            pnos.add(pno);
                            Log.e("PRINT", names.toString() + emails.toString() + pnos.toString());
                            Toast.makeText(getApplicationContext(), "CLICKED!", Toast.LENGTH_SHORT).show();

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


        listView = findViewById(R.id.authorslist_id);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);
        ListUtils.setDynamicHeight(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(AddPublication.this);
                alertDialogBuilder.setCancelable(false);

                initPopupViewControlsdelete();

                alertDialogBuilder.setView(popupInputDialogViewdelete);
                authupname.setText(names.get(position));
                authupemail.setText(emails.get(position));
                authuppno.setText(pnos.get(position));

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        names.remove(position);
                        emails.remove(position);
                        pnos.remove(position);

                        listView.setAdapter(null);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1, android.R.id.text1 , names);

                        listView.setAdapter(adapter);
                        ListUtils.setDynamicHeight(listView);



                        Log.e("PRINT AFTER" , names.toString() + emails.toString() + pnos.toString());
                        alertDialog.cancel();



                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        names.set(position , authupname.getText().toString().trim());
                        emails.set(position, authupemail.getText().toString().trim());
                        pnos.set(position , authupemail.getText().toString().trim());

                        alertDialog.cancel();
                    }
                });
            }
        });


    }

    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(AddPublication.this);

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
        LayoutInflater layoutInflater = LayoutInflater.from(AddPublication.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogViewdelete = layoutInflater.inflate(R.layout.authordetaildelete, null);

        authupname = popupInputDialogViewdelete.findViewById(R.id.authupdatename);
        authupemail = popupInputDialogViewdelete.findViewById(R.id.authupdateemail);
        authuppno = popupInputDialogViewdelete.findViewById(R.id.authupdatepno);

        update = popupInputDialogViewdelete.findViewById(R.id.updatebtn);
        delete = popupInputDialogViewdelete.findViewById(R.id.deletebtn);

    }


    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
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
}