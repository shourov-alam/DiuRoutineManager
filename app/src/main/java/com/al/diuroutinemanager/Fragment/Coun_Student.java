package com.al.diuroutinemanager.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.al.diuroutinemanager.Model.TeacherCounModel;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.StudentCounViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Coun_Student extends Fragment {
Button search;
EditText initial;
RecyclerView recyclerView;
LinearLayoutManager llm;
DatabaseReference databaseReference;

    ProgressBar progressBar;
    Sprite doubleBounce = new Wave();



    public FirebaseRecyclerAdapter<TeacherCounModel, StudentCounViewHolder> firebaseRecyclerAdapter ;
    public FirebaseRecyclerOptions<TeacherCounModel> firebaseRecyclerOptions ; // seraching in the profile ;

    public Coun_Student() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_coun__student, container, false);

        search=view.findViewById(R.id.t_search);
        initial=view.findViewById(R.id.t_initial);
        recyclerView=view.findViewById(R.id.recycleView);

        databaseReference= FirebaseDatabase.getInstance().getReference("Counselling");

        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
        llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm) ;
        recyclerView.setHasFixedSize(true);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               // progressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });



        return view;
    }

    private void loadData() {


        Query query=databaseReference.orderByChild("initial").equalTo(initial.getText().toString().toUpperCase());


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()){
                    Toast.makeText(getContext(),"No data Found",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<TeacherCounModel>().setQuery(query,TeacherCounModel.class).build();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<TeacherCounModel, StudentCounViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull StudentCounViewHolder studentCounViewHolder, int i, @NonNull  TeacherCounModel teacherCounModel) {

                studentCounViewHolder.setDetails(teacherCounModel.getName(),teacherCounModel.getInitial()
                ,teacherCounModel.getDayOfWeek(),teacherCounModel.getTime());
            }

            @NonNull

            @Override
            public StudentCounViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemview=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_counselling, parent, false);

                return new StudentCounViewHolder(itemview);
            }
        };

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {

            try {
                firebaseRecyclerAdapter.startListening();
            }catch (Exception e){

            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            try {
                firebaseRecyclerAdapter.startListening();

            }catch (Exception e){

            }
        }
    }

}
