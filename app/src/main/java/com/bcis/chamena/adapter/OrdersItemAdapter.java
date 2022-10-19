package com.bcis.chamena.adapter;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.R;
import com.bcis.chamena.cart.Cart;
import com.bcis.chamena.cart.CartModel;
import com.bcis.chamena.databinding.FoodItemBinding;
import com.bcis.chamena.databinding.OrderlistBinding;
import com.bcis.chamena.model.Order;
import com.bcis.chamena.model.OrderHelper;
import com.bcis.chamena.model.Product;
import com.bumptech.glide.Glide;

import java.util.List;

public class OrdersItemAdapter extends RecyclerView.Adapter<OrdersItemAdapter.FoodItemViewHolder> {

    List<Order> orders;
Context context;
    public OrdersItemAdapter(List<Order> orders,Context context) {
        this.orders = orders;
        this.context=context;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderlistBinding binding= OrderlistBinding.bind(View.inflate(parent.getContext(), R.layout.orderlist,null));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                MATCH_PARENT, WRAP_CONTENT);
        binding.getRoot().setLayoutParams(params);
        return new FoodItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        holder.binding.price.setText("Rs "+orders.get(position).price.toString());
        holder.binding.quantity.setText("Quantity:"+orders.get(position).quantity.toString());
        holder.binding.productname.setText(orders.get(position).productName);
        if(orders.get(position).imageUrl!=null){
            Glide.with(context).load(orders.get(position).imageUrl).into(holder.binding.image);
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder{
        OrderlistBinding binding;
        public FoodItemViewHolder(@NonNull OrderlistBinding itemView) {
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
