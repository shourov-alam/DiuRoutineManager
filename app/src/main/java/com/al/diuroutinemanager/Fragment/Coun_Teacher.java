package com.al.diuroutinemanager.Fragment;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.al.diuroutinemanager.Model.TeacherCounModel;
import com.al.diuroutinemanager.Model.TeacherModel;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.ViewHolderCounselling;
import com.google.android.gms.tasks.OnSuccessListener;
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
public class Coun_Teacher extends Fragment {

Spinner dayOfWeek,from_hour,from_minute,from_type,to_hour,to_minute,to_type;

  String[] weeks,hour,minute,type;
  Button enter;

  ArrayList<TeacherCounModel> arrayList;

  public String initial;
  RecyclerView recycle;
  LinearLayoutManager llm;
  ViewHolderCounselling viewHolderCounselling;

  DatabaseReference databaseReference,databaseReference1;

    public Coun_Teacher() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_coun__teacher, container, false);

        databaseReference=FirebaseDatabase.getInstance().getReference("Counselling");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Users");

        arrayList=new ArrayList<>();
        dayOfWeek=view.findViewById(R.id.sp_week);
        from_hour=view.findViewById(R.id.sp_from_hour);
        from_minute=view.findViewById(R.id.sp_from_minute);
        from_type=view.findViewById(R.id.sp_from_type);
        to_hour=view.findViewById(R.id.sp_to_hour);
        to_minute=view.findViewById(R.id.sp_to_minute);
        to_type=view.findViewById(R.id.sp_to_type);

        enter=view.findViewById(R.id.btn_enter);

        recycle=view.findViewById(R.id.recycleView);

        llm = new LinearLayoutManager(getContext());
        recycle.setLayoutManager(llm) ;

        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setHasFixedSize(true);

        weeks=getResources().getStringArray(R.array.week);
        hour=getResources().getStringArray(R.array.time_hour);
        minute=getResources().getStringArray(R.array.time_minute);
        type=getResources().getStringArray(R.array.time_type);

        final Query query = databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    TeacherCounModel teacherCounModel =dataSnapshot.getValue(TeacherCounModel.class);
                    arrayList.add(teacherCounModel);
                }
                viewHolderCounselling =new ViewHolderCounselling(getActivity(),arrayList);

                recycle.setAdapter(viewHolderCounselling);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

                // viewHolderCounselling.notifyDataSetChanged();
                //// recyclerView.notify();



        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String final_day=weeks[dayOfWeek.getSelectedItemPosition()];

                String final_from_hour=hour[from_hour.getSelectedItemPosition()];
                String final_from_minute=minute[from_minute.getSelectedItemPosition()];
                String final_from_type=type[from_type.getSelectedItemPosition()];

                String final_to_hour=hour[to_hour.getSelectedItemPosition()];
                String final_to_minute=minute[to_minute.getSelectedItemPosition()];
                String final_to_type=type[to_type.getSelectedItemPosition()];

                final String margeTime=final_from_hour+":"+final_from_minute+" "+final_from_type
                        +" - "+final_to_hour+":"+final_to_minute+" "+final_to_type;



                databaseReference1.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            TeacherModel model=snapshot.getValue(TeacherModel.class);

                            initial=model.getInitial();
                            String name=model.getName();
                            String rootUid=databaseReference.push().getKey();

                            TeacherCounModel teacherCounModel= new TeacherCounModel(name,initial,margeTime,final_day,FirebaseAuth.getInstance().getUid(),rootUid);

                            databaseReference.child(rootUid)
                                    .setValue(teacherCounModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(),"Successfully Posted",Toast.LENGTH_SHORT).show();
                                }
                            });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return view;
    }

}
