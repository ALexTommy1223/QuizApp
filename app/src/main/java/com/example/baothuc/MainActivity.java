package com.example.baothuc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.baothuc.databinding.ActivityMainBinding;
import com.example.baothuc.userui.UserFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
//
//        database=FirebaseDatabase.getInstance();
//        storage=FirebaseStorage.getInstance();
//        FirebaseApp.initializeApp(this);
        replace(new AdminFragment());
        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                if(tab1.getId()==R.id.user){
                    replace(new UserFragment());
                }
                else if(tab1.getId()==R.id.admin){
                    replace(new AdminFragment());
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
    private void replace(Fragment fragment){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

}