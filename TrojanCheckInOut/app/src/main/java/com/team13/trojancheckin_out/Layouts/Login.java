package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyCallback;

import java.util.Map;

public class Login extends AppCompatActivity {

    private Button Login;
    private Button Back;

    private AccountManipulator accountManipulator = new AccountManipulator();
    private EditText email, password;
    private User user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button)findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
                password = (EditText) findViewById(R.id.editTextTextPassword3);

//                for (User checkUser : accountManipulator.getStudentAccounts().values()) {
//                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)){
//                        user = checkUser;
//                        System.out.println(user.isManager());
//                        intent = new Intent(Login.this, StudentLanding.class);
//                        intent.putExtra("PrevPageData", user);
//                        startActivity(intent);
//                    }
//
//                }
//
//                for (User checkUser : accountManipulator.getManagerAccounts().values()) {
//                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)) {
//                        user = checkUser;
//                        System.out.println(user.isManager());
//                        intent = new Intent(Login.this, ManagerLanding.class);
//                        intent.putExtra("PrevPageData", user);
//                        startActivity(intent);
//                    }
//                }

                accountManipulator.getStudentAccounts(new MyCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> checkUser : map.entrySet()) {
                            System.out.println("EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
                            System.out.println("ACTUAL: " + email + " " + password);

                            if (checkUser.getValue().getEmail().equals(email) && checkUser.getValue().getPassword().equals(password)) {
                                user = checkUser.getValue();
                                System.out.println(user.isManager());
                                intent = new Intent(Login.this, StudentLanding.class);
                                intent.putExtra("PrevPageData", user);
                                startActivity(intent);
                            }
                            System.out.println("WE HAVE GOTTEN HERE");
                        }
                    }
                });

                //reset the page here. user not found!
                System.out.println("LOGIN ERROR!");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.login_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);
                Button registerButton = (Button) popupView.findViewById(R.id.button10);

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

                //reroute to register
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                    }
                });
            }
        });

        Back = (Button)findViewById(R.id.back2);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Startup.class);
                startActivity(intent);
            }
        });

    }
}