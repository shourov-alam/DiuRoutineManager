package com.al.diuroutinemanager.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.R;

public class StudentCounViewHolder extends RecyclerView.ViewHolder {

    TextView name,initial,time,day;

    public StudentCounViewHolder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.tName);
        initial=itemView.findViewById(R.id.tInitial);
        day=itemView.findViewById(R.id.tDay);
        time=itemView.findViewById(R.id.tTime);
    }

    public void setDetails(String name1, String initial1,String day1,String time1) {

        name.setText(name1);
        initial.setText(initial1);
        day.setText(day1);
        time.setText(time1);


    }
}
