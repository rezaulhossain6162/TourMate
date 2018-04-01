package com.toremate.tourmate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextView tvToCreateNewAcc,tvError;
    EditText etName,etEamil,etPassword;
    Button btnCreateAccount;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    SignInInformation information;
    String uid;
    String name;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialization();
    }

    private void initialization() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        tvToCreateNewAcc= (TextView) findViewById(R.id.tvToCreateNewAcc);
        tvError= (TextView) findViewById(R.id.tvError);
        etName= (EditText) findViewById(R.id.etName);
        etEamil= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btnCreateAccount= (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreateAccount:
                firebaseAuth = FirebaseAuth.getInstance();
                name = etName.getText().toString().trim();
                email = etEamil.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                    etName.setError("Must be fill up");
                    etEamil.setError("Must be fill up");
                    etPassword.setError("Must be fill up");
                } else {
                    this.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                uid = currentUser.getUid();
                                information = new SignInInformation(name, email, password, uid);
                                DatabaseReference mRef = firebaseDatabase.getReference().child("SignInInformation");
                                mRef.push().setValue(information);
                                finish();
                                Toast.makeText(SignUp.this, "Successfull reg", Toast.LENGTH_SHORT).show();
                            } else {
                                String bad = task.getException().getMessage();
                                tvError.setText(bad);
                            }
                        }
                    });
                }
              break;
    }
}
}
