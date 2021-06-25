package com.al.diuroutinemanager.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomSearchFragment extends Fragment {
View view;

    public CustomSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_custom_search, container, false);
        // Inflate the layout for this fragment

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        // add your fragments
        adapter.addFrag(new SerachTeacher(), "Teacher Routine");
        adapter.addFrag(new StudentFragment(), "Student Routine");

        // set adapter on viewpager
        viewPager.setAdapter(adapter);
        return view;
    }

}
