package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.coursework.Fragment.AddFragment;
import com.example.coursework.Fragment.HomeFragment;
import com.example.coursework.Fragment.SearchFragment;
import com.example.coursework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set the default item to home
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        // Set the default fragment to home
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.add){
                replaceFragment(new AddFragment());
            } else if (id == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (id == R.id.search){
                replaceFragment(new SearchFragment());
            }
            return true;
        });
    }

    public void selectHomeFragment() {
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}