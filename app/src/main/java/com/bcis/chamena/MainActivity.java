package com.bcis.chamena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.bcis.chamena.fragment.AdminAddProductFragment;
import com.bcis.chamena.fragment.AdminHomeFragment;
import com.bcis.chamena.fragment.UserHomeFragment;
import com.bcis.chamena.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;

    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       setUpToolbar();
       setUpDrawer();

       //Todo: Dummy
        binding.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getUid()==null)return;
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getUid()==null){
            changeFragment(new UserHomeFragment());
        }else{
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            if(email.equals("computerstha12@gmail.com")||email.equals("chamena@gmail.com")){
                changeFragment(new AdminHomeFragment());
            }else{
                changeFragment(new UserHomeFragment());
            }

        }
    }

    void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    void setUpToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void setUpDrawer(){
        binding.navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawer,binding.toolbar, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        binding.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chamena_navbar,menu);
        if(FirebaseAuth.getInstance().getUid()!=null){
            MenuItem item = menu.findItem(R.id.user_login);
            item.setVisible(false);
        }
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
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.add_product){
            changeFragment(new AdminAddProductFragment());
        }
        if(id==R.id.home){
            changeFragment(new AdminHomeFragment());
        }
        binding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



