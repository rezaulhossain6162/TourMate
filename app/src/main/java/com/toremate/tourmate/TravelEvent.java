package com.toremate.tourmate;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TravelEvent extends AppCompatActivity implements View.OnClickListener {
    private EditText etTravelDestination, etEstimatedBudget, etFromData, etToData;
    private Button btnCreateEvent;
    private TextView tvError;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
    }

    private void initialization() {
        firebaseAuth = FirebaseAuth.getInstance();
        etTravelDestination = (EditText) findViewById(R.id.etTravelDestination);
        etEstimatedBudget = (EditText) findViewById(R.id.etEstimatedBudget);
        etFromData = (EditText) findViewById(R.id.etFromData);
        etToData = (EditText) findViewById(R.id.etToDate);
        btnCreateEvent = (Button) findViewById(R.id.btnCreateRecord);
        tvError = (TextView) findViewById(R.id.tvError);
        btnCreateEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreateRecord:

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String uid = currentUser.getUid();
                String destination = etTravelDestination.getText().toString().trim();
                String budget = etEstimatedBudget.getText().toString().trim();
                String fromdate = etFromData.getText().toString().trim();
                String todata = etToData.getText().toString().trim();
                TravelEventModel travelEventModel = new TravelEventModel(uid, destination, budget, fromdate, todata);
                if (TextUtils.isEmpty(destination) || TextUtils.isEmpty(budget) || TextUtils.isEmpty(fromdate) || TextUtils.isEmpty(todata)) {
                    etTravelDestination.setError("must be fill up");
                    etEstimatedBudget.setError("must be fill up");
                    etFromData.setError("must be fill up");
                    etToData.setError("must be fill up");
                } else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("TravelEvent");
                    mRef.push().setValue(travelEventModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                etTravelDestination.getText().clear();
                                etEstimatedBudget.getText().clear();
                                etFromData.getText().clear();
                                etToData.getText().clear();
                                Toast.makeText(TravelEvent.this, "Success", Toast.LENGTH_SHORT).show();
                            }else{
                                tvError.setText("Problem Occur");
                            }
                        }
                    });
                    break;
                }
        }
    }
}