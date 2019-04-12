package com.example.myfirstapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_editText_ForSignup,password_editText_ForSignup;
    private Button button_Signup;
    private TextView textView_login;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

        mAuth = FirebaseAuth.getInstance();

        button_Signup.setOnClickListener(this);
        textView_login.setOnClickListener(this);

    }

    private void initViews(){

        email_editText_ForSignup = this.findViewById(R.id.signup_email_address_editText);
        password_editText_ForSignup= this.findViewById(R.id.signup_password_editText);
        button_Signup = this.findViewById(R.id.signup_button);
        textView_login = this.findViewById(R.id.login_text_view);
        progressBar = this.findViewById(R.id.progress_bar);


    }

    private void registerUser(){
        email = email_editText_ForSignup.getText().toString().trim();
        password = password_editText_ForSignup.getText().toString().trim();
        if(validate(email,password)){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        intent.putExtra("profile activity","profile activity");
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(), "User with this email is already registered ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }

    private boolean validate(String email, String password){
        if(email.isEmpty()){
            email_editText_ForSignup.setError("Email id field is required");
            email_editText_ForSignup.requestFocus();
            return false;
        }else if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_editText_ForSignup.setError("Email id field is invalid");
            email_editText_ForSignup.requestFocus();
            return false;
        }
        else if (password.isEmpty()) {
            password_editText_ForSignup.setError("password field is required");
            password_editText_ForSignup.requestFocus();
            return false;
        }
        else if(password.length() <6){
            password_editText_ForSignup.setError("minimum length of password should be 6");
            password_editText_ForSignup.requestFocus();
            return false;
        }
        else{
            return true;
        }

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.signup_button:
                registerUser();
                break;
            case R.id.login_text_view:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }

    }

}
