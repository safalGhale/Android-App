package com.example.finalyearproject.tapaikobotanyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.CategorAdapter;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.CategoryModels;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.NewProductAdaptor;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.NewProductModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ProductsAdapter;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ProductsModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ShowAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    TextView catshowall, allproductshowall, newproductshowall;

    LinearLayout linearLayout;

    ProgressDialog progressDialog;

    ImageSlider imageSlider;

    RecyclerView catrecyclerview, newProductRecyclerview, ProductRecyclerview;

    //Category
    CategorAdapter categorAdapter;
    List<CategoryModels> categoryModelsList;

    //New Product
    NewProductAdaptor newProductAdaptor;
    List<NewProductModel> newProductModelList;

    //Products All
    ProductsAdapter productsAdapter;
    List<ProductsModel> productsModelList;


    FirebaseFirestore db;


    public HomeFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getActivity());
        catrecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview =root.findViewById(R.id.new_product_rec);
        ProductRecyclerview = root.findViewById(R.id.popular_rec);


        catshowall = root.findViewById(R.id.category_see_all);
        allproductshowall = root.findViewById(R.id.popular_see_all);
        newproductshowall = root.findViewById(R.id.newProducts_see_all);

        catshowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        allproductshowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newproductshowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });


        imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        slideModels.add(new SlideModel(R.drawable.mango_plant_plantsguru_800x800,"Flat 60% Discount on two Mango plant", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.bael,"20% Discount on Bael plant", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.alstonia_scholaris,"20%  Discount alstonia scholaris plant", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.guava,"50 % Discount on Guava plant", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome to My Store");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //category
        catrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelsList = new ArrayList<>();
        categorAdapter = new CategorAdapter(getContext(),categoryModelsList);
        catrecyclerview.setAdapter(categorAdapter);

        db.collection("PlantCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModels categoryModels = document.toObject(CategoryModels.class);
                                categoryModelsList.add(categoryModels);
                                categorAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            }
                        } else {
                            Toast.makeText(getActivity(), "" +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //New Products
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductModelList = new ArrayList<>();
        newProductAdaptor = new NewProductAdaptor(getContext(),newProductModelList);
        newProductRecyclerview.setAdapter(newProductAdaptor);

        db.collection("NewProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewProductModel newProductModel = document.toObject(NewProductModel.class);
                                newProductModelList.add(newProductModel);
                                newProductAdaptor.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), "" +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //All Products
        ProductRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        productsModelList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(getContext(),productsModelList);
        ProductRecyclerview.setAdapter(productsAdapter);

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ProductsModel productsModel = document.toObject(ProductsModel.class);
                                productsModelList.add(productsModel);
                                productsAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), "" +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}