package com.bcis.chamena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bcis.chamena.adapter.FoodItemAdapter;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.databinding.ActivityMainBinding;
import com.bcis.chamena.databinding.FoodItemBinding;
import com.bcis.chamena.databinding.FoodItemLayoutBinding;
import com.bcis.chamena.login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int  SPLASH_TIME=1500;
    ActivityMainBinding binding;
    ArrayList<Dummy> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
       loadData();
       bindView();

    }

    void loadData(){
                ArrayList<String> items = new ArrayList<String>();
        for(int i=0;i<10;i++){
            items.add("Item "+i);
        }
        data.add(new Dummy("Foods",items,R.drawable.burger));
        data.add(new Dummy("Drinks",items,R.drawable.cup));
    }
    void bindView(){

        for (Dummy item:data) {
            FoodItemLayoutBinding foodItemBinding = FoodItemLayoutBinding.inflate(getLayoutInflater());
            foodItemBinding.category.setText(item.category);
            foodItemBinding.image.setImageDrawable(getDrawable(item.icon));
            FoodItemAdapter adapter = new FoodItemAdapter(item.items);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView foodRecyclerView = foodItemBinding.foodItemList;
        foodRecyclerView.setLayoutManager(manager);
        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.addItemDecoration(new RecyclerViewMargin(25,item.items.size()));
        foodRecyclerView.setAdapter(adapter);
        binding.root.addView(foodItemBinding.getRoot());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chamena_navbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                Toast.makeText(this, "Nice", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}

class Dummy{
    String category;
    ArrayList<String> items;
    int icon;
    Dummy(String category,ArrayList<String> items,int icon){
        this.category=category;
        this.items=items;
        this.icon=icon;
    }
}

