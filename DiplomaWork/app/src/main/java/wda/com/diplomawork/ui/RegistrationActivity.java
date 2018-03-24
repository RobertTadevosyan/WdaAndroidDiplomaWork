package wda.com.diplomawork.ui;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import wda.com.diplomawork.R;
import wda.com.diplomawork.base.BaseActivity;
import wda.com.diplomawork.core.realM.User;

public class RegistrationActivity extends BaseActivity {

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
//        databaseAddDataeventListener();
        viewsConfig();
    }

    private void databaseAddDataeventListener() {
        myRef = database.getReference().getRoot();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
// whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

    private void createNewUserActionPerformed(){
        if(!areAllFieldsValid()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef = database.getReference("users");
                            myRef.child(user.getUid()).setValue(new User(firstName.getText().toString(), lastName.getText().toString(), login.getText().toString()));
                            movToLoggedInPageWithNewTask();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean areAllFieldsValid() {
        //TODO: VALIDATE ALL DATA
        return true;
    }
}
