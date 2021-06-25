package com.al.diuroutinemanager.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.al.diuroutinemanager.Model.ClassModel;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.ViewHolderPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tuesday extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    Context context;
    ArrayList<ClassModel> list=new ArrayList<>();
    ClassModel classModel;
    DatabaseReference databaseReference,databaseReference1;
    View view;
    TextView t;
    boolean check;
    public Tuesday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tuesday, container, false);
        context = view.getContext();
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Classes");

        recyclerView=view.findViewById(R.id.myList);
        t=view.findViewById(R.id.txt);
        t.setVisibility(View.INVISIBLE);

        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.getChildrenCount()<8){

                    final String b =snapshot.child("initial").getValue(String.class);

                    Query query =databaseReference1.orderByChild("week").equalTo("Tuesday");

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(!snapshot.exists()){

                                t.setVisibility(View.VISIBLE);
                            }else {
                                t.setVisibility(View.INVISIBLE);
                                list.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){


                                    classModel=dataSnapshot1.getValue(ClassModel.class);

                                    if(classModel.getInitial().equals(b)){
                                        list.add(classModel);
                                        check=true;
                                    }


                                }

                                if(!check){
                                    t.setVisibility(View.VISIBLE);

                                }

                                check=false;
                                ViewHolderPost adapter = new ViewHolderPost(getActivity(), list,"te");



                                setHasOptionsMenu(true);
                                llm = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(llm) ;

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {

                    final String a =snapshot.child("match").getValue(String.class);

                    Query query =databaseReference1.orderByChild("match").equalTo("Tuesday"+a);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){

                                t.setVisibility(View.VISIBLE);
                            }else {
                                t.setVisibility(View.INVISIBLE);
                                list.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    classModel=dataSnapshot1.getValue(ClassModel.class);

                                    list.add(classModel);

                                }


                                ViewHolderPost adapter = new ViewHolderPost(getActivity(), list,"st");
                                setHasOptionsMenu(true);
                                llm = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(llm) ;

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}









//  String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

  /*  public void loadData ()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Classes");

        Query query =ref.orderByChild("week").equalTo("Tuesday");
        options = new FirebaseRecyclerOptions.Builder<ClassModel>().setQuery(query, ClassModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClassModel, ViewHolderPost>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderPost viewHolderPost, int i, @NonNull final ClassModel classModel) {


                // setThe data to the row
                //        String imageLink , String itemdes ,String name  ,String quatitys  ,String  category ;


                //     String name , String quantity , String mail , String returnDate , String stats ;


                viewHolderPost.setDetails(classModel.getCourse_name(),classModel.getTeacher_name(),classModel.getInitial()
                        ,classModel.getTime(),classModel.getRoom());


            }


            @NonNull
            @Override
            public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.class_sample, parent, false);
                ViewHolderPost viewHolder = new ViewHolderPost(itemView);

                return viewHolder;
            }
        };

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();

        //setting adapter

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    } */


