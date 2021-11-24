package start.application.RunningApp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.RunningApp.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ProductViewHolder> {

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

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewQty;

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
