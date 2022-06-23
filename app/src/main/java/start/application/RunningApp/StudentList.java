package start.application.RunningApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentListWorkoutAdapter sAdapter;
    private List<Student> productList;
    private ProgressBar progressBar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_workouts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        sAdapter = new StudentListWorkoutAdapter(this, productList);
        recyclerView.setAdapter(sAdapter);
        db = FirebaseFirestore.getInstance();

        db.collection("completed").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Student p = d.toObject(Student.class);
                                p.setId(d.getId());
                                productList.add(p);
                            }
                            sAdapter.notifyDataSetChanged();
                        }
                    }
                });
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public class StudentListWorkoutAdapter extends RecyclerView.Adapter<StudentListWorkoutAdapter.ProductViewHolder> {

        private Context mCtx;
        private List<Student> productList;

        public StudentListWorkoutAdapter(Context mCtx, List<Student> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @NonNull
        @Override
        public StudentListWorkoutAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StudentListWorkoutAdapter.ProductViewHolder(
                    LayoutInflater.from(mCtx).inflate(R.layout.layout_product2, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Student product = productList.get(position);

            holder.textViewName.setText(product.getStudent());
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView textViewName, textViewDesc;

            public ProductViewHolder(View itemView) {
                super(itemView);

                textViewName = itemView.findViewById(R.id.textview_name);
                textViewDesc = itemView.findViewById(R.id.textview_desc);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
        }
    }
}