package com.bcis.chamena.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.R;
import com.bcis.chamena.common.Setting;
import com.bcis.chamena.common.SettingPref;
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
    private  OnDeleteProductClickListener listener;
    public interface OnDeleteProductClickListener{
        void onClicked(Product product);
    }
    public void onDeleteProductClickListener(OnDeleteProductClickListener listener){
        this.listener=listener;
    }
    public void dataSetChanged(){
        notifyDataSetChanged();
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
        Setting setting = new SettingPref(activity).getSettingPref();
        if(setting!=null){
            holder.binding.price.setText(setting.currencyCode.isEmpty()?"Rs ":"$ "+items.get(position).price.toString());
        }else{
            holder.binding.price.setText("Rs "+items.get(position).price.toString());
        }

        holder.binding.productname.setText(items.get(position).productName);
        int pos = position;
        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(items.get(pos));
            }
        });
        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
