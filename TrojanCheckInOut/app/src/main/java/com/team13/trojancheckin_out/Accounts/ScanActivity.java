package com.team13.trojancheckin_out.Accounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Layouts.Register;
import com.team13.trojancheckin_out.Layouts.Startup;
import com.team13.trojancheckin_out.Layouts.StudentLanding;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.IOException;


public class ScanActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView textView;
    private BarcodeDetector barcodeDetector;
    private BuildingManipulator buildingManipulator;
    private AccountManipulator accountManipulator;
    private User user;
    private Building curr;

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
                            String buildingAcronym = qrcode.valueAt(0).displayValue;

                            // check buildingAcronym against the database to find the building object
                            Building match = buildingManipulator.getBuilding(buildingAcronym);
                            //String holder = qrcode.valueAt(0).displayValue.toString();

                            if (match == null) {
                                return;
                            }

                            User user = accountManipulator.currentUser;
                            // check if user is checking in or out of a building
                            if (user.isInBuilding()) {
                                // if the building is the one they are in
                                if (match == user.getCurrentBuilding()) {
                                    // user is trying to check out
                                    match.removeStudent(user);
                                    user.setCurrentBuilding(null);
                                    user.setInBuilding(false);

                                }
                                else {
                                    // send an error message that they need to check out of their current building before trying to check in somewhere else

                                }

                            }
                            else { // user is trying to check in

                                // check if there is capacity in the building
                                if (match.getCurrentCount() + 1 > match.getCapacity()) {
                                    // return error to the user saying they cannot check into this building because it is full



                                }
                                else { // check in the user
                                    match.addStudent(user);
                                    // set in building for curr user to be true so that they check in
                                    user.setCurrentBuilding(match);
                                    user.setInBuilding(true);

                                }

                            }


                            user.setCurrentBuilding(curr);
                            user.setInBuilding(true);
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