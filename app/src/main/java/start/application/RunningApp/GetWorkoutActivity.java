package start.application.RunningApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GetWorkoutActivity extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "WorkoutListActivity";
    private static final String WORKOUTS = "workouts";

    private ArrayAdapter<Workout> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_workout);

        ListView workoutListView = findViewById(R.id.workoutListView);
        adapter = new GetWorkoutActivity.WorkoutAdapter(this, new ArrayList<Workout>());
        workoutListView.setAdapter(adapter);
    }

    class WorkoutAdapter extends ArrayAdapter<Workout> {

        ArrayList<Workout> workouts;

        WorkoutAdapter(Context context, ArrayList<Workout> workouts) {
            super(context, 0, workouts);
            this.workouts = workouts;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_workout, parent, false);
            }

            TextView workoutDay = convertView.findViewById(R.id.itemDay);
            TextView workoutDescription = convertView.findViewById(R.id.itemDescription);
            TextView workoutDistance = convertView.findViewById(R.id.itemDistance);

            Workout p = workouts.get(position);
            workoutDay.setText(p.getDay());
            workoutDescription.setText(p.getDescription());
            workoutDistance.setText(Integer.toString(p.getDistance()));

            return convertView;
        }
    }

    public void onRefreshClick(View view) {
        mDb.collection(WORKOUTS)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Workout> patients = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Workout p = document.toObject(Workout.class);
                            patients.add(p);
                            Log.d(TAG, p.getDescription() + " " + p.getDay() + " " + p.getDistance());
                        }
                        adapter.clear();
                        adapter.addAll(patients);

                    }
                });
    }
}