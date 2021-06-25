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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.al.diuroutinemanager.Adapter.EmptyRoomAdapter;
import com.al.diuroutinemanager.Model.Empty_Room_Model;
import com.al.diuroutinemanager.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class EmptyRoomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Spinner week,time;
    Button search;
    EmptyRoomAdapter adapter;
    View view;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    Context context;
    ArrayList<Empty_Room_Model> list=new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    Sprite doubleBounce = new Wave();

    public EmptyRoomFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_empty_room, container, false);

        context = view.getContext();
        databaseReference= FirebaseDatabase.getInstance().getReference("EmptyRooms");
       // databaseReference1=FirebaseDatabase.getInstance().getReference("Classes");
        recyclerView=view.findViewById(R.id.myList);
        week=view.findViewById(R.id.week);
        time=view.findViewById(R.id.time);
        search=view.findViewById(R.id.search);
        progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);


       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               progressBar.setVisibility(View.VISIBLE);

               final String[] a= getResources().getStringArray(R.array.time);
               String[] a1= getResources().getStringArray(R.array.week);

               final String aa=a[time.getSelectedItemPosition()];
               String aa1=a1[week.getSelectedItemPosition()];

               Query query = databaseReference.orderByChild("week").equalTo(aa1);

               query.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       if(!snapshot.exists()){
                           list.clear();
                           Toast.makeText(getActivity(),"No data found.",Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.INVISIBLE);
                       }else {
                           list.clear();
                           progressBar.setVisibility(View.INVISIBLE);
                           for(DataSnapshot dataSnapshot1:snapshot.getChildren()){

                               Empty_Room_Model empty_room_model=dataSnapshot1.getValue(Empty_Room_Model.class);

                               if(empty_room_model.getTime().equals(aa)){

                                   list.add(empty_room_model);
                               }

                           }

                           adapter = new EmptyRoomAdapter(getActivity(), list);
                           setHasOptionsMenu(true);
                           llm = new LinearLayoutManager(getContext());
                           recyclerView.setLayoutManager(llm) ;
                           llm.setReverseLayout(true);
                           llm.setStackFromEnd(true);
                           recyclerView.setItemAnimator(new DefaultItemAnimator());
                           recyclerView.setHasFixedSize(true);


                       }
                       recyclerView.setAdapter(adapter);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                       progressBar.setVisibility(View.INVISIBLE);

                   }
               });

           }
       });

       /* ViewHolderPost adapter = new ViewHolderPost(getActivity(), list);
        setHasOptionsMenu(true);
        llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm) ;
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);  */

        return view;
    }

}
