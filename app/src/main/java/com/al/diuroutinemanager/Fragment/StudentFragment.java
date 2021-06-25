package com.al.diuroutinemanager.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.al.diuroutinemanager.Model.ClassModel;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.ViewHolderSearch1;
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


public class StudentFragment extends Fragment {
    RecyclerView recyclerView ;
    LinearLayoutManager llm ;
    Context context;
    String mat;

    DatabaseReference databaseReference;
    View view;

    Spinner level,term,shift,section;
    Button search;
    public FirebaseRecyclerAdapter<ClassModel, ViewHolderSearch1> firebaseRecyclerAdapter ;
    public FirebaseRecyclerOptions<ClassModel> firebaseRecyclerOptions ;

    ProgressBar progressBar ;
    Sprite doubleBounce = new Wave();

    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_student, container, false);

        search=view.findViewById(R.id.submit);
        level=view.findViewById(R.id.level1);
        term=view.findViewById(R.id.term1);
        section=view.findViewById(R.id.section1);
        shift=view.findViewById(R.id.shift1);
        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        databaseReference= FirebaseDatabase.getInstance().getReference("Classes");

        recyclerView = view.findViewById(R.id.myList);
        setHasOptionsMenu(true);
        llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm) ;
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);



        final String[] a=getResources().getStringArray(R.array.level);
        final String[] b=getResources().getStringArray(R.array.term);
        final String[] c=getResources().getStringArray(R.array.section);
        final String[] d=getResources().getStringArray(R.array.shift);




         search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 progressBar.setVisibility(View.VISIBLE);
                 String a1=a[level.getSelectedItemPosition()];
                 String b1=b[term.getSelectedItemPosition()];
                 String c1=c[section.getSelectedItemPosition()];
                 String d1=d[shift.getSelectedItemPosition()];

                 mat=d1+a1+b1+c1;


                // recyclerView.scheduleLayoutAnimation();
                 loadData();
             }
         });


        return view;

    }

    private void loadData() {

        Query query=databaseReference.orderByChild("match1").equalTo(mat);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(),"No data Found.",Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<ClassModel>().setQuery(query,ClassModel.class).build();



        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ClassModel, ViewHolderSearch1>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderSearch1 viewHolderSearch1, int i, @NonNull ClassModel classModel) {

                viewHolderSearch1.setDetails(classModel.getCourse_name(),classModel.getRoom(),classModel.getTeacher_name()
                ,classModel.getWeek(),classModel.getTime());
            }

            @NonNull
            @Override
            public ViewHolderSearch1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               View itemview= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_sample, parent, false);

                return new ViewHolderSearch1(itemview);
            }
        };

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        progressBar.setVisibility(View.INVISIBLE);

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
