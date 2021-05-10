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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;

import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
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
    private AccountManipulator accountManipulator = new AccountManipulator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manager_landing);


        user = (User) getIntent().getSerializableExtra("PrevPageData");
        new DownloadImageTask((ImageView)findViewById(R.id.fab)).execute(user.getPhoto());

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

        // SET RECYCLERVIEW ADAPTER HERE BEFORE SO WE DONT HAVE TO WAIT FOR CALLBACK
        //creating recyclerview adapter
        BuildingAdapter adapter1 = new BuildingAdapter(ManagerLanding.this, buildingList);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter1);

        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
            @Override
            public void onCallback(Map<String, Building> map) {
                for (Map.Entry<String, Building> checkBuilding : map.entrySet()) {
                    Building b = checkBuilding.getValue();
                    if (b.getAbbreviation().equalsIgnoreCase("NA")) continue;
                    buildingList.add(new Building(b.getName(), b.getAbbreviation(), b.getCapacity(), b.getCurrentCount(), b.getQRCode()));
                }

                // UPDATE RECYCLERVIEW ADAPTER
                //creating recyclerview adapter
                BuildingAdapter adapter2 = new BuildingAdapter(ManagerLanding.this, buildingList);
                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter2);
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
        menu.getMenu().add("Edit Profile");
        menu.getMenu().add("Student View");

        menu.getMenu().add("Sign Out");
        //menu.getMenu().add("Delete Account");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(ManagerLanding.this, StudentLanding.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(ManagerLanding.this, EditProfile.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(ManagerLanding.this, Startup.class);
                    //startActivity(new Intent(ManagerLanding.this, Startup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    user = null;
                    currentUser = null;
                    //intent.putExtra("PrevPageData", currentUser);
                    intent.putExtra("PrevPageData", user);

                    startActivity(intent);

                   // finishAndRemoveTask();
                    //finishAffinity();
                }
//                if(item.getTitle().toString().equals("Delete Account")){
//                    // inflate the layout of the popup window
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View popupView = inflater.inflate(R.layout.delete_account_popup, null);
//                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
//                    Button submitButton = (Button) popupView.findViewById(R.id.button10);
//
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
//
//                    // delete account when submit is pressed
//                    submitButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // call delete account
//                            accountManipulator.deleteAccount(user);
//
//                            //popupWindow.dismiss();
//
//                            // go back to startup page
//                            Intent intent = new Intent(v.getContext(), Startup.class);
//                            //intent.putExtra("PrevPageData", user);
//                            v.getContext().startActivity(intent);
//
////                            Intent intent = new Intent(ManagerLanding.this, Startup.class);
////                            startActivity(intent);
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

        Button addBuilding = (Button) findViewById(R.id.button);
        addBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_building_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                Button submit = (Button) popupView.findViewById(R.id.button11);
                Button submitDelete = (Button) popupView.findViewById(R.id.button3);
                EditText name = (EditText) popupView.findViewById(R.id.editTextTextPassword);
                EditText abbrev = (EditText) popupView.findViewById(R.id.editTextTextPassword4);
                EditText cap = (EditText) popupView.findViewById(R.id.editTextTextPassword5);
                EditText deleteAbb = (EditText) popupView.findViewById(R.id.editTextTextPassword6);

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

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create new building
                        String w = cap.getText().toString();
                        int x = Integer.parseInt(w);
                        Building temp = new Building(name.getText().toString(), abbrev.getText().toString(), x, 0, "");
                        referenceBuildings.child(temp.getAbbreviation()).setValue(temp);
                        popupWindow.dismiss();
                    }
                });

                submitDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Check if there are any students in this building
                        accountManipulator.getAllAccounts(new MyUserCallback() {
                              @Override
                              public void onCallback(Map<String, User> map) {
                                  System.out.println("MANAGER LANDING ACCOUNT MANIP CALLBACK 1 - DELETE BUILDING");
                                  for (Map.Entry<String, User> checkUser : map.entrySet()) {
                                      if (checkUser.getValue().getCurrentBuilding().getAbbreviation().equalsIgnoreCase(deleteAbb.getText().toString())) {
                                          Toast.makeText(ManagerLanding.this, "There are students in the building!", Toast.LENGTH_SHORT).show();
                                          popupWindow.dismiss();
                                          return;
                                      }
                                  }
                                  referenceBuildings.child(deleteAbb.getText().toString()).removeValue();
                                  popupWindow.dismiss();
                              }
                        });
                    }
                });


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
                    // successText.setText("Upload successful!");
                    //BuildingManipulator buildingManipulator = new BuildingManipulator();
                    File file = new File(path);

                    // Pasted code
                    Scanner scan = null;
                    try {
                        scan = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();

                        // CSV files will put unwanted double quotes around each line of data
                        line = line.replaceAll("\"", "");

                        // <Full Name>|<Abbreviation>|<Capacity>|<Action>
                        // <Action>: a = add, e = edit, d = delete
                        String[] data1 = line.split("@");

                        // Error check
                        if ((data1.length != 4) ||
                                (data1[1].length() != 3) ||
                                (Integer.parseInt(data1[2]) <= 0) ||
                                !(data1[3].equalsIgnoreCase("a") || data1[3].equalsIgnoreCase("e" )|| data1[3].equalsIgnoreCase( "d"))) {
                            successText.setText("Upload failed. CSV file is incorrectly formatted.");
                            scan.close();
                            return;
                        }
                    }
                    scan.close();

                    try {

                        Scanner scan1 = new Scanner(file);
                        while (scan1.hasNextLine()) {
                            String line = scan1.nextLine();
                            line = line.replaceAll("\"","");
                            String[] data2 = line.split("@");
                            String action = data2[3];

                            System.out.println("DATA ARRAY: " + data.toString());
                            System.out.println("ACTION IS: " + action);
                            // Add building
                            if (action.equalsIgnoreCase("a")) {
                                System.out.println("ACTION: a");
                                successText.setText("Upload successful!");
                                referenceBuildings.child(data2[1]).setValue(new Building(data2[0], data2[1], Integer.parseInt(data2[2]), 0, ""));
                            }
                            else if (action.equalsIgnoreCase("e")) {
                                successText.setText("Upload successful!");
                                referenceBuildings.child(data2[1]).child("capacity").setValue(Integer.parseInt(data2[2]));
                            }
                            else if (action.equalsIgnoreCase("d")) {
                                boolean successful = true;
                                if (successful) {
                                    accountManipulator.getAllAccounts(new MyUserCallback() {
                                        @Override
                                        public void onCallback(Map<String, User> map) {
                                            System.out.println("MANAGER LANDING ACCOUNT MANIP CALLBACK 2 - PARSE CSV");
                                            for (Map.Entry<String, User> checkUser : map.entrySet()) {
                                                if (checkUser.getValue().getCurrentBuilding().getAbbreviation().equalsIgnoreCase(data2[1])) {
                                                    successText.setText("Upload failed. There are students in the building(s) you are trying to delete.");
                                                    return;
                                                }
                                            }
                                            successText.setText("Upload successful!");
                                            referenceBuildings.child(data2[1]).removeValue();
                                        }
                                    });
                                    successful = false;
                                }
                            }
                        }
                        scan.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                break;
        }
    }
}

