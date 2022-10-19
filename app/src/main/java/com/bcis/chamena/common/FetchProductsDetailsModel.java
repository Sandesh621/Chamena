package com.bcis.chamena.common;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bcis.chamena.model.Product;
import com.bcis.chamena.model.ProductSecond;

import java.util.ArrayList;
import java.util.List;

public class FetchProductsDetailsModel extends ViewModel {

    private MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private MutableLiveData<Status> status = new MutableLiveData<>();
    public LiveData<List<Product>> _products =products;
    public LiveData<Status> _status = status;
    private MutableLiveData<List<ProductSecond>> productsWithCategory = new MutableLiveData<List<ProductSecond>>();
    public LiveData<List<ProductSecond>> _productsWithCategory = productsWithCategory;
    public void fetchAllProductWithGroupingCategory(){
        status.setValue(Status.PROGRESS);
        FetchProductsDetails fetchProductsDetails=new FetchProductsDetails();
        fetchProductsDetails.fetchAll().addOnFetchListener(new FetchProductsDetails.AddOnFetchListener() {
            @Override
            public void onSuccess(List<Product> products) {
                List<ProductSecond> productSeconds = new ArrayList<>();
                List<String> category = new ArrayList<>();
                for(Product product1:products) {
                    if(!category.contains(product1.category)) {
                        category.add(product1.category);
                    }
                }
                for(String cate:category) {
                    List<Product> newProduct = new ArrayList<>();
                    for (Product product:products) {
                        if (product.category.toLowerCase().equals(cate.toLowerCase())) {
                            newProduct.add(product);
                        }
                    }
                    productSeconds.add(new ProductSecond(cate,newProduct));
                }
                productsWithCategory.setValue(productSeconds);
                status.setValue(Status.COMPLETED);
            }

            @Override
            public void onFailure(Exception e) {
                status.setValue(Status.FAILURE);
            }
        });

    }
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
