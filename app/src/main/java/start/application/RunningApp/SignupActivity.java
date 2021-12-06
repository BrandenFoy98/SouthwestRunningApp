package start.application.RunningApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    Button btn2_signup;
    EditText user_name, pass_word;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    CheckBox isCoachBox, isRunnerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user_name = findViewById(R.id.username);
        pass_word = findViewById(R.id.password1);
        btn2_signup = findViewById(R.id.sign);
        isCoachBox = findViewById(R.id.coachBox);
        isRunnerBox = findViewById(R.id.runnerBox);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        isCoachBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isRunnerBox.setChecked(false);
                }
            }
        });

        isRunnerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isCoachBox.setChecked(false);
                }
            }
        });

        btn2_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!(isCoachBox.isChecked() || isRunnerBox.isChecked()) ) {
                    Toast.makeText(SignupActivity.this, "Select and account type", Toast.LENGTH_LONG).show();
                    return;
                }

                String email = user_name.getText().toString().trim();
                String password = pass_word.getText().toString().trim();
                if (email.isEmpty()) {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    user_name.setError("Enter the valid email address");
                    user_name.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass_word.setError("Enter the password");
                    pass_word.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    pass_word.setError("Length of the password should be more than 6");
                    pass_word.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        DocumentReference df = fStore.collection("users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("UserEmail", user_name.getText().toString().trim());
                        if (isCoachBox.isChecked()) {
                            userInfo.put("isCoach", "1");
                        }

                        if (isRunnerBox.isChecked()) {
                            userInfo.put("isRunner", "1");
                        }

                        df.set(userInfo);

                        Toast.makeText(SignupActivity.this.getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                        // if the user created intent to login activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Registration failed
                        Toast.makeText(SignupActivity.this.getApplicationContext(), "Registration failed!!\n" +
                                "You may have an existing account.\n" + "Please try Logging in", Toast.LENGTH_LONG).show();
                    }
                        });
            }
        });
    }
}