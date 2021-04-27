package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.UPC.Building;

public class ScanReceiver extends AppCompatActivity {
    private User user;
    private Building match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receiver);

        user = (User) getIntent().getSerializableExtra("PrevPageData");
        match = (Building) getIntent().getSerializableExtra("building");


        // check if user is checking in or out of a building
        if (user.isInBuilding()) {
            // if the building is the one they are in
            if (match == user.getCurrentBuilding()) {

                // ADD POPUP TO ASK USER IF THEY WANT TO CHECK OUT
                System.out.println("Scan Receiver: check out case");

//
//                // user is trying to check out
//                match.removeStudent(user);
//                user.setterCurrentBuilding(null);
//                user.setterInBuilding(false);
//
//                // Update count - 1
//                buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
//                    @Override
//                    public void onCallback(Map<String, Building> map) {
//                        int count = map.get(user.getCurrentBuilding().getAbbreviation()).getCurrentCount();
//                        if (notIncremented) {
//                            count = count-1;
//                            notIncremented = false;
//                            referenceBuildings.child(user.getCurrentBuilding().getAbbreviation()).child("currentCount").setValue(count);
//                        }
//                    }
//                });
//                System.out.println("updated count" + referenceBuildings.child(user.getCurrentBuilding().getAbbreviation()).child("currentCount").get().toString());
//                //System.out.println("b" + b.getName().toString());
//
//                Calendar cal = Calendar.getInstance();
//                cal.setTimeZone(TimeZone.getTimeZone("PST"));
//                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
//                int currentMinute = cal.get(Calendar.MINUTE);
//                int currentDate = cal.get(Calendar.DATE);
//                Date dater = cal.getTime();
//                //WE NEED TO CHECK IF THIS IS VALID. IF SO, WE CAN USE AN INT. Old Version: dd.MM.yyyy
//                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//                System.out.println("current min is "+ currentMinute);
//                System.out.println("current hour is "+ currentHour);
//                System.out.println("current date is "+ currentDate);
//                //String currentDate1 = SimpleDateFormat.getDateInstance().format("yyyy-MM-dd");
//
//                System.out.println("currentDate is "+ sdf.format(dater).toString());
//                String min = Integer.toString(currentMinute);
//                String hour = Integer.toString(currentHour);
//                //String date = Integer.toString(currentDate);
//                String date = sdf.format(dater);
//
//                if(currentMinute <= 9){
//                    min = "0" + Integer.toString(currentMinute);
//                }
//
//                if(currentHour <= 9){
//                    hour = "0" + Integer.toString(currentHour);
//                }
//
//                String time = hour + min + "@" + date + ", ";
//
//                System.out.println("time:" + time);
//                checkOutTime = time;
//                referenceUsers.child(user.getId()).child("history").child(user.getCurrentBuilding().getAbbreviation()).setValue(checkOutTime);
//
//                // Removes from current building DB
//                user.getCurrentBuilding().removeStudent(user);
//                System.out.println("CURR: " + user.getCurrentBuilding().getName());
//
//                // Remove user's current building
//                user.setInBuilding(false);
//                System.out.println("removed");
//                Building b = new Building("Not in Building", "NA", 500, "");
//                referenceUsers.child(user.getId()).child("currentBuilding").setValue(b);
//                user.setterCurrentBuilding(b);

            }
            else {
                System.out.println("Scan Receiver: trying to check in while already in a different building case");

                // send an error message that they need to check out of their current building before trying to check in somewhere else

//                                    String error ="Check out of current building before trying to check in somewhere else!";
//                                    Intent intent = new Intent(ScanActivity.this, QRCodeScanner.class);
//
//                                    intent.putExtra("error", error);
//                                    startActivity(intent);

//
//                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                                    View popupView = inflater.inflate(R.layout.scan_qr_popup, null);
//                                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
//
//                                    // create the popup window
//                                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                                    boolean focusable = true; // lets taps outside the popup also dismiss it
//                                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                    popupWindow.setElevation(20);
//
//                                    // show the popup window
//                                    // which view you pass in doesn't matter, it is only used for the window token
//                                    popupWindow.showAtLocation(textView, Gravity.CENTER, 0, 0);
//
//                                    // dismiss the popup window when touched
//                                    closeButton.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            popupWindow.dismiss();
//                                        }
//                                    });
//                                    Toast.makeText(ScanActivity.this, "Check out of current building before trying to check in somewhere else!",
//                                            Toast.LENGTH_SHORT).show();
            }
        }
        else { // user is trying to check in
            // check if there is capacity in the building
            if (match.getCurrentCount() + 1 > match.getCapacity()) {
                System.out.println("Scan Receiver: full building case");

//                // return error to the user saying they cannot check into this building because it is full
//                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.scan_qr_popup2, null);
//                Button closeButton = (Button) popupView.findViewById(R.id.button12);
//
//                // create the popup window
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                popupWindow.setElevation(20);
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window token
//                popupWindow.showAtLocation(textView, Gravity.CENTER, 0, 0);
//
//                // dismiss the popup window when touched
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });

                // add popup
//                                    String error ="Error: this building is full!";
//                                    Intent intent = new Intent(ScanActivity.this, QRCodeScanner.class);
//
//                                    intent.putExtra("error", error);
//                                    startActivity(intent);

//                                    Toast.makeText(ScanActivity.this, "Building is Full!",
//                                            Toast.LENGTH_SHORT).show();
            }
            else { // check in the user
                System.out.println("Scan Receiver: check in case");

                // REDIRECT TO POPUP TO ASK USER IF THEY WANT TO CHECK IN TO THIS BUILDING
                Intent intent1 = new Intent(ScanReceiver.this, CheckInPopup.class);
                intent1.putExtra("building", match);
                intent1.putExtra("PrevPageData", user);
                startActivity(intent1);

            }
        }
//        Intent intent = new Intent(ScanReceiver.this, QRCodeScanner.class);
          System.out.println("Error: we should never get here lol");
//        intent.putExtra("PrevPageData", user);
//        startActivity(intent);
    }
}