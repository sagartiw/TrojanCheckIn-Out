package com.team13.trojancheckin_out.Layouts;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class ManagerLanding extends AppCompatActivity {

    //a list to store all the products
    private List<Building> buildingList;
    private RecyclerView recyclerView;
    private Button Search;
    private User user;
    private TextView txt_path, successText;
    private TextView welcome;
    public static User tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_landing);

        user = (User) getIntent().getSerializableExtra("PrevPageData");
        tracker = user;
        buildingManipulator = new BuildingManipulator();
        Search = (Button)findViewById(R.id.button5);
        welcome = (TextView) findViewById(R.id.TextView16);

        welcome.setText("welcome " + user.getName());

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerLanding.this, SearchStudent2.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });


        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get current buildings
        buildingList = new ArrayList<>();

        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
            @Override
            public void onCallback(Map<String, Building> map) {
                for (Map.Entry<String, Building> checkBuilding : map.entrySet()) {
                    Building b = checkBuilding.getValue();
                    buildingList.add(new Building(b.getName(), b.getAbbreviation(), b.getCapacity(), b.getQRCode()));
                }

                //creating recyclerview adapter
                BuildingAdapter adapter = new BuildingAdapter(ManagerLanding.this, buildingList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

            }
        });

        ///Thread.wait(1000);

//        System.out.println("I am after " + buildingList.size());
//
//
//        // adding some items to our list
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
//
//

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        int imageRe = getResources().getIdentifier(user.getPhoto(), null, getPackageName());
        fab.setImageResource(imageRe);
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
                    intent.putExtra("PrevPageData", user);
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
                Button uploadFile = (Button) popupView.findViewById(R.id.button8);
                txt_path = (TextView) popupView.findViewById(R.id.fileName);
                successText = (TextView) popupView.findViewById(R.id.successText);

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

                uploadFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        myFileIntent.setType("*/*");
                        startActivityForResult(myFileIntent,10);
                    }
                });


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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    String dataPath = data.getData().getPath();
                    String path = dataPath.replace("/document/raw:", "");
                    txt_path.setText(path);
                    successText.setText("Upload successful!");
                    //BuildingManipulator buildingManipulator = new BuildingManipulator();
                    File file = new File(path);
                    buildingManipulator.processCSV(file);
                }
                break;
        }
    }
}

