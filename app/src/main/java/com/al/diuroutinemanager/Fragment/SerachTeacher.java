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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.al.diuroutinemanager.Model.ClassModel;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.ViewHolderSearch;
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

public class SerachTeacher extends Fragment {
    RecyclerView recyclerView ;
    LinearLayoutManager llm ;
    Context context;
    EditText initial;
    Button search;
    DatabaseReference databaseReference;
    View view;

    ProgressBar progressBar;
    Sprite doubleBounce = new Wave();


    public FirebaseRecyclerAdapter<ClassModel, ViewHolderSearch> firebaseRecyclerAdapter ;
    public FirebaseRecyclerOptions<ClassModel>firebaseRecyclerOptions ; // seraching in the profile ;

    public SerachTeacher() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_serach_teacher, container, false);

        initial=view.findViewById(R.id.te_initial);
        search=view.findViewById(R.id.te_search);

        databaseReference= FirebaseDatabase.getInstance().getReference("Classes");

        recyclerView = view.findViewById(R.id.myList);
        setHasOptionsMenu(true);
        llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm) ;
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
               loadData();
            }
        });
        return view;
    }

    private void loadData() {

Query query=databaseReference.orderByChild("initial").equalTo(initial.getText().toString().trim().toUpperCase());


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

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ClassModel, ViewHolderSearch>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderSearch viewHolderSearch, int i, @NonNull ClassModel classModel) {

                viewHolderSearch.setDetails(classModel.getTeacher_name(),classModel.getCourse_name(),classModel.getLevel()
                ,classModel.getTerm(),classModel.getDay(),classModel.getSection(),classModel.getWeek()
                ,classModel.getTime(),classModel.getRoom());

            }

            @NonNull
            @Override
            public ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemview=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.teacher_sample, parent, false);

                return new ViewHolderSearch(itemview);
            }
        };

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();




        recyclerView.setAdapter(firebaseRecyclerAdapter);
       // firebaseRecyclerAdapter.notifyDataSetChanged();
       // recyclerView.scheduleLayoutAnimation();



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
