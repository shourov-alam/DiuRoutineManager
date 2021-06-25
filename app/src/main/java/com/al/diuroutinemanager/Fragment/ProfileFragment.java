package com.al.diuroutinemanager.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.al.diuroutinemanager.Model.StudentModel;
import com.al.diuroutinemanager.Model.TeacherModel;
import com.al.diuroutinemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView name,id,shift,section,level,term,id1,shift1,section1,level1,term1;
    CardView cardView;
    ProgressBar progressBar;

    DatabaseReference databaseReference;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name=view.findViewById(R.id.name);
        id=view.findViewById(R.id.id);
        shift=view.findViewById(R.id.shift);
        section=view.findViewById(R.id.section);
        level=view.findViewById(R.id.level);
        term=view.findViewById(R.id.term);
        id1=view.findViewById(R.id.id1);
        shift1=view.findViewById(R.id.shift1);
        section1=view.findViewById(R.id.section1);
        level1=view.findViewById(R.id.level1);
        term1=view.findViewById(R.id.term1);
        cardView=view.findViewById(R.id.lay);
        progressBar=view.findViewById(R.id.progresssbar);




        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if( snapshot.getChildrenCount()<8){

                            TeacherModel teacherModel = snapshot.getValue(TeacherModel.class);

                            if(teacherModel != null){
                                id1.setText("Mobile");
                                shift1.setText("Initial");
                                cardView.setVisibility(View.INVISIBLE);
                                level1.setVisibility(View.INVISIBLE);
                                term1.setVisibility(View.INVISIBLE);
                                section1.setVisibility(View.INVISIBLE);

                                name.setText(teacherModel.getName());
                                id.setText(teacherModel.getMobile());
                                shift.setText(teacherModel.getInitial());
                                section.setVisibility(View.INVISIBLE);
                                level.setVisibility(View.INVISIBLE);
                                term.setVisibility(View.INVISIBLE);

                                progressBar.setVisibility(View.GONE);

                            }

                        }else {

                            StudentModel studentModel = snapshot.getValue(StudentModel.class);
                            if(studentModel != null){

                                name.setText(studentModel.getName());
                                id.setText(studentModel.getId());
                                shift.setText(studentModel.getShift());
                                section.setText(studentModel.getSection());
                                level.setText(studentModel.getLevel());
                                term.setText(studentModel.getTerm());

                                progressBar.setVisibility(View.GONE);

                            }

                        }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
