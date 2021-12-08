package start.application.RunningApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MakeWorkout extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextDesc;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_workout);

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextDesc = findViewById(R.id.edittext_desc);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_products).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String name, String description) {
        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return true;
        }

        if (description.isEmpty()) {
            editTextDesc.setError("Description required");
            editTextDesc.requestFocus();
            return true;
        }
        return false;
    }


    private void saveProduct(){
        String name = editTextName.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if (!hasValidationErrors(name, desc)) {

            CollectionReference dbProducts = db.collection("products");

            Workout product = new Workout(name, desc);

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MakeWorkout.this, "Product Added", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MakeWorkout.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

       switch(v.getId()){
           case R.id.button_save:
               saveProduct();
               break;
           case R.id.textview_view_products:
               startActivity(new Intent(this, GetWorkout.class));
               break;
       }

    }
}
