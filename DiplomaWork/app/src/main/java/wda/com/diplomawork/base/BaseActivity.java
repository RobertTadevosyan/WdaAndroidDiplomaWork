package wda.com.diplomawork.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wda.com.diplomawork.ui.LoggedInActivity;

/**
 * Created by wedoapps on 3/24/18.
 */

public class BaseActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    protected void movToLoggedInPageWithNewTask() {
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
