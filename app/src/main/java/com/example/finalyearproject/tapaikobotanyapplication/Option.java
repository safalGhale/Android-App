package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Option extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    public void profile(View view) {
        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
        startActivity(intent);
    }

    public void cart(View view) {
        startActivity(new Intent(Option.this,Cart.class));
    }

    public void camera(View view) {
        startActivity(new Intent(Option.this,Camera.class));
    }

    public void home(View view) {
        startActivity(new Intent(Option.this,Home.class));
    }
}