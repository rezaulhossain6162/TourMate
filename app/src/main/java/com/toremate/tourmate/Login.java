package com.toremate.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail,etpassword;
    private Button btnLogin;
    private TextView tvForSignup,tvForSign,tvError;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private void initialization() {
        tvForSign= (TextView) findViewById(R.id.tvForSign);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etpassword= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        tvForSignup= (TextView) findViewById(R.id.tvForSignup);
        tvError= (TextView) findViewById(R.id.tvError);
        tvForSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvForSignup:
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
                break;
            case R.id.btnLogin:
                String email = etEmail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    etEmail.setError("must be fill up");
                    etpassword.setError("must be fill up");
                }else {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                String message = task.getException().getMessage();
                                tvError.setText(message);
                            }

                        }
                    });
                }
                break;
        }

    }
}
