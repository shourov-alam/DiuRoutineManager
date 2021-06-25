package com.al.diuroutinemanager.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.R;

public class ViewHolderSearch extends RecyclerView.ViewHolder {

TextView t_name,course_name,level,term,shift,section,day,time,room;

    public ViewHolderSearch(@NonNull View itemView) {
        super(itemView);

        t_name=itemView.findViewById(R.id.te_name);
        course_name=itemView.findViewById(R.id.te_course);
        level=itemView.findViewById(R.id.level);
        term=itemView.findViewById(R.id.term);
        shift=itemView.findViewById(R.id.shift);
        section=itemView.findViewById(R.id.section);
        day=itemView.findViewById(R.id.day);
        time=itemView.findViewById(R.id.time);
        room=itemView.findViewById(R.id.room);

    }

    public void setDetails(String title, String catgory,String lv,String tr,String sh,String sec,String da,String tim,String rom) {

        t_name.setText(title);
        course_name.setText(catgory);
        level.setText(lv);
        term.setText(tr);
        shift.setText(sh);
        section.setText(sec);
        day.setText(da);
        time.setText(tim);
        room.setText(rom);


    }


}
