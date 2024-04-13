package com.example.imagepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    EditText inputemail,inputpwd,inputconfirmpwd;
    Button reg;
    TextView existinguser;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputemail=findViewById(R.id.email);
        inputpwd=findViewById(R.id.pwd);
        inputconfirmpwd=findViewById(R.id.confirmpwd);
        reg=findViewById(R.id.register);
        existinguser=findViewById(R.id.regiterbtn);

        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        existinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });



    }
    private void PerformAuth()
    {
        String email=inputemail.getText().toString();
        String pwd=inputpwd.getText().toString();
        String confirmpwd=inputconfirmpwd.getText().toString();

        if(!email.matches(emailPattern))
        {
            inputemail.setError("Enter correct email.");
        }
        else if(pwd.isEmpty() || pwd.length()<6)
        {
            inputpwd.setError("Password field must not be empty and should contain more than 6 characters.");
        }
        else if(!pwd.equals(confirmpwd))
        {
            inputconfirmpwd.setError("Passwords must match.");
        }
        else{
            progressDialog.setMessage("Please wait while registering.");
            progressDialog.setTitle("Registering");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(register.this, "Account created.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(register.this,"User already exists. Login to continue.",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    }
}