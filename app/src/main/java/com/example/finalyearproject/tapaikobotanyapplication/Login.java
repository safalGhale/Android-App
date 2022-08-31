package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextInputLayout loginemail, loginpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginemail = findViewById(R.id.log_email);
        loginpassword = findViewById(R.id.log_password);

        mAuth = FirebaseAuth.getInstance();
    }

    private Boolean validateemail() {
        String val = loginemail.getEditText().getText().toString();
        String emailval = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            loginemail.setError("Email field cannot be empty");
            return false;
        } else if (!val.matches(emailval)) {
            loginemail.setError("Invalid Email Address");
            return false;
        } else {
            loginemail.setError(null);
            loginemail.setErrorEnabled(false);
            return true;
        }
    }




    private Boolean validatepassword() {
        String val = loginpassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            loginpassword.setError("Password field cannot be empty");
            return false;
        } else {
            loginpassword.setError(null);
            loginpassword.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        final String userenteredemail = loginemail.getEditText().getText().toString().trim();
        final String userenteredpassword = loginpassword.getEditText().getText().toString().trim();
        if (validateemail() && validatepassword()){
            mAuth.signInWithEmailAndPassword(userenteredemail, userenteredpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Option.class));
                    }else {
                        Toast.makeText(Login.this, "Logged Error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //startActivity(new Intent(Login.this,UserProfile.class));

    }

//    private void isUser() {
//        final String userenteredusername = loginemail.getEditText().getText().toString().trim();
//        final String userenteredpassword = loginpassword.getEditText().getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//
//        Query checkuser = reference.orderByChild("username").equalTo(userenteredusername);
//
//        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//
//                    loginusername.setError(null);
//                    loginusername.setErrorEnabled(false);
//
//                    String passwordfromDB = snapshot.child(userenteredusername).child("password").getValue(String.class);
//                    if (passwordfromDB.equals(userenteredpassword)){
//
//                        loginusername.setError(null);
//                        loginusername.setErrorEnabled(false);
//
//                        String usernamefromDB = snapshot.child(userenteredusername).child("username").getValue(String.class);
//                        String emailfromDB = snapshot.child(userenteredusername).child("email").getValue(String.class);
//                        String phonenofromDB = snapshot.child(userenteredusername).child("phoneno").getValue(String.class);
//
//                        Intent intent = new Intent(getApplicationContext(),Option.class);
//                        intent.putExtra("username",usernamefromDB);
//                        intent.putExtra("email",emailfromDB);
//                        intent.putExtra("phoneno",phonenofromDB);
//                        intent.putExtra("password",passwordfromDB);
//
//                        startActivity(intent);
//                    }
//                    else{
//                        loginpassword.setError("Wrong Password");
//                        loginpassword.requestFocus();
//                    }
//                }
//                else{
//                    loginusername.setError("Nosuch user exists");
//                    loginusername.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    public void Forget(View view) {
        startActivity(new Intent(Login.this,Forget_Password.class));
    }

    public void Register(View view) {
        startActivity(new Intent(Login.this,MainActivity.class));
    }
}