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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    Button btn2_signup;
    EditText user_name, pass_word;
    FirebaseAuth mAuth;
    CheckBox isCoachBox, isRunnerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user_name = findViewById(R.id.username);
        pass_word = findViewById(R.id.password1);
        isCoachBox = findViewById(R.id.checkBox);
        isRunnerBox = findViewById(R.id.checkBox2);
        btn2_signup = findViewById(R.id.sign);
        mAuth = FirebaseAuth.getInstance();

        isRunnerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isCoachBox.setChecked(false);
                }
            }
        });

        isCoachBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isRunnerBox.setChecked(false);
                }
            }
        });

        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (!(isCoachBox.isChecked() || isRunnerBox.isChecked())) {
                    Toast.makeText(SignupActivity.this.getApplicationContext(), "Check a Box", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {





                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this.getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            // if the user created intent to login activity
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // Registration failed
                            Toast.makeText(SignupActivity.this.getApplicationContext(), "Registration failed!!\n" +
                                    "You may have an existing account.\n" + "Please try Logging in", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
