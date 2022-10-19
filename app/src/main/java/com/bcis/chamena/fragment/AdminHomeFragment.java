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

import com.bcis.chamena.common.FetchProductsDetailsModel;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.AdminHomeLayoutBinding;
import com.bcis.chamena.databinding.GreetLayoutBinding;
import com.bcis.chamena.model.Product;

import java.util.List;


public class AdminHomeFragment extends Fragment {
    AdminHomeLayoutBinding binding;
    GreetLayoutBinding greetLayoutBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= AdminHomeLayoutBinding.inflate(getLayoutInflater());
        greetLayoutBinding = GreetLayoutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        greetLayoutBinding.name.setText(new UserPref(null,getContext()).getUserPref().fullName);
        binding.getRoot().addView(greetLayoutBinding.getRoot());
        fetchDataAndBind();
    }
    void fetchDataAndBind(){
        FetchProductsDetailsModel fetchProductsDetailsModel = new FetchProductsDetailsModel();
        fetchProductsDetailsModel._products.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.e("Products",products.toString());
            }
        });
        fetchProductsDetailsModel._status.observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
