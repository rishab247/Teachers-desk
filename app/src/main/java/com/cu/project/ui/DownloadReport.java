package com.cu.project.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.ApiDownload;
import com.cu.project.ProfileEdit;
import com.cu.project.R;
import com.cu.project.ui.Register.RegisterActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DownloadReport  extends AppCompatActivity {


    EditText datefrom , dateto;

    Button fulldownload;

    Button downloadbtn;
    Spinner type_spin;

    private View popupdownload;
    TextView pdf , excel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_report);


        datefrom = findViewById(R.id.fromdate);
        dateto = findViewById(R.id.todate);
        fulldownload = findViewById(R.id.downloadfull);
        downloadbtn = findViewById(R.id.button2);
        type_spin = findViewById(R.id.spinner_);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.download_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spin.setAdapter(adapter);


        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DownloadReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datefrom.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DownloadReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateto.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromdate1 = datefrom.getText().toString().trim();
                String todadte1 = dateto.getText().toString().trim();
                final String type = type_spin.getSelectedItem().toString().trim();

                int flag = 0;

                if(fromdate1.equals(""))
                {
                    datefrom.setError("Field cannot be empty!");
                    flag = 1;
                }
                if(todadte1.equals("")) {
                    dateto.setError("Field cannot be empty!");
                    flag = 1;
                }

                if(flag == 0)
                {
                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date fromdate = null;

                    try {
                        fromdate = date1.parse(datefrom.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = fromdate.getTime();

                    final String datestart = String.valueOf(time1);




                    final SimpleDateFormat date2 = new SimpleDateFormat("yyyy/MM/dd");

                    Date todate = null;

                    try {
                        todate = date2.parse(dateto.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time2 = todate.getTime();

                    final String dateend = String.valueOf(time2);


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DownloadReport.this);

                    initPopupViewControl();
                    alertDialogBuilder.setView(popupdownload);

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    pdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String finaltype = null;
                            if(type.equals("All"))
                                finaltype = "all";

                            else
                                finaltype = type;

                            ApiDownload apiDownload = new ApiDownload(DownloadReport.this);
                            apiDownload.execute(datestart , dateend , finaltype);
                        }
                    });

//                    excel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ApiDownload apiDownload = new ApiDownload(DownloadReport.this);
//                            apiDownload.execute(datestart , dateend);
//                        }
//                    });

                }


            }
        });

        fulldownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DownloadReport.this);

                initPopupViewControl();
                alertDialogBuilder.setView(popupdownload);

                final AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.setTitle("Select an option!");
//                alertDialog.setIcon(android.R.drawable.ic_menu_set_as);
//                alertDialog.
                alertDialog.show();

                pdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DownloadReport.this , "PDF" , Toast.LENGTH_SHORT).show();
                    }
                });

                excel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DownloadReport.this , "EXCEL" , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }

    private void initPopupViewControl() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(DownloadReport.this);

        popupdownload = layoutInflater.inflate(R.layout.downloadpopup, null);

        pdf = popupdownload.findViewById(R.id.pdfdown);
        excel = popupdownload.findViewById(R.id.exceldown);

    }

}
