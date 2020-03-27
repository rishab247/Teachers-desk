package com.cu.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.project.APIHelper.APIVerifyIndividual;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;

public class Individual extends AppCompatActivity {

    ImageView pfile;
    Button verify_btn;
    TextView verify_txt;
    ImageView verify_img;


    // userInfo
    TextView userName , userEmail, userPhone, userDob, userBranch, userDOJ, userQualification, euid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual);

        pfile = findViewById(R.id.circleImageView);


        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        final String[] informationarr = bundle.getStringArray("information");

        boolean verify = bundle.getBoolean("Verify");

        final String userEid = bundle.getString("EID");

        assert informationarr != null;
        String img = informationarr[8];


        if (img.equals("")) {

            Log.e("in individual class" , "Image not present");

        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < img.length(); i++) {
                if (img.charAt(i) == '\\' && img.charAt(i + 1) == 'n') {
                    stringBuilder.append("\n");
                    i = i + 1;
                } else
                    stringBuilder.append(img.charAt(i));
            }

            // setting profile image
            Bitmap bitmap = StringToBitMap(String.valueOf(stringBuilder));
            pfile.setImageBitmap(bitmap);
        }




        // setting user information

        userName = findViewById(R.id.textView24);
        userPhone = findViewById(R.id.textView27);
        userEmail = findViewById(R.id.textView29);
        userDob = findViewById(R.id.textView31);
        userBranch = findViewById(R.id.textView33);
        userDOJ = findViewById(R.id.textView35);
        userQualification = findViewById(R.id.textView37);
        euid = findViewById(R.id.textView23);


        long date1 = Long.parseLong(informationarr[4]);

        DateFormat simple = new SimpleDateFormat("dd MMM yyyy");

        java.util.Date result1 = new Date(date1);

        String hdate = simple.format(result1);



        long date2 = Long.parseLong(informationarr[5]);

        DateFormat simple1 = new SimpleDateFormat("dd MMM yyyy");

        java.util.Date result2 = new Date(date2);

        String hdate2 = simple1.format(result2);

        userName.setText(informationarr[0]);
        userPhone.setText(informationarr[1]);
        userEmail.setText(informationarr[2]);
        userBranch.setText(informationarr[3]);
        userDob.setText(hdate);
        userDOJ.setText(hdate2);
        userQualification.setText(informationarr[6]);
        euid.setText(userEid);


        // verify user
        verify_txt = findViewById(R.id.verifiedtext_id);
        verify_img = findViewById(R.id.verify_img);
        verify_btn = findViewById(R.id.button);

        if(verify){
            verify_txt.setVisibility(View.VISIBLE);
            verify_img.setVisibility(View.VISIBLE);
            verify_btn.setVisibility(GONE);
        }
        else{
            verify_txt.setVisibility(View.GONE);
            verify_img.setVisibility(View.GONE);
            verify_btn.setVisibility(View.VISIBLE);
        }

        if(verify_btn.getVisibility() == View.VISIBLE){
            verify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(Individual.this, "Verified Buton" , Toast.LENGTH_SHORT).show();


                    String[] eid = new String[]{userEid};
                    APIVerifyIndividual apiVerifyIndividual = new APIVerifyIndividual(Individual.this, eid,1);
                    apiVerifyIndividual.execute();

                }
            });
        }
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
