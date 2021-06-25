package com.al.diuroutinemanager.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.Model.NotificationModel;
import com.al.diuroutinemanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    ArrayList<NotificationModel> arrayList;

    public NotificationAdapter(Context context,ArrayList<NotificationModel> arrayList) {

        this.context=context;
        this.arrayList=arrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NotificationModel notificationModel =arrayList.get(position);

        String d= new SimpleDateFormat("dd MMMM yyyy").format(Calendar.getInstance().getTime());

        if(d.equals(notificationModel.getDate())){
            holder.date.setText("Today");
            holder.date.setTextColor(Color.parseColor("#008577"));

        }else {

            holder.date.setText(notificationModel.getDate());

        }

        holder.title.setText(notificationModel.getTitle());
        holder.des.setText(notificationModel.getMessage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(context).setTitle(notificationModel.getTitle()).setMessage(notificationModel.getMessage())
                .setCancelable(true)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,des,date;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.tit);
            des=itemView.findViewById(R.id.des);
            date=itemView.findViewById(R.id.da);
            cardView=itemView.findViewById(R.id.card);

        }
    }
}
