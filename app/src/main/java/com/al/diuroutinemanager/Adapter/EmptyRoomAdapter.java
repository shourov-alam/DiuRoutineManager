package com.al.diuroutinemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.Model.Empty_Room_Model;
import com.al.diuroutinemanager.R;

import java.util.ArrayList;

public class EmptyRoomAdapter extends RecyclerView.Adapter<EmptyRoomAdapter.RoomViewholder>{

    ArrayList<Empty_Room_Model> arrayList;
    Context context;

    public EmptyRoomAdapter(Context context,ArrayList<Empty_Room_Model> arrayList) {

        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RoomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_room_sample,parent,false);

        return new RoomViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewholder holder, int position) {

        String s=arrayList.get(position).getDate()+" "+arrayList.get(position).getMonth();
        holder.room.setText(arrayList.get(position).getRoom());
        holder.time.setText(s);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RoomViewholder extends RecyclerView.ViewHolder{

        TextView room,time;
        public RoomViewholder(@NonNull View itemView) {
            super(itemView);

            room=itemView.findViewById(R.id.rom);
            time=itemView.findViewById(R.id.tm);

        }
    }

}
