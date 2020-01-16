package com.cu.project.ui.Register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.login.loginActivity;
import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements RegisterMvpView{
    Button btn_register , btn_verify;
    TextView Alreadyamember ;
    Spinner type_spinner;
    Dialog dialog;
    CoordinatorLayout coordinatorLayout;




    private boolean initiate = false;

    EditText fname , lname , eid , email , pass , confirmpass , pno , department , doj , qualification , university , dob;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       Alreadyamember = findViewById(R.id.Alreadyamembertext);
        Alreadyamember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                startActivity(intent);

            }
        });


        type_spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_of_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);






        fname = findViewById(R.id.fnameregisteretext);
        lname = findViewById(R.id.lnameregisteretext);
        eid = findViewById(R.id.eidregisteretext);
        email = findViewById(R.id.emailregisteretext);
        pass = findViewById(R.id.passwordregisteretext);
        confirmpass = findViewById(R.id.confirmpasswordregisteretext);
        pno = findViewById(R.id.phoneregisteretext);
        department = findViewById(R.id.Departmentregisteretext);
        doj = findViewById(R.id.Joiningregisteretext);
        qualification = findViewById(R.id.Qualificationregisteretext);
        university = findViewById(R.id.Universityregisteretext);
        dob = findViewById(R.id.dobregisteretext);
        btn_verify = findViewById(R.id.verifyregisterbutton);
        dialog = new Dialog(getApplicationContext());
        dialog.setContentView(R.layout.dialog_box);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Enter OTP");

                final EditText input = new EditText(RegisterActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setCancelable(false);


                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initiate = true;

                    }
                });

                builder.setNegativeButton("Resend OTP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
         }

        });



        // datepicker
        doj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
//                c.get(Calendar.MILLISECOND);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        doj.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });



        dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });









        // button for register and going to login screen

        btn_register = findViewById(R.id.Registerregisterbutton);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname.setError(null);
                lname.setError(null);
                eid.setError(null);
                email.setError(null);
                pass.setError(null);
                confirmpass.setError(null);
                pno.setError(null);
                department.setError(null);
                doj.setError(null);
                qualification.setError(null);
                university.setError(null);


                String fnametext = fname.getText().toString().trim();
                String lnametext = lname.getText().toString().trim();
                String eidtext = eid.getText().toString().trim();
                String emailtext = email.getText().toString().trim();
                String passwordtext = pass.getText().toString().trim();
                String confirmtext = confirmpass.getText().toString().trim();
                String pnotext = pno.getText().toString().trim();
                String departtext = department.getText().toString().trim();
                String dojtext = doj.getText().toString().trim();
                String qualificationtext = qualification.getText().toString().trim();
                String universitytext = university.getText().toString().trim();
                String dobtext =  dob.getText().toString().trim();





                int flag = 0;

                if(fnametext.equals(""))
                {
                    fname.setError("Enter FirstName");
                    flag = 1;
                }

                if(lnametext.equals(""))
                {
                    lname.setError("Enter LastName");
                    flag = 1;
                }

                if(eidtext.equals(""))
                {
                    eid.setError("Enter Eid");
                    flag = 1;
                }

                if(!EMAIL_ADDRESS_PATTERN.matcher(emailtext).matches())
                {

                    email.setError("Enter a valid email address");
                }
                if(emailtext.equals(""))
                {
                    email.setError("Enter Email");
                }

                if(passwordtext.length() < 6)
                {
                    pass.setError("Password must be at least 6 letters long");
                }


                if(passwordtext.equals(""))
                {
                    pass.setError("Enter Password");
                    flag = 1;
                }

                if(!confirmtext.equals(passwordtext))
                {
                    confirmpass.setError("Password Does not match");
                    flag = 1;
                }

                if(pnotext.length() < 10)
                {
                    if(pnotext.equals(""))
                    {
                        pno.setError("Enter Phone number");
                    }
                    else
                    pno.setError("Phone number not valid");

                    flag = 1;
                }

                if(departtext.equals(""))
                {
                    department.setError("Enter Department");
                    flag = 1;
                }

                if(qualificationtext.equals(""))
                {
                    qualification.setError("Enter Qualification");
                    flag = 1;
                }

                if(universitytext.equals(""))
                {
                    university.setError("Enter University");

                    flag = 1;
                }

                if(dojtext.equals(""))
                {
                    doj.setError("Select a date");
                    flag = 1;
                }


                if(dobtext.equals(""))
                {
                    dob.setError("Select a date");
                    flag = 1;
                }

                if(initiate == false)
                {
                    pno.setError("Verify Your Phone number");
                }




                if(flag == 0 && initiate == true)
                {
                    // for going to the next screen

                    SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");

                    Date dojdate = null;

                    try {
                        dojdate = date1.parse(doj.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time1 = dojdate.getTime();

                    String dateofjoin = String.valueOf(time1);



                    SimpleDateFormat date2 = new SimpleDateFormat("yyyy/MM/dd");

                    Date dobdate = null;

                    try {
                        dobdate = date2.parse(dob.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long time2 = dobdate.getTime();

                    final String dateofbirth = String.valueOf(time2);



                    String cap1 = fnametext.substring(0, 1).toUpperCase() + fnametext.substring(1);
                    String cap2 = lnametext.substring(0, 1).toUpperCase() + lnametext.substring(1);






                    String hashedpass = generatedhash12(generatedhash12(passwordtext));
                    int len = hashedpass.length();

                    String finalstring1 = cap1 + " " + cap2;
                    String finalstring2 = eidtext.substring(0, 1).toUpperCase() + eidtext.substring(1);

                    Log.e("String size" , String.valueOf(len));
                    String[] valstring = {finalstring2, finalstring1, emailtext, hashedpass, pnotext, departtext, dateofjoin, qualificationtext,
                            universitytext, dateofbirth, departtext};
                    Log.v("testing",eidtext + fnametext + emailtext + hashedpass + pnotext + departtext + dojtext + qualificationtext +
                            universitytext + dobtext + departtext);


                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());
                    jsonEncoder.jsonify(valstring);



                    Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    String generatedhash12(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

