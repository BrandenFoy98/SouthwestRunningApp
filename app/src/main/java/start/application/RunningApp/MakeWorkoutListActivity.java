package start.application.RunningApp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.application.RunningApp.R;

public class MakeWorkoutListActivity extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "WorkoutListActivity";
    private static final String WORKOUTS = "workouts";

    private ArrayAdapter<Workout> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeworkout_list);
    }

    public void onSubmitClick(View view) {
        EditText dayEditText = findViewById(R.id.dayEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText distanceEditText = findViewById(R.id.distanceEditText);

        String day = dayEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String distanceString = distanceEditText.getText().toString().trim();
        int distance = 0;

        if(!TextUtils.isEmpty(distanceString)) { // If EditText is not empty
            distance = Integer.parseInt(distanceString);// parse its content to integer
        }

        if(day.isEmpty()){
            dayEditText.setError("Email is empty");
            dayEditText.requestFocus();
            return;
        }
        if(description.isEmpty()){
            descriptionEditText.setError("Email is empty");
            descriptionEditText.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(distanceString)) {
            distanceEditText.setError("Email is empty");
            distanceEditText.requestFocus();
            return;
        }

        Workout p = new Workout(day, description, distance);
        Log.d(TAG, "Submitted description: " + p.getDescription() + ", day: "+ p.getDay() + ", distance: " + p.getDistance());
        mDb.collection(WORKOUTS)
                .add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Patient entry added successfully.");
                        Toast.makeText(MakeWorkoutListActivity.this,
                                "Patient entry added!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Could not add patient!");
                        Toast.makeText(MakeWorkoutListActivity.this,
                                "Failed to add patient!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
