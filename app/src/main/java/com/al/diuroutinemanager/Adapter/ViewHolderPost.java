package com.al.diuroutinemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.Model.ClassModel;
import com.al.diuroutinemanager.R;

import java.util.ArrayList;


public class ViewHolderPost extends RecyclerView.Adapter<ViewHolderPost.MyViewHolder> {

    Context context;
    ArrayList<ClassModel> lists;
    String who;

    public ViewHolderPost(Context context, ArrayList<ClassModel> days, String who){

        this.context=context;
        this.lists=days;
        this.who=who;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(context);

        View view=  layoutInflater.inflate(R.layout.class_sample,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // days.get(position).getDt();

        if(who.equals("te")) {
            holder.name.setText(lists.get(position).getCourse_name());
            holder.rm.setText(lists.get(position).getRoom());
            holder.teacher.setText(lists.get(position).getInitial());
            holder.time.setText(lists.get(position).getTime());
        }else {

            holder.name.setText(lists.get(position).getCourse_name());
            holder.rm.setText(lists.get(position).getRoom());
            holder.teacher.setText(lists.get(position).getTeacher_name());
            holder.time.setText(lists.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rm,teacher,time;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.course_names);
            rm=itemView.findViewById(R.id.rooms);
            teacher=itemView.findViewById(R.id.teachers);
            time=itemView.findViewById(R.id.times);

        }
    }
}