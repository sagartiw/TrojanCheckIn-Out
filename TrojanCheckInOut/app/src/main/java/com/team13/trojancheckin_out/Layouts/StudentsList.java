package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.ArrayList;
import java.util.List;

public class StudentsList extends AppCompatActivity {

    private Button Back;

    //a list to store all the products
    List<User> studentList;

    //the recyclerview
    RecyclerView recyclerView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        user = (User) getIntent().getSerializableExtra("PrevPageData");

        Back = (Button)findViewById(R.id.backer2);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsList.this, ManagerLanding.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        studentList = new ArrayList<>();


        //adding some items to our list
        studentList.add(
                new User(
                        "Mindy Diep",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Arian Memari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Sagar Tiwari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Kabir Samra",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Annika Oeth",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Elizabeth Moody",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Mindy Diep",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Arian Memari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Sagar Tiwari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Kabir Samra",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Annika Oeth",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Elizabeth Moody",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        //creating recyclerview adapter
        StudentAdapter adapter = new StudentAdapter(this, studentList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

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