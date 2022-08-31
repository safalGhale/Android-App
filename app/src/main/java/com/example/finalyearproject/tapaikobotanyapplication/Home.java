package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.SurfaceControl;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.CategorAdapter;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.CategoryModels;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    Fragment homefragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homefragment = new HomeFragment();
        LoadFragment(homefragment);
    }

    private void LoadFragment(Fragment homefragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homefragment);
        transaction.commit();
    }


}