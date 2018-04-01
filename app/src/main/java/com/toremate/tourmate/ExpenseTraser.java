package com.toremate.tourmate;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseTraser extends AppCompatActivity {
    TextView tvDestinationE,tvBudgetES,tvTolalCoste,tvBalanceEs;
    int balance=0;
    int cbl;
    ListView lvItem;
    ExpenseTraserModel person;
    String destination1;
    ArrayList<ModelClass>data;
    CustomExpenseTrase adapter;
    Bundle extras;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensetraser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();
        initialization();
        getData();
    }
    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("expensetraser");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    person = child.getValue(ExpenseTraserModel.class);
                    destination1 = person.getDestination();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String destinationabc = extras.getString("destinationhi");
        Query query=reference.child("expensetraser").orderByChild("destination").equalTo(destinationabc);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> value = dataSnapshot.getChildren();
            for (DataSnapshot dataSnapshot1 : value) {

                ExpenseTraserModel travelEvent = dataSnapshot1.getValue(ExpenseTraserModel.class);
                String destination = travelEvent.getDestination();
                String description = travelEvent.getDescription();
                String amount = travelEvent.getAmount();
                String budget = travelEvent.getBudget();
                int result = Integer.parseInt(amount);
                int result1 = Integer.parseInt(budget);
                cbl=cbl+result;
                String s = Integer.toString(cbl);
                balance=balance+result;
                int currentAmount=result1-balance;
                String am=Integer.toString(currentAmount);
                tvDestinationE.setText(destination);
                tvBudgetES.setText(budget);
                tvTolalCoste.setText(s);
                tvBalanceEs.setText(am);

                ModelClass modelClass=new ModelClass(destination,budget,amount,description,am);
                data.add(modelClass);
//                data.add(amount);
//                data.add(budget);
//                data.add(destination);
//                data.add(description);
//                data.add(am);
                adapter=new CustomExpenseTrase(getApplicationContext(),R.layout.customforexpensetrese,data);
                lvItem.setAdapter(adapter);
                progressDialog.dismiss();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }
    private void initialization() {
        tvDestinationE= (TextView) findViewById(R.id.tvDestinationE);
        tvBudgetES= (TextView) findViewById(R.id.tvBudgetEs);
        tvTolalCoste= (TextView) findViewById(R.id.tvCostEs);
        tvBalanceEs= (TextView) findViewById(R.id.tvBalanceEs);
        lvItem= (ListView) findViewById(R.id.lvItem);
        data=new ArrayList<>();
    }
}