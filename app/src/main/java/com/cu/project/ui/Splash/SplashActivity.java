package com.cu.project.ui.Splash;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.cu.project.R;
import com.cu.project.Welcome;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SplashActivity extends Activity implements SplashMvpView {

    private static int SPLASH_SCREEN_TIME_OUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this, Welcome.class);

                startActivity(i);

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }

    @Override
    public void openMainActivity() {
    }

    @Override
    public void openLoginActivity() {
    }


}
