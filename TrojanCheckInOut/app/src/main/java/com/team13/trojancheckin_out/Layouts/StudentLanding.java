package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.R;

public class StudentLanding extends AppCompatActivity {
    private Button SignOut;
    private Button CheckOut;
    private Button Scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        SignOut = (Button)findViewById(R.id.signOut);
        Scan = (Button)findViewById(R.id.Scan);
        CheckOut = (Button)findViewById(R.id.checkOut);


        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, Startup.class);
                startActivity(intent);
            }
        });

        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, Startup.class);
                startActivity(intent);
            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Edit Profile");
        menu.getMenu().add("Manager View");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here

                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(StudentLanding.this, EditProfile.class);
                    startActivity(intent);
                }

                if(item.getTitle().toString().equals("Manager View")){
                    Intent intent = new Intent(StudentLanding.this, ManagerLanding.class);
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
            }
        });

        Button signout = (Button) findViewById(R.id.signOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.sign_out_popup, null);
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
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

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