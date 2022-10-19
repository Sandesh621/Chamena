package com.bcis.chamena.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bcis.chamena.R;
import com.bcis.chamena.adapter.FoodItemAdapter;
import com.bcis.chamena.common.FetchProductsDetailsModel;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.FoodItemLayoutBinding;
import com.bcis.chamena.databinding.GreetLayoutBinding;
import com.bcis.chamena.databinding.UserHomeLayoutBinding;
import com.bcis.chamena.model.Product;
import com.bcis.chamena.model.ProductSecond;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    UserHomeLayoutBinding binding;
    GreetLayoutBinding greetLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserHomeLayoutBinding.inflate(getLayoutInflater());
        greetLayoutBinding = GreetLayoutBinding.inflate(getLayoutInflater());
        bindView();
        return binding.getRoot();
    }

    void bindView(){
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            greetLayoutBinding.name.setText(new UserPref(null,getContext()).getUserPref().fullName);
            binding.root.addView(greetLayoutBinding.getRoot());
        }
        FetchProductsDetailsModel fetchProductsDetailsModel = new FetchProductsDetailsModel();
        fetchProductsDetailsModel.fetchAllProductWithGroupingCategory();


        fetchProductsDetailsModel._productsWithCategory.observe(getViewLifecycleOwner(), new Observer<List<ProductSecond>>() {
            @Override
            public void onChanged(List<ProductSecond> productSeconds) {
                for(ProductSecond productSecond:productSeconds){
                    FoodItemLayoutBinding foodItemBinding = FoodItemLayoutBinding.inflate(getLayoutInflater());
                    foodItemBinding.category.setText(productSecond.category);
//                    foodItemBinding.image.setImageDrawable(getContext().getDrawable(R.drawable.burger));
                    RecyclerView foodRecyclerView = foodItemBinding.foodItemList;
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    foodRecyclerView.setLayoutManager(manager);
                    FoodItemAdapter adapter = new FoodItemAdapter(productSecond.products,requireContext());
                    foodRecyclerView.setHasFixedSize(true);
                    foodRecyclerView.addItemDecoration(new RecyclerViewMargin(25,productSecond.products.size()));
                    foodRecyclerView.setAdapter(adapter);
                    binding.root.addView(foodItemBinding.getRoot());
                }
            }
        });

        fetchProductsDetailsModel._status.observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch (status){
                    case COMPLETED:
                    case FAILURE:
                        binding.progress.setVisibility(View.GONE);
                }
            }
        });

    }




}
