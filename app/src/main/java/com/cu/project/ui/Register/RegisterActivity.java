package com.cu.project.ui.Register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.RegisterAPIHelper;
import com.cu.project.R;
import com.cu.project.Util.JsonEncoder;
import com.cu.project.ui.Profiile.ProfileActivity;
import com.cu.project.ui.login.loginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{
    Button btn_register , btn_verify;
    TextView Alreadyamember ;
    Spinner type_spinner;
    Dialog dialog;
    private String mVerificationId;
    AlertDialog.Builder builder;
    View popupdialog;
    Button verifybtn , canclebtn;
    EditText otpedittext;
    TextView resendtext;
      AlertDialog alertDialog;
    private FirebaseAuth mAuth;


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

        mAuth = FirebaseAuth.getInstance();

       Alreadyamember = findViewById(R.id.Alreadyamembertext);
        Alreadyamember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                startActivity(intent);

            }
        });


        type_spinner = findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_of_account, android.R.layout.simple_spinner_item);
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




        doj.setOnClickListener(new View.OnClickListener(){
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



            btn_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (pno.getText().toString().trim().equals("")) {
                        pno.setError("Please enter a Phone number");
                    }
                    else if(pno.getText().toString().trim().length() < 10)
                    {
                        pno.setError("Phone Number not valid");
                    }
                    else {
                        sendVerificationCode( pno.getText().toString().trim());

                         builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setCancelable(false);
                        initializedialog();

                        builder.setView(popupdialog);

                          alertDialog = builder.create();

                        alertDialog.show();



                        resendtext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendVerificationCode( pno.getText().toString().trim());

                                initiate = false;

                                Toast.makeText(RegisterActivity.this , "OTP is resend to your device", Toast.LENGTH_SHORT).show();
                            }
                        });


                        verifybtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(otpedittext.getText().toString().trim().equals("")){
                                    otpedittext.setError("Enter otp");
                                     initiate = false;

                                }
                                else
                                {
                                    verifyVerificationCode(otpedittext.getText().toString().trim());

                                     initiate = true;
                                }
                            }
                        });







                        canclebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initiate = false;
                                alertDialog.cancel();
                            }
                        });
                    }
                }

            });



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

                if(fnametext.equals("") || fnametext.equals("None"))
                {
                    fname.setError("This field is Required");
                    flag = 1;
                }

                if(lnametext.equals("") || lnametext.equals("None"))
                {
                    lname.setError("This field is Required");
                    flag = 1;
                }

                if(eidtext.equals(""))
                {
                    eid.setError("This field is Required");
                    flag = 1;
                }

                if(!EMAIL_ADDRESS_PATTERN.matcher(emailtext).matches())
                {

                    email.setError("Enter a valid email address");
                }
                if(emailtext.equals(""))
                {
                    email.setError("This field is Required");
                }

                if(passwordtext.length() < 6)
                {
                    pass.setError("Password must be at least 6 letters long");
                }


                if(passwordtext.equals(""))
                {
                    pass.setError("This field is Required");
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
                        pno.setError("This field is Required");
                    }
                    else
                    pno.setError("Phone number not valid");

                    flag = 1;
                }

                if(departtext.equals(""))
                {
                    department.setError("This field is Required");
                    flag = 1;
                }

                if(qualificationtext.equals(""))
                {
                    qualification.setError("This field is Required");
                    flag = 1;
                }

                if(universitytext.equals(""))
                {
                    university.setError("This field is Required");

                    flag = 1;
                }

                if(dojtext.equals(""))
                {
                    doj.setError("This field is Required");
                    flag = 1;
                }


                if(dobtext.equals(""))
                {
                    dob.setError("This field is Required ");
                    flag = 1;
                }


                if(!initiate)
                {
                    pno.setError("Verify Your Phone number");
                }




                if(flag == 0 && initiate)
                {

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

                    String[] valstring = {finalstring2, finalstring1, emailtext, hashedpass, pnotext, departtext, dateofjoin, qualificationtext,
                            universitytext, dateofbirth, departtext , type_spinner.getSelectedItem().toString()};

                    JsonEncoder jsonEncoder = new JsonEncoder(getApplicationContext());

                    String information = jsonEncoder.jsonify(valstring);

                    RegisterAPIHelper apiHelper = new RegisterAPIHelper(RegisterActivity.this);
                    apiHelper.execute(information);

                    Intent intent = new Intent(RegisterActivity.this , loginActivity.class);
                    startActivity(intent);

                    }

                }

        });

    }



    public void initializedialog()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(RegisterActivity.this);

        popupdialog = layoutInflater.inflate(R.layout.otp, null);


        otpedittext = popupdialog.findViewById(R.id.editText2);
        verifybtn = popupdialog.findViewById(R.id.btnverfy);
        canclebtn = popupdialog.findViewById(R.id.btncancle);
        resendtext = popupdialog.findViewById(R.id.resendtextid);


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


    int range = 9;
    int length = 4;

    public int generateRandomNumber() {
        int randomNumber;

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0)
            {
                i = -1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);

        return randomNumber;
    }




    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        Log.e(   "VerificationCompleted:","testing"  );

    }



    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                Log.e(   "VerificationCompleted:",code  );
                otpedittext.setText(code);
//                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e(   "VerificationCompleted:",e.getMessage()  );
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.e(     "onCodeSent: ",s+forceResendingToken.toString() );
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code );

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            alertDialog.cancel();
                            initiate =  true;
                            Toast.makeText(getBaseContext(),"Verified   ",Toast.LENGTH_SHORT).show();
                        } else {


                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



}

