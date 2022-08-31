package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static int splash = 5000;
    //Declaring Variables

    Animation TopAnimation, BottomAnimation;
    ImageView Logo;
    TextView Logoname, Logodesc;

    SharedPreferences boarddPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        TopAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        BottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        Logo = findViewById(R.id.Logo_App);
        Logoname = findViewById(R.id.Logo_name);
        Logodesc = findViewById(R.id.Logo_desc);

        Logo.setAnimation(TopAnimation);
        Logoname.setAnimation(BottomAnimation);
        Logodesc.setAnimation(BottomAnimation);
        mAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boarddPreferences = getSharedPreferences("boardingscreen",MODE_PRIVATE);

                boolean isFirstTime = boarddPreferences.getBoolean("firsttime", true);

                if (isFirstTime) {
                    SharedPreferences.Editor editor = boarddPreferences.edit();
                    editor.putBoolean("firsttime", false);
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), Boarding_screen.class));
                    finish();

                } else if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), Option.class));
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        },splash);
        }
}