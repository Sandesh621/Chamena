package com.bcis.chamena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bcis.chamena.cart.CartActivity;
import com.bcis.chamena.common.FetchUserDetailsModel;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.ActivityMainBinding;
import com.bcis.chamena.fragment.AdminAddProductFragment;
import com.bcis.chamena.fragment.AdminHomeFragment;
import com.bcis.chamena.fragment.UserHomeFragment;
import com.bcis.chamena.login.LoginActivity;
import com.bcis.chamena.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

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

        if(FirebaseAuth.getInstance().getUid()==null){
            changeFragment(new UserHomeFragment());
        }else{
            if(isAdmin()){
                changeFragment(new AdminHomeFragment());
            }else{
                changeFragment(new UserHomeFragment());
            }
        }
    }
    boolean isAdmin(){
        User user = new UserPref(null,getApplicationContext()).getUserPref();
        if(user==null)return false;
        return user.isAdmin;
    }

    @Override
    protected void onStart() {
        super.onStart();
        removeMenu();
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
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
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
        switch (id){
            case R.id.add_product:
                changeFragment(new AdminAddProductFragment());
                break;
            case R.id.logout:
                logout();
                break;
            default:
                switchHomeFragment();
        }
        binding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void switchHomeFragment(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&isAdmin()){
            changeFragment(new AdminHomeFragment());
        }else{
            changeFragment(new UserHomeFragment());
        }
    }
    void removeMenu(){
        Menu menu = binding.navigationView.getMenu();
        //Check user is login or not
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            menu.removeItem(R.id.add_product);
            menu.removeItem(R.id.orders);
            menu.removeItem(R.id.myorder);
            menu.removeItem(R.id.logout);
        }
        if(!isAdmin()){
            menu.removeItem(R.id.orders);
            menu.removeItem(R.id.add_product);
        }else{
            menu.removeItem(R.id.myorder);
        }
    }
    void logout(){
        if(FirebaseAuth.getInstance().getUid()==null)return;
        new UserPref(null,getApplicationContext()).clearPref();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();
    }
}



