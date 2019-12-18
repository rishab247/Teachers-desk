package com.cu.project.ui.Splash;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.cu.project.R;
import com.cu.project.Welcome;

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
}
