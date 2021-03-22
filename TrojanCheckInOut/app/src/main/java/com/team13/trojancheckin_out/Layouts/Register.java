package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private EditText email, password, completePassword;
    private AlertDialog.Builder builder;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private User user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.signOut);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        completePassword = (EditText)  findViewById(R.id.editTextTextPassword2);

        //Alert Dialog Declaration
        builder = new AlertDialog.Builder(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ALERT 1: check if email is usc email and is valid

                accountManipulator.getStudentAccounts(new MyCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        System.out.println("CHECKING MAP CONTENTS USING GETSTUDENTACCOUNTS");
                        for (Map.Entry<String, User> u : map.entrySet()) {
                            System.out.println("SHIT: " + u.getValue().getName());
                        }
                    }
                });



                if ((!email.getText().toString().contains("@usc.edu") &&
                        !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())) {
                    System.out.println("EMAIL ERROR!");

                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.registration_popup, null);
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
                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                //ALERT 2: check if passwords match
                else if (!password.getText().toString().equals(completePassword.getText().toString())) {
                    System.out.println("PASSWORD ERROR!");

                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.registration_popup2, null);
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
                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                else{
                    System.out.println("BEFORE THE ACCOUNT SEARCH");
                    accountManipulator.getStudentAccounts(new MyCallback() {
                        @Override
                        public void onCallback(Map<String, User> map) {
                            System.out.println("CHECKING MAP CONTENTS USING GETSTUDENTACCOUNTS");
                            for (Map.Entry<String, User> u : map.entrySet()) {
                                System.out.println("SHIT: " + u.getValue().getName());
                            }
                        }
                    });
//                    for (User checkUser : accountManipulator.getStudentAccounts().values()) {
//                        if (checkUser.getEmail().equals(email)){
//                            user = checkUser;
//                            System.out.println(user.isManager());
//                        }
//                        System.out.println("STUDENT ACCOUNT SEARCH INDICATOR");
//                    }
//
//                    for (User checkUser : accountManipulator.getManagerAccounts().values()) {
//                        if (checkUser.getEmail().equals(email)) {
//                            user = checkUser;
//                            System.out.println(user.isManager());
//                        }
//                        System.out.println("MANAGER ACCOUNT SEARCH INDICATOR");
//                    }
//                    System.out.println("AFTER THE ACCOUNT SEARCH");
                    //ALERT 3: account already exists
                    if(user != null){
                        System.out.println("DUPLICATE ACCOUNT ERROR!");
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.registration_popup3, null);
                        Button closeButton = (Button) popupView.findViewById(R.id.button12);
                        Button loginButton = (Button) popupView.findViewById(R.id.button10);

                        // create the popup window
                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.setElevation(20);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window token
                        popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                        // dismiss the popup window when touched
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        //reroute to login
                        loginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }
                        });
                    }
                    //REGISTER. We can create a user object and move on to Complete Profile
                    else{
                        System.out.println("CREATION OF ACCOUNT");
                        List<Building> buildingList = new ArrayList<>();
                        User user = new User("Adam Levine", email.getText().toString(), password.getText().toString(),
                                "Photo", "123", false, null, buildingList,
                                "Business", "true");
                        Intent intent = new Intent(Register.this, CompleteProfile.class);
                        intent.putExtra("PrevPageData", user);
                        startActivity(intent);
                    }
                }
            }
        });

        Back = (Button)findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Startup.class);
                startActivity(intent);
            }
        });
    }
}