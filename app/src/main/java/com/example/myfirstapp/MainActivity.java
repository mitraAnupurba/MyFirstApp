package com.example.myfirstapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_editText_ForLogin,password_editText_ForLogin;
    private Button button_login;
    private TextView textView_signup;

    private FirebaseAuth mAuth;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mAuth = FirebaseAuth.getInstance();

        textView_signup.setOnClickListener(this);
        button_login.setOnClickListener(this);

    }

    private void initViews(){

        email_editText_ForLogin = this.findViewById(R.id.email_address_editText);
        password_editText_ForLogin = this.findViewById(R.id.password_editText);
        button_login = this.findViewById(R.id.login_button);
        textView_signup = this.findViewById(R.id.sign_up_text_view);

    }

    private void userLogin(){

        email = email_editText_ForLogin.getText().toString().trim();
        password = password_editText_ForLogin.getText().toString().trim();
        if(validate(email,password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        intent.putExtra("email address",email_editText_ForLogin.getText()+"\n"+password_editText_ForLogin.getText());
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"user successfully logged in",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{

        }


    }

    private boolean validate(String email, String password){
        if(email.isEmpty()){
            email_editText_ForLogin.setError("Email id field is required");
            email_editText_ForLogin.requestFocus();
            return false;
        }else if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_editText_ForLogin.setError("Email id field is invalid");
            email_editText_ForLogin.requestFocus();
            return false;
        }
        else if (password.isEmpty()) {
            password_editText_ForLogin.setError("password field is required");
            password_editText_ForLogin.requestFocus();
            return false;
        }
        else if(password.length() <6){
            password_editText_ForLogin.setError("minimum length of password should be 6");
            password_editText_ForLogin.requestFocus();
            return false;
        }
        else{
            return true;
        }

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                userLogin();
                break;
            case R.id.sign_up_text_view:
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                break;
        }
    }


}
