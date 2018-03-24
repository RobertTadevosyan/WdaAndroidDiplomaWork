package wda.com.diplomawork.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wda.com.diplomawork.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
