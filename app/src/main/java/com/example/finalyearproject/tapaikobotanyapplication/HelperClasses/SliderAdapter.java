package com.example.finalyearproject.tapaikobotanyapplication.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.finalyearproject.tapaikobotanyapplication.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    int images[] = {
            R.drawable.capture,
            R.drawable.search_plant,
            R.drawable.buy_plants,
            R.drawable.know_about_plants
    };

    int headings[] = {
            R.string.first_silde_title,
            R.string.sec_silde_title,
            R.string.third_silde_title,
            R.string.forth_silde_title
    };

    int descriptions[] = {
            R.string.capture_description,
            R.string.image_search_description,
            R.string.buy_plant_description,
            R.string.know_plant_description
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);


        ImageView imageView = view.findViewById(R.id.capture_image);
        TextView heading = view.findViewById(R.id.capture_heading);
        TextView desc = view.findViewById(R.id.capture_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
