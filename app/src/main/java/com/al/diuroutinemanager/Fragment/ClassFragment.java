package com.al.diuroutinemanager.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class ClassFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_class, container, false);
        return fragmentView;
    }

    public ClassFragment(){


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find views by id
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        // add your fragments
        adapter.addFrag(new Saturday(), "SAT");
        adapter.addFrag(new Sunday(), "SUN");
        adapter.addFrag(new Monday(), "MON");
        adapter.addFrag(new Tuesday(), "TUE");
        adapter.addFrag(new Wednesday(), "WED");
        adapter.addFrag(new Thursday(), "THU");
        adapter.addFrag(new Friday(), "FRI");


        // set adapter on viewpager
        viewPager.setAdapter(adapter);
    }
}
