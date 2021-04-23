package com.team13.trojancheckin_out.Accounts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Layouts.StudentLanding;
import com.team13.trojancheckin_out.UPC.Building;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import static com.team13.trojancheckin_out.Accounts.ScanActivity.checkInTime;

import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;

public class QRCodeScanner extends AppCompatActivity {
    public static final int CAMERA_PERMISSION_CODE = 100;

    private Button camera;
    private Button scan;
    private User user;
    private boolean notIncremented = true;
    private AccountManipulator accountManipulator = new AccountManipulator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_logic);

        user = (User) getIntent().getSerializableExtra("PrevPageData");

        String error = (String) getIntent().getSerializableExtra("error");
        System.out.println("TESTING THIS " + error);
        if (error != null) {
            System.out.println("TESTING THIS 2");
            if (error != "") {
                // add popup
                System.out.println("TESTING THIS 3");
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
                popupWindow.showAtLocation(this.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
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
                        System.out.println("before building callback ");


                        System.out.println("after building callback ");


                        // Removes from current building DB
                        user.getCurrentBuilding().removeStudent(user);
                        System.out.println("CURR: " + user.getCurrentBuilding().getName());


                        Building b = new Building("Not in Building", "NA", 500, "");
                        user.setterCurrentBuilding(b);
                        referenceUsers.child(user.getId()).child("currentBuilding").setValue(b);


                        // Add to NA in DB
                        referenceBuildings.child("NA").child("currentStudents").child(user.getId()).setValue(user);


                        // Remove user's current building
                        user.setterInBuilding(false);
                        user.setInBuilding(false);



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


                        accountManipulator.currentUser = user;


                        Intent intent = new Intent(v.getContext(), StudentLanding.class);
                        intent.putExtra("PrevPageData", user);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRCodeScanner.this, ScanActivity.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);

                String error = (String) getIntent().getSerializableExtra("error");



            }
        });
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(QRCodeScanner.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRCodeScanner.this, new String[] {permission}, requestCode);
        }
        else {
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
