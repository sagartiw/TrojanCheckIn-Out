package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team13.trojancheckin_out.Accounts.QRCodeScanner;
import com.team13.trojancheckin_out.Accounts.R;
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

public class CheckOutPopup extends AppCompatActivity {
    private User user;
    private Building match;
    private AccountManipulator accountManipulator = new AccountManipulator();
    public static String checkInTime = "-1";
    public static String checkOutTime = "-1";
    private boolean notIncremented = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_popup);

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
                Intent intent = new Intent(CheckOutPopup.this, QRCodeScanner.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isInBuilding()) {
                    // if the building is the one they are in
                    if (match == user.getCurrentBuilding()) {


                        // user is trying to check out
                        match.removeStudent(user);
                        user.setterCurrentBuilding(null);
                        user.setterInBuilding(false);

                        // Update count - 1
                        buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
                            @Override
                            public void onCallback(Map<String, Building> map) {
                                int count = map.get(user.getCurrentBuilding().getAbbreviation()).getCurrentCount();
                                if (notIncremented) {
                                    count = count - 1;
                                    notIncremented = false;
                                    referenceBuildings.child(user.getCurrentBuilding().getAbbreviation()).child("currentCount").setValue(count);
                                }
                            }
                        });
                        System.out.println("updated count" + referenceBuildings.child(user.getCurrentBuilding().getAbbreviation()).child("currentCount").get().toString());


                        //System.out.println("b" + b.getName().toString());

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeZone(TimeZone.getTimeZone("PST"));
                        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = cal.get(Calendar.MINUTE);
                        int currentDate = cal.get(Calendar.DATE);
                        Date dater = cal.getTime();
                        //WE NEED TO CHECK IF THIS IS VALID. IF SO, WE CAN USE AN INT. Old Version: dd.MM.yyyy
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        System.out.println("current min is " + currentMinute);
                        System.out.println("current hour is " + currentHour);
                        System.out.println("current date is " + currentDate);
                        //String currentDate1 = SimpleDateFormat.getDateInstance().format("yyyy-MM-dd");

                        System.out.println("currentDate is " + sdf.format(dater).toString());
                        String min = Integer.toString(currentMinute);
                        String hour = Integer.toString(currentHour);
                        //String date = Integer.toString(currentDate);
                        String date = sdf.format(dater);

                        if (currentMinute <= 9) {
                            min = "0" + Integer.toString(currentMinute);
                        }

                        if (currentHour <= 9) {
                            hour = "0" + Integer.toString(currentHour);
                        }

                        String time = hour + min + "@" + date + ", ";

                        System.out.println("time:" + time);
                        checkOutTime = time;
                        referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkOutTime);


                        // Removes from current building DB
                        user.getCurrentBuilding().removeStudent(user);
                        System.out.println("CURR: " + user.getCurrentBuilding().getName());

                        // Remove user's current building
                        user.setInBuilding(false);
                        System.out.println("removed");
                        Building b = new Building("Not in Building", "NA", 500, "");
                        referenceUsers.child(user.getId()).child("currentBuilding").setValue(b);
                        user.setterCurrentBuilding(b);
                    }
                }

                // redirect to student landing
                Intent intent = new Intent(CheckOutPopup.this, StudentLanding.class);

                intent.putExtra("PrevPageData", user);
                startActivity(intent);


            }
        });


    }
}