package com.bcis.chamena.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bcis.chamena.databinding.AlertDeleteProductDialogBinding;

public class AlertDeleteProductFragment extends DialogFragment {
    AlertDeleteProductDialogBinding binding;
   public interface OnDeleteProductConfirmListener{
        void confirm();
    }
    OnDeleteProductConfirmListener listener;
   public  void addOnDeleteConfirmListener(OnDeleteProductConfirmListener listener){
       this.listener=listener;
   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = AlertDeleteProductDialogBinding.inflate(getLayoutInflater());
       binding.cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dismiss();
           }
       });
       binding.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                listener.confirm();
           }
       });
        return binding.getRoot();
    }
}
