package wda.com.diplomawork.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import wda.com.diplomawork.R;

public class LogInActivity extends AppCompatActivity {
    private EditText yourLogin;
    private EditText yourPassword;
    private Button buttonLogIn;
    private Button buttonCreate;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        yourLogin = (EditText) findViewById(R.id.enter_login);
        yourPassword = (EditText) findViewById(R.id.enter_password);
        buttonLogIn = (Button) findViewById(R.id.button_login);
        buttonCreate = (Button) findViewById(R.id.button_create);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
                sendLoginRequest();
                moveLoggedInPage();
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

                        // ...
                    }
                });
    }

    private void moveToRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void moveLoggedInPage() {
        Intent intent = new Intent(this, LoggedInActivity.class);
//        intent.putExtra("login", yourLogin.getText().toString());
//        intent.putExtra("password", yourPassword.getText().toString());
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        }
        //TODO: implement
    }

}

class Validation {
    public static boolean isValidLogin(String login) {
        return login.length() >= 6;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

}





