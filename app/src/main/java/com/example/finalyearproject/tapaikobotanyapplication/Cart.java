package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.MyCartAdapter;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.MyCartModel;
import com.example.finalyearproject.tapaikobotanyapplication.HelperClasses.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    int overAllTotalAmount;
    TextView TotalPrice;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter CartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        //get data from my cart activity
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mmessagereceiver, new IntentFilter("MyTotalAmount"));
        TotalPrice = findViewById(R.id.TotalPrice_cart);
        recyclerView = findViewById(R.id.cart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        CartAdapter = new MyCartAdapter(this,cartModelList);
        recyclerView.setAdapter(CartAdapter);
        buyBtn = findViewById(R.id.buy_now_cart);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){

                        String documentId = doc.getId();
                        MyCartModel cartModel = doc.toObject(MyCartModel.class);
                        cartModel.setDocumentId(documentId);

                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        cartModelList.add(myCartModel);
                        CartAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cart.this,AddressActivity.class));
            }
        });

    }
    public BroadcastReceiver mmessagereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("TotalPrice", 0);
            TotalPrice.setText("Total Amount: "+ "Rs" + totalBill);
        }
    };

}