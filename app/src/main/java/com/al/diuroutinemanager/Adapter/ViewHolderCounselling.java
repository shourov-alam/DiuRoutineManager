package com.al.diuroutinemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.Model.TeacherCounModel;
import com.al.diuroutinemanager.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewHolderCounselling extends RecyclerView.Adapter<ViewHolderCounselling.ViewHolder>{

    Context context;
    ArrayList<TeacherCounModel> arrayList;

    public ViewHolderCounselling(Context context, ArrayList<TeacherCounModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.teacher_counselling,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.initial.setText(arrayList.get(position).getInitial());
        holder.day.setText(arrayList.get(position).getDayOfWeek());
        holder.time.setText(arrayList.get(position).getTime());

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Counselling").child(arrayList.get(position).getRootUid()).removeValue();
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,initial,time,day;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tName);
            initial=itemView.findViewById(R.id.tInitial);
            day=itemView.findViewById(R.id.tDay);
            time=itemView.findViewById(R.id.tTime);
            imgView=itemView.findViewById(R.id.img);
        }

    }
}
