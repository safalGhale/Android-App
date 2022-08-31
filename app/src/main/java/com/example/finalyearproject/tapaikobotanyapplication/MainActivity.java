package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout signusername, signemail, signphone, signpassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //hooks to all xml elements in activity_main
        signusername = findViewById(R.id.name);
        signemail = findViewById(R.id.email);
        signphone = findViewById(R.id.contact);
        signpassword = findViewById(R.id.password);
    }

    private  Boolean validateusername(){
        String val = signusername.getEditText().getText().toString();

        if (val.isEmpty()){
            signusername.setError("Username field cannot be empty");
            return false;
        }
        else if (val.length()>=20){
            signusername.setError("Username too long");
            return false;
        }
        else {
            signusername.setError(null);
            signusername.setErrorEnabled(false);
            return true;
        }
    }

    private  Boolean validateemail(){
        String val = signemail.getEditText().getText().toString();
        String emailval = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            signemail.setError("Email field cannot be empty");
            return false;
        }
        else if (!val.matches(emailval)) {
            signemail.setError("Invalid Email Address");
            return false;
        }
        else {
            signemail.setError(null);
            signemail.setErrorEnabled(false);
            return true;
        }
    }

    private  Boolean validatephoneno(){
        String val = signphone.getEditText().getText().toString();

        if (val.isEmpty()){
            signphone.setError("Phone Number field cannot be empty");
            return false;
        }
        else {
            signphone.setError(null);
            signphone.setErrorEnabled(false);
            return true;
        }
    }

    private  Boolean validatepassword(){
        String val = signpassword.getEditText().getText().toString();
        String passwordval = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()){
            signpassword.setError("Password field cannot be empty");
            return false;
        }
        else if (val.length()<=4){
            signpassword.setError("Password too short / Must contain atleast 6 character");
            return false;
        }
        else if (!val.matches(passwordval)) {
            signpassword.setError("Password too weak / Password should have " +
                    "1 uppercase letter," +
                    "1 lowercase letter," +
                    "1 special character and " +
                    "1 numeric character");
            return false;
        }
        else {
            signpassword.setError(null);
            signpassword.setErrorEnabled(false);
            return true;
        }
    }






    public void SIGNUP(View view) {

        if(!validateusername() | !validateemail() | !validatephoneno() | !validatepassword()){

            return;
        }

//        rootNode = FirebaseDatabase.getInstance();
//        reference = rootNode.getReference("User");

        String username = signusername.getEditText().getText().toString();
        String email = signemail.getEditText().getText().toString();
        String phoneno = signphone.getEditText().getText().toString();
        String password = signpassword.getEditText().getText().toString();
//        Toast.makeText(this, "Regesteration Successful", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserHelper userHelper = new UserHelper(username, email,phoneno, password);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Regesteration Successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Regesteration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(MainActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //Storing data in firebase
//        UserHelper helperclass = new UserHelper(username,email,phoneno,password);

//        reference.child(username).setValue(helperclass);

//        Toast.makeText(this, "Regesteration Successful", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(MainActivity.this,Login.class));
        finish();

    }


}