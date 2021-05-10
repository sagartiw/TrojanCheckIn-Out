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
            if (match.getAbbreviation().equalsIgnoreCase(user.getCurrentBuilding().getAbbreviation().toString()) ) {

                // ADD POPUP TO ASK USER IF THEY WANT TO CHECK OUT
                System.out.println("Scan Receiver: check out case");

                // REDIRECT TO POPUP TO ASK USER IF THEY WANT TO CHECK OUT OF THIS BUILDING
                Intent intent1 = new Intent(ScanReceiver.this, CheckOutPopup.class);
                intent1.putExtra("building", match);
                intent1.putExtra("PrevPageData", user);
                startActivity(intent1);

            }
            else {
                System.out.println("Scan Receiver: trying to check in while already in a different building case");

                // send an error message that they need to check out of their current building before trying to check in somewhere else

                String error ="Check out of current building before trying to check in somewhere else!";
                Intent intent1 = new Intent(ScanReceiver.this, CheckOutErrorPopup.class);
                intent1.putExtra("PrevPageData", user);
                intent1.putExtra("building", match);
                intent1.putExtra("error", error);
                startActivity(intent1);

            }
        }
        else { // user is trying to check in
            // check if there is capacity in the building
            System.out.println("capacity of " + match.getAbbreviation() + " is " + match.getCapacity());
            System.out.println("current count in " + match.getAbbreviation() + " is " + match.getCurrentCount());


            if (match.getCurrentCount() + 1 > match.getCapacity()) {
                System.out.println("Scan Receiver: full building case");


                String error ="This building is currently full!";
                Intent intent1 = new Intent(ScanReceiver.this, CheckOutErrorPopup.class);
                intent1.putExtra("PrevPageData", user);
                intent1.putExtra("building", match);
                intent1.putExtra("error", error);
                startActivity(intent1);

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