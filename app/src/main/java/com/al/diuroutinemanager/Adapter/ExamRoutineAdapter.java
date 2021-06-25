package com.al.diuroutinemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al.diuroutinemanager.Model.ExamModel;
import com.al.diuroutinemanager.Model.TeacherExamRoutineModel;
import com.al.diuroutinemanager.R;

import java.util.ArrayList;

public class ExamRoutineAdapter extends RecyclerView.Adapter<ExamRoutineAdapter.ExamViewHolder> {

    Context context;
    ArrayList<ExamModel> arrayList;
    ArrayList<TeacherExamRoutineModel> arrayList1;
    String role;

    public ExamRoutineAdapter(Context context, ArrayList<ExamModel> arrayList,ArrayList<TeacherExamRoutineModel> arrayList1,String role) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayList1 = arrayList1;
        this.role=role;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.routine_exam_row,parent,false);

        return new ExamViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {


        if(role.equals("s")){
            holder.teacher.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.course.setText(arrayList.get(position).getCourse());
            holder.date.setText(arrayList.get(position).getDate());
            holder.time.setText(arrayList.get(position).getTime());
            holder.type.setText(arrayList.get(position).getType());
            holder.week2.setText(arrayList.get(position).getWeek());
            holder.room.setText(arrayList.get(position).getRoom());
        }else if(role.equals("t")) {
            holder.teacher.setVisibility(View.VISIBLE);
            holder.teacher.setText(arrayList1.get(position).getTeacher());
            holder.linearLayout.setVisibility(View.GONE);
            holder.date.setText(arrayList1.get(position).getDate());
            holder.time.setText(arrayList1.get(position).getTime());
            holder.week2.setText(arrayList1.get(position).getWeek());
            holder.type.setText(arrayList1.get(position).getType());
            holder.room.setText(arrayList1.get(position).getRoom());
        }

    }

    @Override
    public int getItemCount() {
        if(role.equals("s")){return arrayList.size();}
        else {
            return arrayList1.size();
        }

    }

    public class ExamViewHolder extends RecyclerView.ViewHolder{

        TextView course,date,type,time,week2,room,teacher;
        LinearLayout linearLayout;
        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);

            course=itemView.findViewById(R.id.course_name2);
            date=itemView.findViewById(R.id.date2);
            time=itemView.findViewById(R.id.time2);
            week2=itemView.findViewById(R.id.week2);
            room=itemView.findViewById(R.id.room2);
            type=itemView.findViewById(R.id.type2);
            teacher=itemView.findViewById(R.id.teacher);
            linearLayout=itemView.findViewById(R.id.linearView);




        }
    }
}
