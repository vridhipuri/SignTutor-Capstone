package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.android.OpenCVLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    private Button camera_button;
    //private Button combine_letter_button;
    FirebaseAuth auth;

    Button sign_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        auth = FirebaseAuth.getInstance();
        sign_out_button=findViewById(R.id.logout);

        camera_button=findViewById(R.id.camerabutton);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        //combine_letter_button=findViewById(R.id.combineletterbutton);
        /*combine_letter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when this button is clicked navigate to combineLetterActivity
                //create combineLetterActivity
                startActivity(new Intent(MainActivity.this,CombineLettersActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });*/

        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        auth.signOut();
        // Redirect to login page
        startActivity(new Intent(MainActivity.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();  // Optional: Finish the current activity
    }
}