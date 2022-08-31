package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private String username;

    TextInputLayout prousername, proemail, prophonenumber, propassword;
    TextView profileusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);




        //hooks
        profileusername = findViewById(R.id.user_name);
        prousername = findViewById(R.id.profile_Username);
        proemail = findViewById(R.id.emailprofile);
        prophonenumber = findViewById(R.id.phonenoprofile);
        propassword = findViewById(R.id.passwordprofile);




        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper = snapshot.getValue(UserHelper.class);

                String profile_username = userHelper.username;
                String profile_email = userHelper.email;
                String profile_phoneno = userHelper.phoneno;
                String profile_password = userHelper.password;

                if (userHelper != null){
                    profileusername.setText(profile_username);
                    prousername.getEditText().setText(profile_username);
                    proemail.getEditText().setText(profile_email);
                    prophonenumber.getEditText().setText(profile_phoneno);
                    propassword.getEditText().setText(profile_password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        //show all data

    }

//    private void showAllUserData() {
//        Intent intent = getIntent();
//        String profile_username = intent.getStringExtra("username");
//        String profile_email = intent.getStringExtra("email");
//        String profile_phoneno = intent.getStringExtra("phoneno");
//        String profile_password = intent.getStringExtra("password");
//
//
//        profileusername.setText(profile_username);
//        prousername.getEditText().setText(profile_username);
//        proemail.getEditText().setText(profile_email);
//        prophonenumber.getEditText().setText(profile_phoneno);
//        propassword.getEditText().setText(profile_password);
//
//
//    }


    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Bye Bye! See You Again", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UserProfile.this,SplashScreen.class));
        finish();
    }


    public void cartopen(View view) {
        startActivity(new Intent(UserProfile.this,Cart.class));
    }
}