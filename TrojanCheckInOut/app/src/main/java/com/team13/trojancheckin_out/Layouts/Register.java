package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;

public class Register extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private EditText email, password, completePassword;
    private AlertDialog.Builder builder;
    private AccountManipulator accountManipulator = new AccountManipulator();
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
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        System.out.println("CHECKING MAP CONTENTS USING GETSTUDENTACCOUNTS");
                        for (Map.Entry<String, User> u : map.entrySet()) {
                            System.out.println("SHIT: " + u.getValue().getName());
                        }
                    }
                });

              //ALERT 1: check if email is usc email and is valid
                if (!email.getText().toString().contains("@usc.edu")) {
                    System.out.println("EMAIL ERROR!");

                    // TODO: ADD A CHECK TO SEE IF THE EMAIL GIVEN ALREADY BELONGS TO A USER/CHECK AND IF THE ACCOUNT EXISTS TRANSFER THEM TO THE LOGIN PAGE

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                // Check if fields are filled
                else if (password.getText().toString().matches("") || completePassword.getText().toString().matches("")) {
                    System.out.println("EMAIL ERROR!");

                    // TODO: ADD A CHECK TO SEE IF THE EMAIL GIVEN ALREADY BELONGS TO A USER/CHECK AND IF THE ACCOUNT EXISTS TRANSFER THEM TO THE LOGIN PAGE

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    //Toast.makeText(Register.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }

                // Password matching
                else if (!password.getText().toString().equals(completePassword.getText().toString())) {
                    System.out.println("EMAIL ERROR!");

                    // TODO: ADD A CHECK TO SEE IF THE EMAIL GIVEN ALREADY BELONGS TO A USER/CHECK AND IF THE ACCOUNT EXISTS TRANSFER THEM TO THE LOGIN PAGE

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    //Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }

                // Check password size >= 4
                else if (password.getText().toString().length() < 4) {
                    System.out.println("EMAIL ERROR!");

                    // TODO: ADD A CHECK TO SEE IF THE EMAIL GIVEN ALREADY BELONGS TO A USER/CHECK AND IF THE ACCOUNT EXISTS TRANSFER THEM TO THE LOGIN PAGE

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    //System.out.println("I FUCKED UP SIZE");
                    //Toast.makeText(Register.this, "Password must be greater than 4 characters!", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().equals("") && email.getText().toString().equals("") && completePassword.getText().toString().equals("")) {
                    System.out.println("EMAIL ERROR!");

                    // TODO: ADD A CHECK TO SEE IF THE EMAIL GIVEN ALREADY BELONGS TO A USER/CHECK AND IF THE ACCOUNT EXISTS TRANSFER THEM TO THE LOGIN PAGE

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                else{
                    accountManipulator.getAllAccounts(new MyUserCallback() {
                        @Override
                        public void onCallback(Map<String, User> map) {
                            System.out.println("CHECKING MAP CONTENTS USING GETSTUDENTACCOUNTS");
                            boolean doesExist = false;
                            for (Map.Entry<String, User> u : map.entrySet()) {
                                System.out.println("SHIT: " + u.getValue().getName());
                                if(u.getValue().getEmail().equals(email.getText().toString())){
                                    doesExist = true;
                                    break;
                                }
                            }
                            if(doesExist){
                                System.out.println("LOGIN ERROR!");
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.account_exists_popup, null);
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
                                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                                // dismiss the popup window when touched
                                closeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow.dismiss();
                                    }
                                });

                                //reroute to register
                                loginButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                    }
                                });
                                //Toast.makeText(Register.this, "Account Already Exists!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                System.out.println("point 3");
                                System.out.println("BEFORE THE ACCOUNT SEARCH");
                                System.out.println("CREATION OF ACCOUNT");
                                Map<String, String> buildingList = new HashMap<>();
                                User user = new User("Adam Levine", email.getText().toString(), password.getText().toString(),
                                        "Photo", "123", false, null, buildingList,
                                        "Business", "true");
                                Intent intent = new Intent(Register.this, CompleteProfile.class);
                                intent.putExtra("PrevPageData", user);
                                startActivity(intent);
                            }
                        }
                    });
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