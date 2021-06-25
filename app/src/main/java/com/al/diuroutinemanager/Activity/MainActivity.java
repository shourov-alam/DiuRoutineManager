package com.al.diuroutinemanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.al.diuroutinemanager.Fragment.ClassFragment;
import com.al.diuroutinemanager.Fragment.Coun_Student;
import com.al.diuroutinemanager.Fragment.Coun_Teacher;
import com.al.diuroutinemanager.Fragment.CustomSearchFragment;
import com.al.diuroutinemanager.Fragment.EmptyRoomFragment;
import com.al.diuroutinemanager.Fragment.ExamRoutine;
import com.al.diuroutinemanager.Fragment.NotificationFragment;
import com.al.diuroutinemanager.Fragment.ProfileFragment;
import com.al.diuroutinemanager.R;
import com.al.diuroutinemanager.Model.StudentModel;
import com.al.diuroutinemanager.Model.TeacherModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;
  //  private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_CLASSES = "classes";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_EMPTY_ROOMS = "empty_rooms";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_CUSTOM_SEARCH = "custom_serach";
    private static final String TAG_EXAM_ROUTINE = "exam_routine";
    private static final String TAG_COUN_HOUR = "counselling_hour";
    public static String CURRENT_TAG = TAG_CLASSES;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles,sh,sect,lv,tr;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        sh=getResources().getStringArray(R.array.shift);
        sect=getResources().getStringArray(R.array.section);
        lv=getResources().getStringArray(R.array.level);
        tr=getResources().getStringArray(R.array.term);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        check();

        sharedPreferences=getSharedPreferences("Notification",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);

        //  loadNotificationAction();
        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });  */

        // load nav menu header data/notification dot
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        loadNotificationAction();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CLASSES;
            loadHomeFragment();
        }
    }

    private void loadNotificationAction() {

        Query query = FirebaseDatabase.getInstance().getReference("Notifications").orderByChild("timestamp").startAt(Long.parseLong(sharedPreferences.getString("value","0")));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    navigationView.getMenu().getItem(3).getActionView().setVisibility(View.VISIBLE);
                }else {

                    navigationView.getMenu().getItem(3).getActionView().setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website

        // loading header background image
// showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
        navigationView.getMenu().getItem(3).getActionView().setVisibility(View.INVISIBLE);

    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
       // toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // class
                 ClassFragment classFragment = new ClassFragment();
                return classFragment;
            case 1:
                // profile
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 2:
                // empty room
                EmptyRoomFragment emptyRoomFragment = new EmptyRoomFragment();
                return emptyRoomFragment;
            case 3:
                // notifications fragment
                NotificationFragment notificationsFragment = new NotificationFragment();
                return notificationsFragment;

            case 4:
                // notifications fragment
                ExamRoutine examRoutine = new ExamRoutine();
                return examRoutine;

            case 5:
                // search fragment
                CustomSearchFragment customSearchFragment = new CustomSearchFragment();
                return customSearchFragment;

            case 6:
                // counselling fragment

                if(flag==0){
                    check();
                }
                if(flag==1){
                    Coun_Teacher coun_teacher = new Coun_Teacher();
                    return coun_teacher;
                }else if(flag==2){
                    Coun_Student coun_student = new Coun_Student();
                    return coun_student;
                }else {
                    Toast.makeText(getApplicationContext(),"Connect With Internet",Toast.LENGTH_SHORT).show();
                }

            default:
                return new ClassFragment();

        }

        //return null;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_class:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_CLASSES;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_empty_rm:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_EMPTY_ROOMS;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATIONS;

                        //to save the last date the notification was viewed
                        editor.putString("value",String.valueOf(new Date().getTime()));
                        editor.commit();
                        navigationView.getMenu().getItem(3).getActionView().setVisibility(View.INVISIBLE);
                        break;

                    case R.id.nav_exam_routine:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_EXAM_ROUTINE;
                        break;

                    case R.id.nav_cus_search:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_CUSTOM_SEARCH;
                        break;

                    case R.id.nav_coun_hour:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_COUN_HOUR;
                        break;

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                       // startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        startActivity(new Intent(getApplicationContext(), About.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                       // startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                        drawer.closeDrawers();
                        finish();
                        return true;


                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_CLASSES;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected


        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.profile, menu);
        }

        // when fragment is notifications, load the menu created for notifications

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LogIn.class));
            return true;
        }  */

        if (id == R.id.edit) {

            LayoutInflater layoutInflater= LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.update_field,null);

            final EditText name =view.findViewById(R.id.name);
            final EditText ids =view.findViewById(R.id.id);
            final EditText initial =view.findViewById(R.id.initial);
            final Spinner shift =view.findViewById(R.id.shift);
            final Spinner sec =view.findViewById(R.id.section);
            final Spinner term =view.findViewById(R.id.term);
            final Spinner level =view.findViewById(R.id.level);

            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if( snapshot.getChildrenCount()<8){

                        TeacherModel teacherModel = snapshot.getValue(TeacherModel.class);

                        if(teacherModel != null){
                            name.setText(teacherModel.getName());
                            ids.setText(teacherModel.getMobile());
                            initial.setText(teacherModel.getInitial());
                            shift.setVisibility(View.INVISIBLE);
                            level.setVisibility(View.INVISIBLE);
                            term.setVisibility(View.INVISIBLE);
                            sec.setVisibility(View.INVISIBLE);
                        }

                    }else {

                        StudentModel studentModel = snapshot.getValue(StudentModel.class);
                        if(studentModel != null){

                            name.setText(studentModel.getName());
                            initial.setVisibility(View.GONE);
                            ids.setText(studentModel.getId());
                            shift.setSelection(shift_position(studentModel.getShift()));
                            sec.setSelection(sec_position(studentModel.getSection()));
                            level.setSelection(level_position(studentModel.getLevel()));
                            term.setSelection(term_position(studentModel.getTerm()));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Update Profile")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(shift.getVisibility()==View.VISIBLE){

                                    if(name.getText().toString().equals("") || ids.getText().toString().equals("")){

                                        Toast.makeText(getApplicationContext(),"Fill up all the fields properly",Toast.LENGTH_SHORT).show();
                                    }else {


                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DatabaseReference dRef= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());

                                                dRef.child("name").setValue(name.getText().toString());
                                                dRef.child("id").setValue(ids.getText().toString());
                                                dRef.child("shift").setValue(sh[shift.getSelectedItemPosition()]);
                                                dRef.child("section").setValue(sect[sec.getSelectedItemPosition()]);
                                                dRef.child("term").setValue(tr[term.getSelectedItemPosition()]);
                                                dRef.child("level").setValue(lv[level.getSelectedItemPosition()]);
                                                dRef.child("match").setValue(sh[shift.getSelectedItemPosition()]+lv[level.getSelectedItemPosition()]+
                                                        tr[term.getSelectedItemPosition()] +sect[sec.getSelectedItemPosition()]);

                                            }
                                        }).start();

                                    }
                                }else {
                                    DatabaseReference dRef= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
                                    dRef.child("name").setValue(name.getText().toString());
                                    dRef.child("mobile").setValue(ids.getText().toString());
                                    dRef.child("initial").setValue(initial.getText().toString());

                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).setView(view).show();


            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'

        // user is in notifications fragment
        // and selected 'Clear All'

        return super.onOptionsItemSelected(item);
    }

    private int term_position(String term) {

        int pos=0;
        String[] arr= getResources().getStringArray(R.array.term);

        for (int i=0;i<arr.length;i++){

            if(arr[i].equals(term)){
                break;
            }else {
                pos++;
            }

        }
        return pos;
    }



    private int level_position(String level) {

        int pos=0;
        String[] arr= getResources().getStringArray(R.array.level);

        for (int i=0;i<arr.length;i++){

            if(arr[i].equals(level)){
                break;
            }else {
                pos++;
            }

        }
        return pos;
    }

    private int sec_position(String sec) {

        int pos=0;
        String[] arr= getResources().getStringArray(R.array.section);

        for (int i=0;i<arr.length;i++){

            if(arr[i].equals(sec)){
                break;
            }else {
                pos++;
            }

        }
        return pos;
    }

    private int shift_position(String shift) {
        int pos;

        if(shift.equals("Day")){

            pos=0;
        }else {

            pos=1;
        }
       return pos;
    }

     void check(){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()<8){
                    flag=1;
                }else {
                    flag=2;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                flag=0;
            }
        });
    }

    // show or hide the fab
   /* private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }  */
}