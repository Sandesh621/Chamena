package com.bcis.chamena.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bcis.chamena.R;
import com.bcis.chamena.adapter.CartAdapter;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.databinding.ActivityCartBinding;
import com.bcis.chamena.databinding.EmptyCartBinding;
import com.bcis.chamena.databinding.UnauthenticateSuggestionLayoutBinding;
import com.bcis.chamena.login.LoginActivity;
import com.bcis.chamena.register.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
        UnauthenticateSuggestionLayoutBinding unauthenticateSuggestionLayoutBinding;
        EmptyCartBinding emptyCartBinding;
        ActivityCartBinding binding;
    CartViewModel cartViewModel = new CartViewModel();
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
        emptyCartBinding.shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
            }
        });
        unauthenticateSuggestionLayoutBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(LoginActivity.class);
            }
        });
        unauthenticateSuggestionLayoutBinding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(Register.class);
            }
        });
    }
    void navigate(Class xClass){
        Intent intent = new Intent(this,xClass);
        startActivity(intent);
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
        cartViewModel.getCartItems();
        cartViewModel.totalPrice();
        cartViewModel._totalPrice.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                binding.total.setText("Total Price: "+aFloat.toString());
            }
        });
        cartViewModel._carts.observe(this, new Observer<ArrayList<Cart>>() {
            @Override
            public void onChanged(ArrayList<Cart> carts) {
                if(carts.isEmpty()){
                    CartModel.clearAllData();
                    binding.totalContainer.setVisibility(View.GONE);
                }
                CartAdapter adapter =new CartAdapter(CartModel.carts,getApplicationContext());
                adapter.onCartManipulationListener(new CartAdapter.OnCartManipulateListener() {
                    @Override
                    public void onChange(Cart cart, boolean status) {
                        if(status){
                            CartModel.addToCart(cart);
                        }else{
                            CartModel.removeFromCart(cart);
                        }
                        cartViewModel.getCartItems();
                        cartViewModel.totalPrice();
                    }
                });
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        });
        recyclerView.addItemDecoration(new RecyclerViewMargin(7,1));
    }

    void checkout(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(getApplicationContext(),"Please login your account",Toast.LENGTH_LONG).show();
            return;
        }
        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                Number timestamp=System.currentTimeMillis();
                for (Cart cart:CartModel.carts){
                    CartCheckoutHelper helper = new CartCheckoutHelper(cart.docId,timestamp,cart.orderItems,cart.productPrice,userId);
                    DocumentReference reference = db.collection("orders").document();
                    transaction.set(reference,helper);
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Object>() {
            @Override
            public void onComplete(@NonNull Task<Object> task) {
                if(task.isSuccessful()){
                    CartModel.carts = new ArrayList<>();
                    cartViewModel.getCartItems();
                    CartModel.clearAllData();
                    showNotingInCart();
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
class CartCheckoutHelper{
  public   String productId;
    public  Number timestamp;
    public  int orderItems;
    public  Number price;
    public  String orderBy;

    public CartCheckoutHelper(String productId,Number timestamp, int orderItems, Number price, String orderBy) {
        this.productId = productId;
        this.timestamp = timestamp;
        this.orderItems = orderItems;
        this.price = price;
        this.orderBy = orderBy;
    }
}