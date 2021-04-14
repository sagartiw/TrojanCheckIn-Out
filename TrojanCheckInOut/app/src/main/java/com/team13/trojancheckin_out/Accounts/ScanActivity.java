package com.team13.trojancheckin_out.Accounts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Layouts.StudentLanding;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;


public class ScanActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView textView;
    private BarcodeDetector barcodeDetector;
    private AccountManipulator accountManipulator;
    //private BuildingManipulator buildingManipulator2 = new BuildingManipulator();
    private User user;
    private Building curr;
    private Map<User, String> sendIt;
    public static String buildingCheck;
    public static String checkInTime = "-1";
    private boolean notIncremented = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        user = (User) getIntent().getSerializableExtra("PrevPageData");

        surfaceView = findViewById(R.id.camera);
        textView = findViewById(R.id.text);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if (qrcode.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            // this should be a building acronym
                            sendIt = new HashMap<>();
                            String buildingAcronym = qrcode.valueAt(0).displayValue;
                            buildingCheck = qrcode.valueAt(0).displayValue;
                            System.out.println("hello i am me: " + buildingAcronym);

                            // check buildingAcronym against the database to find the building object
                            //Building match = buildingManipulator.getBuilding(buildingAcronym);

                            //String holder = qrcode.valueAt(0).displayValue.toString();

                            if (buildingManipulator == null) { return; }
                            Building match = buildingManipulator.getBuilding(buildingAcronym);
                            System.out.println("CHECK MATCH: " + match.getAbbreviation());
                            User user = accountManipulator.currentUser;

                            // check if user is checking in or out of a buildingtem.out: hello i am me: SAL
                            //    IM HERE: SAL
                            if (user.isInBuilding()) {
                                // if the building is the one they are in
                                if (match == user.getCurrentBuilding()) {
                                    // user is trying to check out
                                    match.removeStudent(user, user.getCurrentBuilding().getAbbreviation());
                                    user.setterCurrentBuilding(null);
                                    user.setInBuilding(false);
                                }
                                else {
                                    // send an error message that they need to check out of their current building before trying to check in somewhere else
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View popupView = inflater.inflate(R.layout.scan_qr_popup, null);
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
                                    popupWindow.showAtLocation(textView, Gravity.CENTER, 0, 0);

                                    // dismiss the popup window when touched
                                    closeButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    Toast.makeText(ScanActivity.this, "Check out of current building before trying to check in somewhere else!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else { // user is trying to check in
                                // check if there is capacity in the building
                                if (match.getCurrentCount() + 1 > match.getCapacity()) {
                                    // return error to the user saying they cannot check into this building because it is full
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View popupView = inflater.inflate(R.layout.scan_qr_popup2, null);
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
                                    popupWindow.showAtLocation(textView, Gravity.CENTER, 0, 0);

                                    // dismiss the popup window when touched
                                    closeButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                        }
                                    });

                                    Toast.makeText(ScanActivity.this, "Building is Full!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else { // check in the user
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

                                    // Remove from NA if there
                                    referenceBuildings.child("NA").child("currentStudents").child(user.getId()).removeValue();

                                    // Grab a the current time in the following format "1111".
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeZone(TimeZone.getTimeZone("PST"));
                                    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                                    int currentMinute = cal.get(Calendar.MINUTE);
                                    int currentDate = cal.get(Calendar.DATE);
                                    String currentDate1 = SimpleDateFormat.getDateInstance().format("ddMMyyyy");

                                    String min = Integer.toString(currentMinute);
                                    String hour = Integer.toString(currentHour);
                                    String date = Integer.toString(currentDate);

                                    if(currentMinute <= 9){
                                        min = "0" + Integer.toString(currentMinute);
                                    }

                                    if(currentHour <= 9){
                                        hour = "0" + Integer.toString(currentHour);
                                    }

                                    String time = hour + min + currentDate1;

                                    System.out.println("time:" + time);
                                    checkInTime = time;
                                    referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkInTime);
                                    user.setterInBuilding(true);

                                    // Go to where we checkout students and write this line of code: "referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkInTime + " " + checkOutTime);
                                }
                            }
                            Intent intent = new Intent(ScanActivity.this, StudentLanding.class);
                            intent.putExtra("PrevPageData", user);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}