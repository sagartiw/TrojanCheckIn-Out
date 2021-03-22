package com.team13.trojancheckin_out.Layouts;

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
import java.util.List;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.currentBuildings;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.ManagerLanding.tracker;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class StudentsList extends AppCompatActivity {

    private Button Back;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private TextView buildingName;
    private Building building;

    //a list to store all the products
    List<User> studentList;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Back = (Button)findViewById(R.id.backer2);

        buildingName = (TextView) findViewById(R.id.textView32);
        building = (Building) getIntent().getSerializableExtra("PrevPageData");

        //NEED TO MAKE THIS OUR ACTUAL BUILDING OBJECT
        buildingName.setText(building.getAbbreviation());


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsList.this, ManagerLanding.class);
                intent.putExtra("PrevPageData", tracker);
                startActivity(intent);
            }
        });

        //initializing the productlist
        studentList = new ArrayList<>();
        System.out.println("BEFORE I ENTER");
        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
            @Override
            public void onCallback(Map<String, Building> map) {
                studentList = map.get(buildingName).getCurrentStudents();

                System.out.println("THIS IS A TEST" + currentBuildings.get(buildingName));

                //getting the recyclerview from xml
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                //creating recyclerview adapter
                StudentAdapter adapter = new StudentAdapter(getApplicationContext(), studentList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);
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


//        //adding some items to our list
//        studentList.add(
//                new User(
//                        "Mindy Diep",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Arian Memari",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Sagar Tiwari",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Kabir Samra",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Annika Oeth",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Elizabeth Moody",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Mindy Diep",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Arian Memari",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Sagar Tiwari",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Kabir Samra",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Annika Oeth",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));
//
//        studentList.add(
//                new User(
//                        "Elizabeth Moody",
//                        "mindydie@usc.edu",
//                        "hello",
//                        "photo",
//                        "123456789",
//                        true,
//                        null,
//                        null,
//                        "CSBA",
//                        "false"
//                ));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        menu.getMenu().add("Delete Account");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(StudentsList.this, StudentLanding.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(StudentsList.this, Startup.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Delete Account")){
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.delete_account_popup, null);
                    Button closeButton = (Button) popupView.findViewById(R.id.button12);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popupWindow.setElevation(20);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window token
                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
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