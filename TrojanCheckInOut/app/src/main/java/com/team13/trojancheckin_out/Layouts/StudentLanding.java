package com.team13.trojancheckin_out.Layouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team13.trojancheckin_out.Accounts.QRCodeScanner;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.team13.trojancheckin_out.Accounts.ScanActivity.checkInTime;
import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;


public class StudentLanding extends AppCompatActivity {
    private Button SignOut;
    private Button CheckOut;
    private Button Scan;
    private Button History;
    private User user;
    private FloatingActionButton soFab;
    private TextView welcomeMessage;
    private TextView Name;
    private TextView ID;
    private TextView Major;
    private TextView currBuilding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private TextView welcomeName;
    private boolean notIncremented = true;
    private RecyclerView recyclerView;
    private List<History> historyList;
    private AccountManipulator accountManipulator = new AccountManipulator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);

        SignOut = (Button)findViewById(R.id.signOut);
        Scan = (Button)findViewById(R.id.Scan);
        CheckOut = (Button)findViewById(R.id.checkOut);
        History = (Button)findViewById(R.id.checkOut2);
        user = (User) getIntent().getSerializableExtra("PrevPageData");

        System.out.println("Track user 1" + user);
        soFab = (FloatingActionButton)findViewById(R.id.fab);
        welcomeName = (TextView)findViewById(R.id.welcomeMessage);
        System.out.println("NAME: " + user.getName());
        welcomeName.setText("Welcome " + user.getName());

        welcomeMessage = (TextView)findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("welcome " + user.getName());
        Name = (TextView)findViewById(R.id.name);
        Name.setText(user.getName());
        ID = (TextView)findViewById(R.id.id);
        ID.setText(user.getId());
        Major = (TextView)findViewById(R.id.id2);
        Major.setText(user.getMajor());
        currBuilding = (TextView)findViewById(R.id.buildingName);

        System.out.println("TESTER : " + user.getCurrentBuilding().getAbbreviation());
        System.out.println("TESTER : " + user.isInBuilding());

        if(user.isInBuilding() == true){

            currBuilding.setText(user.getCurrentBuilding().getAbbreviation());
        }
        else {
            currBuilding.setText("USC");
        }

//        if(user.getCurrentBuilding().getName().toString().equals("NA")){
//            currBuilding.setText("NA");
//            user.setterInBuilding(false);
//        }




        StorageReference pfp = FirebaseStorage.getInstance().getReference().child(user.getPhoto());

        System.out.println("This is the user photo in student landing" + user.getPhoto());
     //   Glide.with(getApplicationContext()).load(pfp).into(soFab);

//        int imageRe = getResources().getIdentifier(user.getPhoto(), null, getPackageName());
//        soFab.setImageResource(imageRe);

//        SignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentUser = null;
//                Intent intent = new Intent(StudentLanding.this, Startup.class);
//                intent.putExtra("PrevPageData", user);
//                startActivity(intent);
//            }
//        });


        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, QRCodeScanner.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Edit Profile");
        if(user.isManager().equals("true")) {
            menu.getMenu().add("Manager View");
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here

                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(StudentLanding.this, EditProfile.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }

                if(item.getTitle().toString().equals("Manager View")){
                    Intent intent = new Intent(StudentLanding.this, ManagerLanding.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }

                Log.d("menu title: ", item.getTitle().toString());
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });

        Button checkout = (Button) findViewById(R.id.checkOut);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.check_out_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);
                Button nameButton = (Button) popupView.findViewById(R.id.button8);
                Button submit = (Button) popupView.findViewById(R.id.button10);

                nameButton.setText(user.getCurrentBuilding().getName());

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
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {


                        // Update count - 1


                        Calendar cal = Calendar.getInstance();
                        cal.setTimeZone(TimeZone.getTimeZone("PST"));
                        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = cal.get(Calendar.MINUTE);
                        //int currentDate = cal.get(Calendar.DATE);
                        Date dater = cal.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                        String min = Integer.toString(currentMinute);
                        String hour = Integer.toString(currentHour);
                        //String date = Integer.toString(currentDate);

                        if(currentMinute <= 9){
                            min = "0" + Integer.toString(currentMinute);
                        }

                        if(currentHour <= 9){
                            hour = "0" + Integer.toString(currentHour);
                        }

                        //String currentDate1 = SimpleDateFormat.getDateInstance().format("ddMMyyyy");
                        String date = sdf.format(dater).toString();
                        String time = hour + min + "@" + date;
                        System.out.println("time:" + time);
                        String checkOutTime = time;

                        System.out.println(checkInTime);

                        referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkInTime + " " + checkOutTime);


                        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
                            @Override
                            public void onCallback(Map<String, Building> map) {
                                int count = map.get(user.getCurrentBuilding().getAbbreviation()).getCurrentCount();
                                if (notIncremented) {
                                    count = count-1;
                                    notIncremented = false;
                                    referenceBuildings.child(user.getCurrentBuilding().getAbbreviation()).child("currentCount").setValue(count);
                                }
                            }
                        });



                        // Removes from current building DB
                        System.out.println("Track user 2" + user);
                        user.getCurrentBuilding().removeStudent(user);
                        System.out.println("CURR: " + user.getCurrentBuilding().getName());


                        Building b = new Building("Not in Building", "NA", 500, "");
                        user.setterCurrentBuilding(b);
                        referenceUsers.child(user.getId()).child("currentBuilding").setValue(b);


                        // Add to NA in DB
                        referenceBuildings.child("NA").child("currentStudents").child(user.getId()).setValue(user);

                        currBuilding.setText("NA");

                        // Remove user's current building
                        user.setterInBuilding(false);
                        user.setInBuilding(false);



                        accountManipulator.currentUser = user;


                        Intent intent = new Intent(v.getContext(), StudentLanding.class);
                        intent.putExtra("PrevPageData", user);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                System.out.println("HERE!!!!");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.student_history_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                TextView nameText = (TextView) popupView.findViewById(R.id.nameTitle4);

                nameText.setText(user.getName());

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

                //getting the recyclerview from xml
                recyclerView = (RecyclerView) popupView.findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                //get current buildings
                historyList = new ArrayList<>();

                //creating recyclerview adapter
                HistoryAdapter adapter = new HistoryAdapter(view.getContext(), historyList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        map.get(user.getId()).getHistory();
                        for (Map.Entry<String, String> e : map.get(user.getId()).getHistory().entrySet()) {
                            String[] comp = e.getValue().split(" ");
                            String [] components = new String[2];
                            components[0] = comp[0];
                            if(comp.length < 2)
                            {
                                components[1] = " ";
                            }
                            else
                            {
                                components[1] = comp[1];
                            }
                            History history = new History(e.getKey(), "In: " + components[0], "Out: " + components[1]);
                            System.out.println("HISTORY: " + e.getKey() + components[0] + components[1]);
                            historyList.add(history);
                        }

                        adapter.notifyDataSetChanged();
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

        //Button signout = (Button) findViewById(R.id.signOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.sign_out_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);
                Button submit = (Button) popupView.findViewById(R.id.button10);

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

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentUser = null;
                        Intent intent = new Intent(v.getContext(), Startup.class);
                        intent.putExtra("PrevPageData", currentUser);
                        v.getContext().startActivity(intent);
                        //startActivity(new Intent(v.getContext(), Startup.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        //finishAndRemoveTask();
                        //finishAffinity();
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
}