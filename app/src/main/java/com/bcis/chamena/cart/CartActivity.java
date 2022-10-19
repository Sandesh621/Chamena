package com.bcis.chamena.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bcis.chamena.R;
import com.bcis.chamena.adapter.CartAdapter;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.databinding.ActivityCartBinding;
import com.bcis.chamena.databinding.EmptyCartBinding;
import com.bcis.chamena.databinding.UnauthenticateSuggestionLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
        UnauthenticateSuggestionLayoutBinding unauthenticateSuggestionLayoutBinding;
        EmptyCartBinding emptyCartBinding;
        ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unauthenticateSuggestionLayoutBinding = UnauthenticateSuggestionLayoutBinding.inflate(getLayoutInflater());
        emptyCartBinding = EmptyCartBinding.inflate(getLayoutInflater());
        showNotingInCart();
        renderCartData();
        showIfNotAuth();
        hideProgress();
    }
    void hideProgress(){
        binding.progress.setVisibility(View.GONE);
    }
    void showIfNotAuth(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            binding.root.addView(unauthenticateSuggestionLayoutBinding.getRoot());
        }
    }
    void showNotingInCart(){
        if(CartModel.carts.size()==0){
            binding.totalContainer.setVisibility(View.GONE);
            binding.root.addView(emptyCartBinding.getRoot());
        }else{
            binding.root.removeView(emptyCartBinding.getRoot());
        }
    }
    void renderCartData(){
        RecyclerView recyclerView = binding.cartRecyclerView;
        recyclerView.setHasFixedSize(true);
        CartViewModel cartViewModel = new CartViewModel();
        cartViewModel.getCartItems();

        cartViewModel._carts.observe(this, new Observer<ArrayList<Cart>>() {
            @Override
            public void onChanged(ArrayList<Cart> carts) {
                CartAdapter adapter =new CartAdapter(CartModel.carts,getApplicationContext());
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        });
        recyclerView.addItemDecoration(new RecyclerViewMargin(7,1));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}