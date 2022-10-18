package com.bcis.chamena.common;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bcis.chamena.model.Product;

import java.util.List;

public class FetchProductsDetailsModel extends ViewModel {

    private MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private MutableLiveData<Status> status = new MutableLiveData<>();
    public LiveData<List<Product>> _products =products;
    public LiveData<Status> _status = status;
   public void fetchAll(){
        status.setValue(Status.PROGRESS);
        FetchProductsDetails fetchProductsDetails=new FetchProductsDetails();
        fetchProductsDetails.fetchAll().addOnFetchListener(new FetchProductsDetails.AddOnFetchListener() {
            @Override
            public void onSuccess(List<Product> product) {
                products.setValue(product);
                status.setValue(Status.COMPLETED);
            }

            @Override
            public void onFailure(Exception e) {
                status.setValue(Status.FAILURE);
            }
        });
    }
}
