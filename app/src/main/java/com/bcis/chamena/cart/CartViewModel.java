package com.bcis.chamena.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CartViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Cart>> carts = new MutableLiveData<>();
    public LiveData<ArrayList<Cart>> _carts = carts;
    private MutableLiveData<Float> totalPrice = new MutableLiveData<>();
    public LiveData<Float> _totalPrice = totalPrice;
    public void getCartItems(){
        carts.setValue(CartModel.carts);
    }
    public void totalPrice(){
        float total = 0.0F;
        for (Cart cart:_carts.getValue()){
            float price = (float) (cart.productPrice*cart.orderItems);
            total+=price;
        }
        totalPrice.setValue(total);
    }




}
