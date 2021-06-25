package com.al.diuroutinemanager.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.al.diuroutinemanager.Adapter.ExamRoutineAdapter;
import com.al.diuroutinemanager.Model.ExamModel;
import com.al.diuroutinemanager.Model.StudentModel;
import com.al.diuroutinemanager.Model.TeacherExamRoutineModel;
import com.al.diuroutinemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExamRoutine extends Fragment {

    TextView level,term,shift,section;

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    View view;
    ArrayList <ExamModel> arrayList;
    ArrayList <TeacherExamRoutineModel> arrayList1;


    CardView cardView;




    public ExamRoutine() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_exam_routine, container, false);
        level=view.findViewById(R.id.level2);
        term=view.findViewById(R.id.term2);
        shift=view.findViewById(R.id.shift2);
        section=view.findViewById(R.id.section2);
        cardView=view.findViewById(R.id.card);
        cardView.setVisibility(View.INVISIBLE);

        recyclerView=view.findViewById(R.id.myList);

        arrayList=new ArrayList<>();
        arrayList1=new ArrayList<>();

        llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm) ;

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.getChildrenCount()<8){

                    cardView.setVisibility(View.GONE);
                    String b=snapshot.child("initial").getValue(String.class);

                    ////getting multiple value

                    Query query=FirebaseDatabase.getInstance().getReference("TeacherExamRoutines").orderByChild("initial").equalTo(b);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)

                        {

                            if(!snapshot.exists()){

                                Toast.makeText(getActivity(),"No Exam Routine Found",Toast.LENGTH_LONG).show();
                            }
                            for (DataSnapshot dataSnapshot1: snapshot.getChildren()){

                               TeacherExamRoutineModel teacherExamRoutineModel=dataSnapshot1.getValue(TeacherExamRoutineModel.class);
                               arrayList1.add(teacherExamRoutineModel);
                            }


                            recyclerView.setAdapter(new ExamRoutineAdapter(getActivity(),null,arrayList1,"t"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {

                    String a=snapshot.child("match").getValue(String.class);
                    cardView.setVisibility(View.VISIBLE);

                    ////getting multiple value
                    StudentModel studentModel =snapshot.getValue(StudentModel.class);
                    level.setText(studentModel.getLevel());
                    term.setText(studentModel.getTerm());
                    shift.setText(studentModel.getShift());
                    section.setText(studentModel.getSection());

                    Query query=FirebaseDatabase.getInstance().getReference("ExamRoutines").orderByChild("match").equalTo(a);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)

                        {

                            if(!snapshot.exists()){

                                Toast.makeText(getActivity(),"No Exam Routine Found",Toast.LENGTH_LONG).show();
                            }
                            for (DataSnapshot dataSnapshot1: snapshot.getChildren()){

                                ExamModel examModel =dataSnapshot1.getValue(ExamModel.class);
                                arrayList.add(examModel);
                            }


                            recyclerView.setAdapter(new ExamRoutineAdapter(getActivity(),arrayList,null,"s"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                //getting a single value



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}
