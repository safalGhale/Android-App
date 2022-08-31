package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.SliderAdapter;

public class Boarding_screen extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;

    SliderAdapter sliderAdapter;
    TextView[] dot;
    Button getstarted;
    Animation animation;
    int currentpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boarding_screen);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dots = findViewById(R.id.dots);
        getstarted = findViewById(R.id.get_started);

        //Call adapter
        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }
    private  void addDots(int position){
        dot = new TextView[4];
        dots.removeAllViews();

        for(int i =0; i< dot.length; i++) {
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#8226;"));
            dot[i].setTextSize(35);

            dots.addView(dot[i]);
        }
        if(dot.length > 0){
            dot[position].setTextColor(getResources().getColor(R.color.Orange));
        }
    }


    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentpos = position;

            if (position == 0){
                getstarted.setVisibility(View.INVISIBLE);
            }
            else if (position == 1) {
                getstarted.setVisibility(View.INVISIBLE);
            }
            else if (position == 2) {
                getstarted.setVisibility(View.INVISIBLE);
            }
            else{
                animation = AnimationUtils.loadAnimation(Boarding_screen.this,R.anim.new_anim);
                getstarted.setAnimation(animation);
                getstarted.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void NEXT(View view) {
        viewPager.setCurrentItem(currentpos + 1);
    }

    public void GetStarted(View view) {
        startActivity(new Intent(this,Login.class) );
        finish();
    }

    public void SKIP(View view) {
        startActivity(new Intent(this,Login.class));
        finish();
    }
}
