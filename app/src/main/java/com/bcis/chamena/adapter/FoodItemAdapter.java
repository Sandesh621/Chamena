package com.bcis.chamena.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.R;
import com.bcis.chamena.databinding.FoodItemBinding;

import java.util.ArrayList;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {
    ArrayList<String> items;

    public FoodItemAdapter(ArrayList<String> items){
      this.items=items;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding binding= FoodItemBinding.bind(View.inflate(parent.getContext(), R.layout.food_item,null));
        return new FoodItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
       // holder.binding.textview.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder{
        FoodItemBinding binding;
        public FoodItemViewHolder(@NonNull FoodItemBinding itemView) {
            super(itemView.getRoot());
           binding=itemView;
        }
    }
    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
