package com.team13.trojancheckin_out.Layouts;

import android.animation.BidirectionalTypeConverter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.currentBuildings;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.ManagerLanding.tracker;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class StudentsList extends AppCompatActivity {

    private Button Back;
    private RecyclerView recyclerView;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private TextView buildingName;
    private Building building;
    private TextView welcome;

    //a list to store all the products
    private List<User> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        User user = accountManipulator.currentUser;


        Back = (Button)findViewById(R.id.backer2);

        buildingName = (TextView) findViewById(R.id.textView32);
        building = (Building) getIntent().getSerializableExtra("PrevPageData");

        //NEED TO MAKE THIS OUR ACTUAL BUILDING OBJECT
        buildingName.setText(building.getAbbreviation());

        // make welcome message empty
        welcome = (TextView) findViewById(R.id.textView30);

        welcome.setText(" ");


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsList.this, ManagerLanding.class);
                intent.putExtra("PrevPageData", tracker);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentsList.this, LinearLayoutManager.VERTICAL, false));

        System.out.println("CHANGE");

        studentList = new ArrayList<>();

        // get students list - idk why this doesnt work
        //studentList = building.getCurrentStudents();

        StudentAdapter adapter = new StudentAdapter(StudentsList.this, studentList);
        recyclerView.setAdapter(adapter);



        //creating recyclerview adapter
        //StudentAdapter adapter1 = new StudentAdapter(StudentsList.this, studentList);




        accountManipulator.getAllAccounts(new MyUserCallback() {
            @Override
            public void onCallback(Map<String, User> map) {
                for (Map.Entry<String, User> e : map.entrySet()) {
                    User user = e.getValue();
                    if (user.isInBuilding()) {
                        if (user.getCurrentBuilding().getAbbreviation().equals(building.getAbbreviation())){
                            studentList.add(new User(user.getName(),user.getEmail(),user.getPassword(),user.getPhoto(),user.getId(),user.isInBuilding(),user.getCurrentBuilding(),user.getHistory(),user.getMajor(),user.isManager(), user.isDeleted()));
                            System.out.println("USER NAME: " + user.getName());
                        }
                    }

                }
                //getting the recyclerview from xml
//                recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(StudentsList.this));
//                StudentAdapter adapter = new StudentAdapter(StudentsList.this, studentList);
//                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                System.out.println("NOTIFY! " + adapter.getItemCount());

            }
        });

        /*
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        */
        //studentList = currentBuildings.get(buildingName).getCurrentStudents();

//        DatabaseReference r = referenceBuildings.child(building.getAbbreviation()).child("currentStudents");
//        for (DataSnapshot ds : r.get().getResult().getChildren()) {
//            User user = ds.getValue(User.class);
//            studentList.add(user);
//        }


//        //trying to add cards
//        accountManipulator.getAllAccounts(new MyUserCallback() {
//            @Override
//            public void onCallback(Map<String, User> map) {
//                for (Map.Entry<String, User> checkUser : map.entrySet()) {
//                    System.out.println(checkUser.getValue());
//                    if (checkUser.getValue().getCurrentBuilding().equals("BSR")) {
//                        studentList.add(checkUser.getValue());
//                    }
//                }
//            }
//        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Edit Profile");

        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
       // menu.getMenu().add("Delete Account");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(StudentsList.this, StudentLanding.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(StudentsList.this, EditProfile.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(StudentsList.this, Startup.class);
                    startActivity(intent);
                }
//                if(item.getTitle().toString().equals("Delete Account")){
//                    // inflate the layout of the popup window
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View popupView = inflater.inflate(R.layout.delete_account_popup, null);
//                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
//
//                    // create the popup window
//                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    boolean focusable = true; // lets taps outside the popup also dismiss it
//                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    popupWindow.setElevation(20);
//
//                    // show the popup window
//                    // which view you pass in doesn't matter, it is only used for the window token
//                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
//
//                    // dismiss the popup window when touched
//                    closeButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//                }
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });
    }
}