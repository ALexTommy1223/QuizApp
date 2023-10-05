    package com.example.baothuc.adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.baothuc.R;
    import com.example.baothuc.databinding.ItemsCategoryBinding;
    import com.example.baothuc.model.CategoryModel;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.database.FirebaseDatabase;
    import com.squareup.picasso.Picasso;

    import java.util.ArrayList;

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
        Context context;
        ArrayList<CategoryModel> list;
        FirebaseDatabase database;
        public CategoryAdapter(Context context, ArrayList<CategoryModel> list) {
            this.context = context;
            this.list = list;
            database=FirebaseDatabase.getInstance();
        }

        @NonNull
        @Override
        public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.items_category,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
            CategoryModel model=list.get(position);
            holder.binding.categoryName.setText(model.getCategoryName());
            Picasso.get().load(model.getCategoryImage())
                    .placeholder(R.drawable.logo)
                    .into(holder.binding.profileImage);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemsCategoryBinding binding;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                binding=ItemsCategoryBinding.bind(itemView);
                binding.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            removeItem(position);
                        }
                    }
                });
            }
        }

        private void removeItem(int position) {
            String categoryKey=list.get(position).getKey();
            list.remove(position);
            notifyItemRemoved(position);
            database.getReference().child("categories").child(categoryKey).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context,"Item deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Failed items deleted",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
