package com.toremate.tourmate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.toremate.tourmate.R.id.dataListView;
import static com.toremate.tourmate.R.id.packed;

/**
 * Created by jack on 10/26/2017.
 */

public class MyCustomAdapterForViewEvent extends ArrayAdapter<TravelEventModel> {
    private Context mcontext;
    TravelEventModel eventData;
    private ArrayList<TravelEventModel> data;
    public MyCustomAdapterForViewEvent(@NonNull Context context, int resource, ArrayList<TravelEventModel> object) {
        super(context, resource, object);
        this.mcontext = context;
        this.data = object;
    }
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mconvertview=convertView;
        if (mconvertview==null){
            LayoutInflater from = LayoutInflater.from(getContext());
            mconvertview = from.inflate(R.layout.customlayoutforviewevent, null);
        }
        eventData=data.get(position);
        Button btnCreateRecord=mconvertview.findViewById(R.id.btnCreateRecord);
        TextView destination = mconvertview.findViewById(R.id.tvDestination);
        TextView budget=mconvertview.findViewById(R.id.tvBudget);
        TextView fromdata=mconvertview.findViewById(R.id.tvFromData);
        TextView todate=mconvertview.findViewById(R.id.tvToData);
        destination.setText(""+eventData.getDestination());
        budget.setText(""+eventData.getBudget());
        fromdata.setText(""+eventData.getFromdate());
        todate.setText(""+eventData.getTodata());
        btnCreateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext.getApplicationContext(),ExpenseTraserView.class);
                intent.putExtra("budget",data.get(position).getBudget());
                intent.putExtra("destination",data.get(position).getDestination());
                mcontext.startActivity(intent);
            }
        });
        return mconvertview;
    }
}
