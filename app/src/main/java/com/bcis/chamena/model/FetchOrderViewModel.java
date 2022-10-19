package com.bcis.chamena.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bcis.chamena.common.FetchOrdersDetails;
import com.bcis.chamena.common.Status;

import java.util.ArrayList;
import java.util.List;

public class FetchOrderViewModel {

    private MutableLiveData<List<OrderHelper>> orders =new MutableLiveData<>();
    public LiveData<List<OrderHelper>> _orders = orders;
    private MutableLiveData<Status> status = new MutableLiveData<>(Status.IDLE);
    public LiveData<Status> _status = status;

   public void fetch(){
        status.setValue(Status.PROGRESS);
        FetchOrdersDetails ordersDetails = new FetchOrdersDetails();
        ordersDetails.fetchOrders();
        ordersDetails.OnAddFetchOrderListener(new FetchOrdersDetails.FetchOrderListener() {
            @Override
            public void onFetchSuccess(List<OrderHelper> orderHelpers) {
                orders.setValue(orderHelpers);
                status.setValue(Status.COMPLETED);
            }

            @Override
            public void onFetchFailure(Exception e) {
                Log.e("ErrorFetchingOrder",e.getMessage().toString());
                status.setValue(Status.FAILURE);
            }
        });
    }

}
