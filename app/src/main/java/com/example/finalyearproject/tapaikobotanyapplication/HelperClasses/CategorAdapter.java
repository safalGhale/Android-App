package com.example.finalyearproject.tapaikobotanyapplication.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.tapaikobotanyapplication.R;

import java.util.List;

public class CategorAdapter extends RecyclerView.Adapter<CategorAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModels> list;

    public CategorAdapter(Context context, List<CategoryModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.catimg);
        holder.catname.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowAllActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catimg;
        TextView catname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catimg = itemView.findViewById(R.id.cat_img);
            catname = itemView.findViewById(R.id.cat_name);
        }
    }
}
