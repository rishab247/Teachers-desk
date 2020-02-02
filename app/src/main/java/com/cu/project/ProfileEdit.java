package com.cu.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.ApiEditProfile;
import com.cu.project.APIHelper.ApiPassword;
import com.cu.project.ui.detailclass;
import com.cu.project.ui.login.loginActivity;

import java.util.concurrent.ExecutionException;

public class ProfileEdit extends AppCompatActivity {

    TextView chagepass , saveUserDataButton , cancelUserDataButton;
    private View popupInputDialog;
    private View popupInputDialogprofile;
    private EditText oldpasswordEditText, newpasswordEditText;


    EditText fname , dob , doj , email , eid , pno , quali , depart, uni;

    Button savebtn;
    private EditText confirmpass;
    private TextView canclepass , savepass;

    String password;
    String p_no;

    String qualifications;

    String department;

    String university;



    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);

        chagepass = findViewById(R.id.passchangetext);


        chagepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileEdit.this);
                alertDialogBuilder.setCancelable(false);

                initPopupViewControls();

                alertDialogBuilder.setView(popupInputDialog);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String oldpass = oldpasswordEditText.getText().toString().trim();
                        String newpass = newpasswordEditText.getText().toString().trim();


                        int flag = 0;

                        if (oldpass.equals("")) {
                            oldpasswordEditText.setError("Field cannot be empty");
                            flag = 1;
                        }
                        if (newpass.equals("")) {
                            newpasswordEditText.setError("Field cannot be empty");
                            flag = 1;
                        }

                        if(flag == 0)
                        {
                            oldpass = loginActivity.generatedhash12(oldpass);
                            oldpass = loginActivity.generatedhash12(oldpass);

                            newpass = loginActivity.generatedhash12(newpass);
                            newpass = loginActivity.generatedhash12(newpass);


                            if (oldpass.equals(newpass)) {
                                Toast.makeText(getApplicationContext(), "cannot have previous password", Toast.LENGTH_SHORT).show();
                            } else {
                                ApiPassword apiPassword = new ApiPassword(ProfileEdit.this);
                                apiPassword.execute(oldpass, newpass);
                                alertDialog.cancel();
                            }
                        }

                    }
                });

                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });


            }
        });

        fname = findViewById(R.id.fnameedittext);
        email = findViewById(R.id.emailedittext);
        eid = findViewById(R.id.eidedittext);
        pno = findViewById(R.id.phoneedittext);
        quali = findViewById(R.id.Qualificationedittext);
        depart = findViewById(R.id.Departmentedittext);
        uni = findViewById(R.id.Universityedittext);
        dob = findViewById(R.id.dobedittext);
        doj = findViewById(R.id.dojedittext);


        Bundle bundle = getIntent().getExtras();

        final String[] dataarry = bundle.getStringArray("data");


        fname.setText(dataarry[0]);
        email.setText(dataarry[1]);
        pno.setText(dataarry[2]);
        depart.setText(dataarry[3]);
        doj.setText(dataarry[4]);
        quali.setText(dataarry[5]);
        uni.setText(dataarry[6]);
        dob.setText(dataarry[7]);
        eid.setText(dataarry[8]);


        savebtn = findViewById(R.id.savechanges);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                p_no = pno.getText().toString().trim();

                qualifications = quali.getText().toString().trim();

                department = depart.getText().toString().trim();

                university = uni.getText().toString().trim();

                int flag = 0;


                if (p_no.equals("")) {
                    pno.setError("Field cannot be empty");
                    flag = 1;
                }
                if (qualifications.equals("")) {
                    quali.setError("Field cannot be empty");
                    flag = 1;
                }
                if (department.equals("")) {
                    depart.setError("Field cannot be empty");
                    flag = 1;
                }
                if (university.equals("")) {
                    uni.setError("Field cannot be empty");
                    flag = 1;
                }

                if (flag == 0) {

                    if (p_no.equals(dataarry[2]) && qualifications.equals(dataarry[5]) && department.equals(dataarry[3]) && university.equals(dataarry[6])) {
                        Toast.makeText(getApplicationContext(), "None of the fields are changed", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileEdit.this);
                        alertDialogBuilder.setCancelable(false);

                        initPopupViewControlsprofileedit();

                        alertDialogBuilder.setView(popupInputDialogprofile);

                        final AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                        savepass.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                password = confirmpass.getText().toString().trim();

                                password = loginActivity.generatedhash12(password);
                                password = loginActivity.generatedhash12(password);
                                alertDialog.cancel();

                                ApiEditProfile apiEditProfile = new ApiEditProfile(ProfileEdit.this);
                                apiEditProfile.execute(p_no, qualifications, university, department, password);


                            }
                        });

                        canclepass.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });


                    }
                }


            }
        });
    }

    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileEdit.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialog = layoutInflater.inflate(R.layout.passchangedialog, null);

        oldpasswordEditText =  popupInputDialog.findViewById(R.id.oldpass);
        newpasswordEditText =  popupInputDialog.findViewById(R.id.newpass);
        saveUserDataButton = popupInputDialog.findViewById(R.id.buttonsaveuserdata);
        cancelUserDataButton = popupInputDialog.findViewById(R.id.buttoncanceluserdata);


    }


    private void initPopupViewControlsprofileedit()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileEdit.this);

        popupInputDialogprofile = layoutInflater.inflate(R.layout.passdialog, null);

        confirmpass =  popupInputDialogprofile.findViewById(R.id.confirmpass);

        savepass = popupInputDialogprofile.findViewById(R.id.btnconfirm);
        canclepass = popupInputDialogprofile.findViewById(R.id.canclepass);


    }

}
