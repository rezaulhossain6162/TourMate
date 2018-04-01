package com.toremate.tourmate;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllEvent extends Fragment {
    public ViewAllEvent() {

    }
    ArrayList<TravelEventModel> data;
    MyCustomAdapterForViewEvent adapter;
    ListView dataListView;
    ProgressDialog progressDialog;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_view_all_event, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        dataListView=v.findViewById(R.id.dataListView);
        data=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        Query query=databaseReference.child("TravelEvent").orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    final TravelEventModel travelEvent = child.getValue(TravelEventModel.class);
                    data.add(travelEvent);
                    adapter = new MyCustomAdapterForViewEvent(getContext(),R.layout.customlayoutforviewevent, data);
                    dataListView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;

    }
}