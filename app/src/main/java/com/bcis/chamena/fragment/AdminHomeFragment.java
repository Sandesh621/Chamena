package com.bcis.chamena.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bcis.chamena.adapter.AdminProductItemAdapter;
import com.bcis.chamena.common.FetchProductsDetailsModel;
import com.bcis.chamena.common.ManipulateProductDetails;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.AdminHomeLayoutBinding;
import com.bcis.chamena.databinding.GreetLayoutBinding;
import com.bcis.chamena.model.Product;

import java.util.List;


public class AdminHomeFragment extends Fragment {
    AdminHomeLayoutBinding binding;
    GreetLayoutBinding greetLayoutBinding;
    AdminProductItemAdapter adapter;
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
        binding.root.addView(greetLayoutBinding.getRoot());
        fetchDataAndBind();
    }
    void fetchDataAndBind(){
        FetchProductsDetailsModel fetchProductsDetailsModel = new FetchProductsDetailsModel();
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchProductsDetailsModel.fetchAll();
            }
        });
        fetchProductsDetailsModel.fetchAll();
        RecyclerView recyclerView= new RecyclerView(getContext());
        fetchProductsDetailsModel._products.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                binding.swipeRefresh.setRefreshing(false);
                adapter=new AdminProductItemAdapter(products,getActivity());
                recyclerView.setAdapter(adapter);
                adapter.onDeleteProductClickListener(new AdminProductItemAdapter.OnDeleteProductClickListener() {
                    @Override
                    public void onClicked(Product product) {
                        AlertDeleteProductFragment alertDeleteProductFragment= new AlertDeleteProductFragment();
                        alertDeleteProductFragment.addOnDeleteConfirmListener(new AlertDeleteProductFragment.OnDeleteProductConfirmListener() {
                            @Override
                            public void confirm() {
                                ManipulateProductDetails manipulateProductDetails =new ManipulateProductDetails();
                                manipulateProductDetails.delete(product.docId, product.imagePath);
                                manipulateProductDetails.addOnProductDeleteListener(new ManipulateProductDetails.OnProductDeleteListener() {
                                    @Override
                                    public void onSuccess() {
                                        fetchProductsDetailsModel.fetchAll();
                                        alertDeleteProductFragment.dismiss();
                                        adapter.dataSetChanged();
                                        Toast.makeText(getContext(),"Deleted Product Successfully",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });
                        alertDeleteProductFragment.show(getParentFragmentManager(),"DeleteFragment");


                    }
                });
            }
        });
        LinearLayoutManager manager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecyclerViewMargin(25,1000));
        binding.root.addView(recyclerView);
        fetchProductsDetailsModel._status.observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status==Status.COMPLETED){
                    binding.progress.setVisibility(View.GONE);
                    binding.swipeRefresh.setRefreshing(false);
                }else if(status==Status.FAILURE){
                    binding.progress.setVisibility(View.GONE);
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
