package com.example.queryapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.queryapplication.R;
import com.example.queryapplication.model.TeacherModel;
import com.example.queryapplication.views.TeacherHome;
import com.example.queryapplication.views.TeacherReply;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {




    private Context mContext;
    private List<TeacherModel> TeacherList;


    private static final String TAG = "RecyclerViewAdapter";



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,query,add,prodid;
        public ImageView icon;



        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name);
            query = (TextView) view.findViewById(R.id.txt_query);
            add=view.findViewById(R.id.txt_reply);


        }


    }


    public TeacherAdapter(List<TeacherModel> TeacherList, Context context) {

        this.TeacherList = TeacherList;
        mContext=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_list, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TeacherModel teacher = TeacherList.get(position);




        holder.name.setText(teacher.getName());
        holder.query.setText(teacher.getQuery());




        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, TeacherReply.class);
                intent.putExtra("name",holder.name.getText());
                intent.putExtra("query",holder.query.getText());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return TeacherList.size();
    }
}