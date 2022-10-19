package com.bcis.chamena.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.adapter.OrdersItemAdapter;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.databinding.AdminOrderFragmentBinding;
import com.bcis.chamena.databinding.OrderbyNameBinding;
import com.bcis.chamena.model.FetchOrderViewModel;
import com.bcis.chamena.model.OrderHelper;

import java.util.List;

public class AdminOrdersFragment extends Fragment {
    AdminOrderFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AdminOrderFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FetchOrderViewModel fetchOrderViewModel = new FetchOrderViewModel();
        fetchOrderViewModel.fetch();
        fetchOrderViewModel._orders.observe(getViewLifecycleOwner(), new Observer<List<OrderHelper>>() {
            @Override
            public void onChanged(List<OrderHelper> orderHelpers) {
               for (OrderHelper orderHelper:orderHelpers){
                   OrderbyNameBinding orderbyNameBinding = OrderbyNameBinding.inflate(getLayoutInflater());
                   orderbyNameBinding.name.setText(orderHelper.orders.get(0).fullName);
                   binding.root.addView(orderbyNameBinding.getRoot());
                   RecyclerView recyclerView = new RecyclerView(requireContext());
                   OrdersItemAdapter adapter = new OrdersItemAdapter(orderHelper.orders,requireContext());
                   recyclerView.setHasFixedSize(true);
                   recyclerView.setAdapter(adapter);
                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                   recyclerView.setLayoutManager(linearLayoutManager);
                   recyclerView.addItemDecoration(new RecyclerViewMargin(5,1));
                   binding.root.addView(recyclerView);
               }
            }
        });

        fetchOrderViewModel._status.observe(getViewLifecycleOwner(), new Observer<Status>() {
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
