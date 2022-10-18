package com.bcis.chamena.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bcis.chamena.databinding.AdminAddprdBinding;
import com.bcis.chamena.databinding.AdminHomeLayoutBinding;
import com.bcis.chamena.model.Product;
import com.bcis.chamena.uploader.FileUploader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.xml.transform.Result;


public class AdminAddProductFragment extends Fragment  {
    //Todo:Change this according to the xml file you have created
    AdminAddprdBinding binding;
   private ActivityResultLauncher<Intent> launcher;
   String imageUrl="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // Todo: Change AdminAddprdBinding to binding ClassName
        binding= AdminAddprdBinding.inflate(getLayoutInflater());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if(resultCode==RESULT_OK){
                            Uri uri = data.getData();
                            FileUploader uploader = new FileUploader();
                            uploader.upload(uri);
                            uploader.addOnFileUploaderListener(new FileUploader.FileUploaderInterface() {
                                @Override
                                public void onSuccess(String url) {
                                    imageUrl=url;
                                    checkSubmitButtonVisibility();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    imageUrl="";
                                    Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        return binding.getRoot();
    }
    void checkSubmitButtonVisibility(){
        if(!imageUrl.isEmpty()&& !binding.productname.getText().toString().isEmpty()&&!binding.productprice.getText().toString().isEmpty()
        && !binding.productcat.getText().toString().isEmpty()){
            binding.productsubmit.setVisibility(View.VISIBLE);
        }else{
            binding.productsubmit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.productname.addTextChangedListener(watcher());
        binding.productcat.addTextChangedListener(watcher());
        binding.productprice.addTextChangedListener(watcher());
        binding.selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imagePicker = new Intent(Intent.ACTION_PICK);
                imagePicker.setType("image/*");
               launcher.launch(imagePicker);
            }
        });
        binding.productsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = binding.productname.getText().toString();
                Number price = Float.parseFloat(binding.productprice.getText().toString());
                String category =binding.productcat.getText().toString();
                Product product = new Product(FirebaseAuth.getInstance().getCurrentUser().getUid(),productName,price,category,imageUrl);
                addData(product);
            }
        });

    }
    TextWatcher watcher(){
       return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkSubmitButtonVisibility();
            }
        };

    }
    void clear(){
        binding.productname.setText("");
        binding.productprice.setText("");
        binding.productcat.setText("");
        imageUrl="";
    }

    void addData(Product product){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("products").add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Added Product",Toast.LENGTH_LONG).show();
                            clear();
                        }else{
                            Toast.makeText(getContext(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
