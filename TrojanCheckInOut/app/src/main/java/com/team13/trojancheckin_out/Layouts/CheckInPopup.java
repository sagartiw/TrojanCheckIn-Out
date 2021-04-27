package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team13.trojancheckin_out.Accounts.QRCodeScanner;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.ScanActivity;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class CheckInPopup extends AppCompatActivity {
    private User user;
    private Building match;
    private AccountManipulator accountManipulator = new AccountManipulator();
    public static String checkInTime = "-1";
    public static String checkOutTime = "-1";
    private boolean notIncremented = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_popup);

        user = (User) getIntent().getSerializableExtra("PrevPageData");
        match = (Building) getIntent().getSerializableExtra("building");

        Button cancel = (Button) findViewById(R.id.buttonCancel);
        Button submit = (Button) findViewById(R.id.buttonSubmit);

        TextView building = (TextView)findViewById(R.id.textView26);
        building.setText(match.getName());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirect to qr code scanner
                Intent intent = new Intent(CheckInPopup.this, QRCodeScanner.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add student to this building

                System.out.println("Track user 4" + match);
                match.addStudent(user);


                // set in building for curr user to be true so that they check in
                System.out.println("SCAN ID: " + user.getId());
                System.out.println("MATCH " + match.getAbbreviation());
                user.setterCurrentBuilding(match);
                System.out.println("MATCH 2" + match.getAbbreviation());

                // Update count + 1
                buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
                    @Override
                    public void onCallback(Map<String, Building> map) {
                        int count = map.get(match.getAbbreviation()).getCurrentCount();
                        if (notIncremented) {
                            count = count+1;
                            notIncremented = false;
                            referenceBuildings.child(match.getAbbreviation()).child("currentCount").setValue(count);
                        }
                    }
                });
//                String count = referenceBuildings.child(match.getAbbreviation()).child("currentCount").getKey();
//                int numCount = Integer.parseInt(count);
//                if (notIncremented) {
//                    numCount++;
//                    notIncremented = false;
//                    referenceBuildings.child(match.getAbbreviation()).child("currentCount").setValue(numCount);
//                }

                // Remove from NA if there
                referenceBuildings.child("NA").child("currentStudents").child(user.getId()).removeValue();

                // Grab a the current time in the following format "1111".
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("PST"));
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);
                int currentDate = cal.get(Calendar.DATE);
                Date dater = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                System.out.println("current min is "+ currentMinute);
                System.out.println("current hour is "+ currentHour);
                System.out.println("current date is "+ currentDate);
                //String currentDate1 = SimpleDateFormat.getDateInstance().format("yyyy-MM-dd");

                System.out.println("currentDate is "+ sdf.format(dater).toString());
                String min = Integer.toString(currentMinute);
                String hour = Integer.toString(currentHour);
                //String date = Integer.toString(currentDate);
                String date = sdf.format(dater).toString();

                if(currentMinute <= 9){
                    min = "0" + Integer.toString(currentMinute);
                }

                if(currentHour <= 9){
                    hour = "0" + Integer.toString(currentHour);
                }

                String time = hour + min + "@" + date;

                System.out.println("time:" + time);
                checkInTime = time;

                // Update full time in DB
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        map.get(user.getId()).getHistory();
                        System.out.println("inside AM");
                        boolean a = false;
                        for (Map.Entry<String, String> e : map.get(user.getId()).getHistory().entrySet()) {
                            if(e.getKey().equalsIgnoreCase(match.getAbbreviation())) {
                                String currentTime = e.getValue();
                                System.out.println("CURRENT TIME: " + currentTime + " @ " + e.getKey());
                                System.out.println("CHECK IN TIME: " + checkInTime);
                                referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue("," + currentTime + " " + checkInTime);
                                a = true;
                                break;
                            }
                        }
                        if(!a){
                            referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkInTime);
                        }
                    }
                });

//                String currentTime = e.getValue();
//                System.out.println("CURRENT TIME: " + currentTime + " @ " + e.getKey());
//                System.out.println("CHECK IN TIME: " + checkInTime);
//                referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue("," + currentTime + " " + checkInTime);
//                a = true;

                user.setterInBuilding(true);
                user.setterCurrentBuilding(match);

                accountManipulator.currentUser = user;


                // redirect to student landing
                Intent intent = new Intent(CheckInPopup.this, StudentLanding.class);

                intent.putExtra("PrevPageData", user);
                startActivity(intent);


            }
        });


    }
}