package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Forget_Password extends AppCompatActivity {

    TextInputLayout forgetemail;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        mAuth = FirebaseAuth.getInstance();

        forgetemail =  findViewById(R.id.forgot_email);
    }

    public void Send(View view) {
        String email = forgetemail.getEditText().getText().toString();
        if (email.isEmpty()){
            forgetemail.setError("Email is Required");
            forgetemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgetemail.setError("Enter a Valid Email Address");
            forgetemail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Forget_Password.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Forget_Password.this, "Something went wrong. Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}