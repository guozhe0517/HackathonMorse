package com.doyoon.android.hackathonmorse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends FragmentActivity {

    EditText myEmail,myPassWrod;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String email;
    String password;
    private FirebaseAuth  mAuth = FirebaseAuth.getInstance();;
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d("Auth", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d("Auth", "onAuthStateChanged:signed_out");
            }
            // ...
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myEmail = (EditText)findViewById(R.id.email);
        myPassWrod = (EditText)findViewById(R.id.passWord);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Chat");



    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void signUp(View view){
        String email = myEmail.getText().toString();
        String password = myPassWrod.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sign Up 되지않았음니다", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Sign Up 되었읍니다", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    public void signIn(View view){
        email = myEmail.getText().toString();
        password = myPassWrod.getText().toString();

        if(!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Auth", "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w("Auth", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Sign In 되지않았읍니다", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                String userUid = mAuth.getCurrentUser().getUid();
                                intent.putExtra("UID",userUid);
                                startActivity(intent);
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this, "email,password를 입력하세요", Toast.LENGTH_LONG).show();

        }
    }
}

