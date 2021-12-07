package start.application.RunningApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.RunningApp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetWorkout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<Workout> productList;
    private ProgressBar progressBar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new WorkoutAdapter(this, productList);

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        db.collection("products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Workout p = d.toObject(Workout.class);
                                p.setId(d.getId());
                                productList.add(p);

                            }

                            adapter.notifyDataSetChanged();

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

    public static class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ProductViewHolder> {

        private Context mCtx;
        private List<Workout> productList;

        public WorkoutAdapter(Context mCtx, List<Workout> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductViewHolder(
                    LayoutInflater.from(mCtx).inflate(R.layout.layout_product, parent, false)
            );
        }


        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Workout product = productList.get(position);

            holder.textViewName.setText(product.getName());
            holder.textViewDesc.setText(product.getDescription());
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
                Workout product = productList.get(getAdapterPosition());
                Intent intent = new Intent(mCtx, UpdateWorkout.class);
                intent.putExtra("product", product);
                mCtx.startActivity(intent);
            }
        }

    }
}
