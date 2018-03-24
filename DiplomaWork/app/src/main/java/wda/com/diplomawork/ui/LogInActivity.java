package wda.com.diplomawork.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import wda.com.diplomawork.R;
import wda.com.diplomawork.base.BaseActivity;
import wda.com.diplomawork.core.realM.User;
import wda.com.diplomawork.core.realM.UserRM;
import wda.com.diplomawork.util.Validation;

public class LogInActivity extends BaseActivity {
    private EditText yourLogin;
    private EditText yourPassword;
    private Button buttonLogIn;
    private Button buttonCreate;
    private RelativeLayout progressLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if(UserRM.getLoggedInUser() != null){
            movToLoggedInPageWithNewTask();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        viewConfiguretions();
    }

    private void viewConfiguretions() {
        progressLayout = findViewById(R.id.progress_layout);
        yourLogin = (EditText) findViewById(R.id.enter_login);
        yourPassword = (EditText) findViewById(R.id.enter_password);
        buttonLogIn = (Button) findViewById(R.id.button_login);
        buttonCreate = (Button) findViewById(R.id.button_create);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendLoginRequest();
            }
        });
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToRegistrationActivity();
            }
        });
    }

    private void sendLoginRequest() {
        if (!validate()) {
            return;
        }
        progressLayout.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(yourLogin.getText().toString(), yourPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        progressLayout.setVisibility(View.GONE);

                        // ...
                    }
                });
    }

    private void moveToRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        String error = "";
        if (!Validation.isValidLogin(yourLogin.getText().toString())) {
            error = "Login must contain at least 6 characters";
        } else if (!Validation.isValidPassword(yourPassword.getText().toString())) {
            error = "Login must contain at least 8 characters";
        }
        if (!error.isEmpty()) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void updateUI(final FirebaseUser user){
        if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
//            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            myRef = database.getReference("users");
            myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserRM userFromFDB = new UserRM();
                    HashMap<String, String> userMap = (HashMap<String, String>)dataSnapshot.getValue();
                    userFromFDB.setFirstName(userMap.get("firstName"));
                    userFromFDB.setLastName(userMap.get("lastName"));
                    userFromFDB.setLogin(userMap.get("login"));
                    UserRM.saveUserInRealM(userFromFDB);
                    movToLoggedInPageWithNewTask();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}






