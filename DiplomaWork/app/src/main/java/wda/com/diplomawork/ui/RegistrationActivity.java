package wda.com.diplomawork.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wda.com.diplomawork.R;
import wda.com.diplomawork.ui.core.User;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText firstName;
    private EditText lastName;
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_acivity);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        viewsConfig();
    }

    private void viewsConfig() {
        firstName = findViewById(R.id.et_firstName);
        lastName = findViewById(R.id.et_lastName);
        login = findViewById(R.id.et_logIn);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_conf_pass);
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUserActionPerformed();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void createNewUserActionPerformed(){
        if(!areAllFieldsValid()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference myRef = database.getReference("users");
                            myRef.setValue(new User(firstName.getText().toString(), lastName.getText().toString(), login.getText().toString()));
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private boolean areAllFieldsValid() {
        //TODO: VALIDATE ALL DATA
        return true;
    }
}
