package com.example.queryapplication.controller;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.queryapplication.R;
import com.example.queryapplication.model.SpinnerModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapt extends ArrayAdapter<SpinnerModel> {
    public SpinnerAdapt(Context context, ArrayList<SpinnerModel> spinnerlist)
    {
        super(context,0,spinnerlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }
    private View initview(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.item_spinner,parent,false
            );
        }
        TextView txtviewname=convertView.findViewById(R.id.txtspinner);

        SpinnerModel spinneritem=getItem(position);

        if(spinneritem!=null) {
            txtviewname.setText(spinneritem.getSpinnertext());
        }

        return  convertView;

    }
}

