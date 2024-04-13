package com.example.imagepro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class GooglesignIn extends Login {

    private static final int RC_SIGN_IN = 101;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mgoogleSignInClient;
    ProgressDialog progressDialog;
    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(GooglesignIn.this);
        progressDialog.setTitle("Google Sign in");
        progressDialog.setMessage("Signing in with your Google account");
        progressDialog.show();

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("YourPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        auth.signOut();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mgoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Handle sign-out completion if needed
                    }
                });


        Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e)
            {
                if (e.getStatusCode() == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                    // User canceled the sign-in process
                    Toast.makeText(this, "Sign-in canceled", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(this, "Unable to sign in with Google", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential=GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    FirebaseUser user=auth.getCurrentUser();
                    Users users=new Users();
                    users.setUserId(user.getUid());
                    users.setName(user.getDisplayName());
                    users.setProfile(user.getPhotoUrl().toString());
                    database.getReference().child("Users").child(user.getUid()).setValue(users);

                    updateUI(user);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Unable to sign in with Google",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();

                }
            }
        });
    }
    private void updateUI(FirebaseUser user)
    {
        Intent intent=new Intent(GooglesignIn.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}