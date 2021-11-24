package start.application.RunningApp;

import android.app.appsearch.GetByDocumentIdRequest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateWorkout extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;

    private FirebaseFirestore db;

    private Workout product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        product = (Workout) getIntent().getSerializableExtra("product");
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextDesc = findViewById(R.id.edittext_desc);

        editTextName.setText(product.getName());
        editTextDesc.setText(product.getDescription());


        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String name, String brand) {
        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            editTextBrand.setError("Brand required");
            editTextBrand.requestFocus();
            return true;
        }
        return false;
    }


    private void updateProduct() {
        String name = editTextName.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if (!hasValidationErrors(name, desc)) {

            Workout p = new Workout(name, desc);


            db.collection("products").document(product.getId())
                    .update(
                            "description", p.getDescription(),
                            "name", p.getName()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateWorkout.this, "Product Updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateWorkout.this, GetWorkout.class));
                        }
                    });
        }
    }

    private void deleteProduct() {
        db.collection("products").document(product.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateWorkout.this, "Product deleted", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(UpdateWorkout.this, GetWorkout.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                updateProduct();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
