package com.cu.project.ui.Splash;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cu.project.R;
import com.cu.project.Welcome;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SplashActivity extends Activity implements SplashMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(SplashActivity.this , Welcome.class);
        startActivity(intent);

    }

    @Override
    public void openMainActivity() {
    }

    @Override
    public void openLoginActivity() {
    }
//
//    public static void generateHash1(String input) {
//        {
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[16];
//            random.nextBytes(salt);
//            MessageDigest md = null;
//            try {
//                md = MessageDigest.getInstance("SHA-512");
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            md.update(salt);
//            byte[] hashedPassword = md.digest(input.getBytes(StandardCharsets.UTF_8));
//            String hash = Arrays.toString(hashedPassword);
//             Log.d(TAG, "generateHash1: "+salt);
//            Log.d(TAG, "generateHash2: "+ Arrays.toString(hashedPassword));
//            Log.d(TAG, "generateHash3: "+new  String(hashedPassword, 0, 10, StandardCharsets.UTF_8));
//        }}
//    public static void generateHash(String input) {
//        StringBuilder hash = new StringBuilder();
//
//        try {
//            MessageDigest sha = MessageDigest.getInstance("SHA-512");
//            byte[] hashedBytes = sha.digest(input.getBytes());
//
//            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                    'a', 'b', 'c', 'd', 'e', 'f' };
//            for (int idx = 0; idx < hashedBytes.length;   idx ++) {
//                byte b = hashedBytes[idx];
//                hash.append(digits[(b & 0xf0) >> 4]);
//                hash.append(digits[b & 0x0f]);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            // handle error here.
//        }
//String str   = String.valueOf(hash);
//       Log.v("dsd",str);
//    }



}
