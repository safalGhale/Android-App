package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.AddressAdapter;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.AddressModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.NewProductModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ProductsModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ShowAllActivity;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detaimimg;
    TextView rating, name, description, price, quantity;
    Button addtocart, buynow;
    ImageView additem, removeitem;

    int totalQuantity = 1;
    int totalPrice = 0;

    //New Product
    NewProductModel newProductModel = null;

    //Products
    ProductsModel productsModel = null;

    //show all products
    ShowAllModel showAllModel = null;


    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductModel){
            newProductModel = (NewProductModel) obj;
        }else if (obj instanceof ProductsModel){
            productsModel = (ProductsModel) obj;
        }else if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detaimimg = findViewById(R.id.detailed_img);
        rating = findViewById(R.id.detailed_rating);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        quantity = findViewById(R.id.detail_adding);

        addtocart = findViewById(R.id.detail_add_to_cart);
        buynow = findViewById(R.id.detail_Buy_now);

        additem = findViewById(R.id.detailed_add_item);
        removeitem = findViewById(R.id.detailed_remove_item);

        //New Product
        if (newProductModel != null){
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detaimimg);
            name.setText(newProductModel.getName());
            rating.setText(newProductModel.getRating());
            description.setText(newProductModel.getDescription());
            price.setText(String.valueOf(newProductModel.getPrice()));
            name.setText(newProductModel.getName());

            totalPrice = newProductModel.getPrice() * totalQuantity;
        }
        //Products
        if (productsModel != null){
            Glide.with(getApplicationContext()).load(productsModel.getImg_url()).into(detaimimg);
            name.setText(productsModel.getName());
            rating.setText(productsModel.getRating());
            description.setText(productsModel.getDescription());
            price.setText(String.valueOf(productsModel.getPrice()));
            name.setText(productsModel.getName());

            totalPrice = productsModel.getPrice() * totalQuantity;
        }
        //show all Products
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detaimimg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }
        //Buy Now
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

                if (newProductModel != null){
                    intent.putExtra("item",newProductModel);
                }
                if (productsModel !=null){
                    intent.putExtra("item",productsModel);
                }
                if (showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });

        //add to cart
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
            }
        });

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductModel != null) {

                        totalPrice = newProductModel.getPrice() * totalQuantity;
                    }
                    if (productsModel != null){
                        totalPrice = productsModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                }

            }
        });

        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));

                }
            }
        });


    }



    private void addtocart() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        }

}