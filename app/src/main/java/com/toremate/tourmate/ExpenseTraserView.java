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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExpenseTraserView extends AppCompatActivity implements View.OnClickListener {

    private String budget;
    private EditText etDescription,etAmount;
    private TextView tvError;
    private Button btnCreateRecord,btnViewRecord;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mRef;
    private String uid;
    Bundle extras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_traser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();
        initialization();

    }

    private void initialization() {
        etDescription= (EditText) findViewById(R.id.etDescription);
        etAmount= (EditText) findViewById(R.id.etAmont);
        btnCreateRecord= (Button) findViewById(R.id.btnCreateRecord);
        tvError= (TextView) findViewById(R.id.tvError);
        btnViewRecord= (Button) findViewById(R.id.btnViewRecord);
        btnCreateRecord.setOnClickListener(this);
        btnViewRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreateRecord:
                uid = firebaseAuth.getInstance().getCurrentUser().getUid();
                String description = etDescription.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();
                if (TextUtils.isEmpty(description) ||TextUtils.isEmpty(amount)) {
                    etDescription.setError("Must be fill up");
                    etAmount.setError("Must be fill up");
                }else {
                    budget = extras.getString("budget");
                    String destination = extras.getString("destination");
                    ExpenseTraserModel traserModel = new ExpenseTraserModel(description, amount, uid, budget, destination);
                    firebaseAuth = FirebaseAuth.getInstance();
                    mRef = FirebaseDatabase.getInstance().getReference().child("expensetraser");
                    mRef.push().setValue(traserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ExpenseTraserView.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            etAmount.getText().clear();
                            etDescription.getText().clear();
                        }else {
                            tvError.setText("Error Occur");
                            Toast.makeText(ExpenseTraserView.this, "No Insert Data", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
                break;
            case R.id.btnViewRecord:
                String destinationall = extras.getString("destination");
                Intent intent=new Intent(ExpenseTraserView.this,ExpenseTraser.class);
                intent.putExtra("destinationhi",destinationall);
                startActivity(intent);
                break;
        }
    }
}
