package com.example.finalyearproject.tapaikobotanyapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.tapaikobotanyapplication.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Camera extends AppCompatActivity {

    TextView textView;
    ImageView camimage;
    Button camerabtn, gallerybtn, identify;
    private Bitmap img;
    int largest = 0;

    private String[] class_names = {"Alstonia Scholaris","Arjun","Basil","Chinar","Guava","Jamun","Jatropha","Lemon","Mango","Pomegranate","Pongamia Pinnata"};
    private float[] predicted_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camimage = findViewById(R.id.capturedimage);
        camerabtn = findViewById(R.id.camera);
        gallerybtn = findViewById(R.id.gallery);
        identify = findViewById(R.id.identifyimg);
        textView = findViewById(R.id.textViewdetail);

        if (ContextCompat.checkSelfPermission(Camera.this,Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Camera.this, new String[]{
                        Manifest.permission.CAMERA
                },100);
        }
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,101);
            }
        });

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ml", "button clicked");
            img = Bitmap.createScaledBitmap(img,1,1,true);

                try {
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1, 1, 3}, DataType.FLOAT32);

                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(img);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();

                    predicted_array = outputFeature0.getFloatArray();

                    for ( int i = 0; i < outputFeature0.getFloatArray().length; i++ )
                    {
                        Log.d("ml", "index = "+ i + "value = "+ outputFeature0.getFloatArray()[i] );

                        if ( outputFeature0.getFloatArray()[i] > outputFeature0.getFloatArray()[largest] ) largest = i;
                    }

//                    int largest = 0;
//                    for ( int i = 1; i < predicted_array.length; i++ )
//                    {
//                        if ( predicted_array[i] > predicted_array[largest] ) largest = i;
//                    }
                    Log.d("ml", "largest = "+ largest);
                    Log.d("ml", "classname = "+ class_names);
                    Log.d("ml", "predictions = "+ outputFeature0.getFloatArray());
                    textView.setText(outputFeature0.getFloatArray()[largest] + "\n"+class_names[largest]);
//                  textView.setText(outputFeature0.getFloatArray()[largest] + "\n"+outputFeature0.getFloatArray()[0]);



                } catch (IOException e) {
                    System.out.println(e.toString());
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camimage.setImageBitmap(bitmap);

        }
        if (requestCode == 101){
            camimage.setImageURI(data.getData());
            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}