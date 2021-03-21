package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;

public class ManagerLanding extends AppCompatActivity {

    //a list to store all the products
    List<Building> buildingList;

    //the recyclerview
    RecyclerView recyclerView;

    private Button Search;
    private Button Import;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_landing);

        Search = (Button)findViewById(R.id.button5);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerLanding.this, SearchStudent2.class);
                startActivity(intent);
            }
        });

        Import = (Button)findViewById(R.id.button4);

        Import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerLanding.this, SearchStudent2.class);
                startActivity(intent);
            }
        });
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        buildingList = new ArrayList<>();


        //adding some items to our list
        buildingList.add(
                new Building(
                        "Salvatori Computer Science Center",
                        "SAL",
                        100,
                        null,
                        null
                ));

        buildingList.add(
                new Building(
                        "Salvatori Computer Science Center",
                        "SAL",
                        100,
                        null,
                        null
                ));

        buildingList.add(
                new Building(
                        "Salvatori Computer Science Center",
                        "SAL",
                        100,
                        null,
                        null
                ));

        //creating recyclerview adapter
        BuildingAdapter adapter = new BuildingAdapter(this, buildingList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        menu.getMenu().add("Delete Account");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(ManagerLanding.this, StudentLanding.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(ManagerLanding.this, Startup.class);
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

        Button importCSV = (Button) findViewById(R.id.button4);
        importCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.csv_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
}