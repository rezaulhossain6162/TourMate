package com.toremate.tourmate;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomExpenseTrase extends ArrayAdapter<ModelClass> {
    ArrayList<ModelClass> mData;
    Context context;
    public CustomExpenseTrase( Context context, int resource, ArrayList<ModelClass> objects) {
        super(context, resource, objects);
        this.context=context;
        this.mData=objects;
    }




    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View mconvertview=convertView;

        if (mconvertview==null){
            LayoutInflater from = LayoutInflater.from(getContext());
            mconvertview = from.inflate(R.layout.customforexpensetrese, null);
        }
        TextView destination = mconvertview.findViewById(R.id.tvBudget);
        TextView perpose = mconvertview.findViewById(R.id.tvPerposes);
        TextView tvPerpusCost = mconvertview.findViewById(R.id.tvPerpusCost);
        ModelClass modelClass = mData.get(position);
        destination.setText(""+modelClass.getDestination());
        tvPerpusCost.setText(""+modelClass.getCost());
        perpose.setText(""+modelClass.getBecauseOfCost());
        return mconvertview;

    }
}
