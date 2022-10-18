package com.bcis.chamena.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.R;
import com.bcis.chamena.databinding.FoodItemBinding;
import com.bcis.chamena.databinding.ProductItemBinding;
import com.bcis.chamena.model.Product;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdminProductItemAdapter extends RecyclerView.Adapter<AdminProductItemAdapter.FoodItemViewHolder> {
    List<Product> items;
    FragmentActivity activity;
    public AdminProductItemAdapter(List<Product> items, FragmentActivity activity){
      this.items=items;
      this.activity=activity;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding= ProductItemBinding.bind(View.inflate(parent.getContext(), R.layout.product_item,null));
        return new FoodItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        Glide.with(activity).load(items.get(position).productUrl).into(holder.binding.image);
        holder.binding.price.setText(items.get(position).price.toString());
        holder.binding.productname.setText(items.get(position).productName);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder{
        ProductItemBinding binding;
        public FoodItemViewHolder(@NonNull ProductItemBinding itemView) {
            super(itemView.getRoot());
           binding=itemView;
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
