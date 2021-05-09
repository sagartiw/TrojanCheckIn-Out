package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.UPC.Building;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SearchStudent extends AppCompatActivity {

    private Button Search;
    private Button Back;

    //a list to store all the products
    List<Building> buildingList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        Search = (Button)findViewById(R.id.button7);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Back = (Button)findViewById(R.id.back3);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchStudent.this, ManagerLanding.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(SearchStudent.this, StudentLanding.class);
                    startActivity(intent);
                }
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        buildingList = new ArrayList<>();


        //adding some items to our list
//        buildingList.add(
//                new Building(
//                        "Salvatori Computer Science Center",
//                        "SAL",
//                        100,
//                        null
//                ));
//
//        buildingList.add(
//                new Building(
//                        "Salvatori Computer Science Center",
//                        "SAL",
//                        100,
//                        null
//                ));
//
//        buildingList.add(
//                new Building(
//                        "Salvatori Computer Science Center",
//                        "SAL",
//                        100,
//                        null
//                ));

        //creating recyclerview adapter
        BuildingAdapter adapter = new BuildingAdapter(this, buildingList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}