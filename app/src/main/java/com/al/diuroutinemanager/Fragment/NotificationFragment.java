package com.al.diuroutinemanager.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.al.diuroutinemanager.Adapter.NotificationAdapter;
import com.al.diuroutinemanager.Model.NotificationModel;
import com.al.diuroutinemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    LinearLayoutManager lm;
    ArrayList<NotificationModel> arrayList;
    ProgressBar progressBar;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView =view.findViewById(R.id.myList);
        lm=new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        arrayList=new ArrayList<>();
        progressBar=view.findViewById(R.id.progresssbar);

        databaseReference= FirebaseDatabase.getInstance().getReference("Notifications");

        recyclerView.setHasFixedSize(true);


        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility(View.GONE);

                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot1: snapshot.getChildren()){

                        NotificationModel notificationModel=dataSnapshot1.getValue(NotificationModel.class);

                        arrayList.add(notificationModel);

                    }

                    recyclerView.setAdapter(new NotificationAdapter(getActivity(),arrayList));
                }else {

                    Toast.makeText(getActivity(),"No data Found",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        return view;
    }

}
