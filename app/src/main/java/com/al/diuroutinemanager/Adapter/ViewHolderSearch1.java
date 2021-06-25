package com.al.diuroutinemanager.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.R;


public class ViewHolderSearch1 extends RecyclerView.ViewHolder {

    TextView nam,rm,teacher,day,time;
    public ViewHolderSearch1(@NonNull View itemView) {
        super(itemView);

        nam=itemView.findViewById(R.id.course);
        rm=itemView.findViewById(R.id.roms);
        teacher=itemView.findViewById(R.id.teacher);
        day=itemView.findViewById(R.id.day);
        time=itemView.findViewById(R.id.time);


    }
    public void setDetails(String title, String catgory,String tea,String da,String ti) {

        nam.setText(title);
        rm.setText(catgory);
        teacher.setText(tea);
        day.setText(da);
        time.setText(ti);


    }
}
