
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
import com.team13.trojancheckin_out.Database.MyUserCallback;

import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.AccountManipulator.resetFromStart;


public class Login extends AppCompatActivity {

    private AccountManipulator accountManipulator = new AccountManipulator();
    private EditText email, password;
    private Button Login;
    private Button Back;
    private Button forgotPassword;
    private User user;
    private Intent intent;
    private boolean found = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button)findViewById(R.id.login);

/*
        //requests email through google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient loginClient = GoogleSignIn.getClient(this.gso);
        GoogleSignInAccount loginAccount = GoogleSignIn.getLastSignedInAccount(this);
        //User already signed in
        if (loginAccount != null){
            Toast.makeText(this, "User is signed in already", Toast.LENGTH_SHORT).show();
        }

 */
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                //Intent sign = loginClient.getSignInIntent();
                //startActivityForResult(sign, GOOGLE_REQUEST_CODE);
                Intent intent = new Intent(Login.this, ManagerLanding.class);
                startActivity(intent);
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

        Login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        password = (EditText) findViewById(R.id.editTextTextPassword3);
        //forgotPassword = (Button) findViewById(R.id.button);

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
                password = (EditText) findViewById(R.id.editTextTextPassword3);

                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        System.out.println("LOGIN PAGE ACCOUNT MANIP CALLBACK");


                        // check if resetFromStart is true --> then we should immediately go to startup
                        if (resetFromStart) {
                            // this should never happen lol but redirect to startup anyways
                            System.out.println("RESET FROM START IS TRUE SO REDIRECTING TO STARTUP PAGE");
                            Intent intent = new Intent(v.getContext(), Startup.class);

                            // do i even need this ?
                            //intent.putExtra("PrevPageData", user);

                            v.getContext().startActivity(intent);

                        }


                        for (Map.Entry<String, User> checkUser : map.entrySet()) {
                            System.out.println("BEFORE EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
                            System.out.println("ACTUAL: " + email.getText().toString() + " " + password.getText().toString());
                            if (checkUser.getValue().getEmail().equals(email.getText().toString()) &&
                                    checkUser.getValue().getPassword().equals(password.getText().toString())) {
                                System.out.println("AFTER EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
                                System.out.println("ACTUAL: " + email.getText().toString() + " " + password.getText().toString());
                                user = checkUser.getValue();
                                System.out.println(user.isManager());
                                found = true;
                                currentUser = user;
                                // check if account is deleted
                                if (user.isDeleted()) {
                                    // create specific popup??
                                    System.out.println("Deleted Account ERROR!");
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View popupView = inflater.inflate(R.layout.reactivate_popup , null);
                                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
                                    Button reactivateButton = (Button) popupView.findViewById(R.id.button10);

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
                                    reactivateButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            referenceUsers.child(user.getId()).child("deleted").setValue(false);

                                            if (user.isManager().equalsIgnoreCase("false")) {
                                                intent = new Intent(Login.this, StudentLanding.class);
                                                intent.putExtra("PrevPageData", user);
                                                startActivity(intent);
                                            }
                                            //Manager Case
                                            else if (user.isManager().equalsIgnoreCase("true")) {
                                                intent = new Intent(Login.this, ManagerLanding.class);
                                                intent.putExtra("PrevPageData", user);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                    continue;
                                }
                                //Student Case
                                System.out.println("LOGIN ID: " + currentUser.getId());

                                if (user.isManager().equalsIgnoreCase("false")) {
                                    intent = new Intent(Login.this, StudentLanding.class);
                                    intent.putExtra("PrevPageData", user);
                                    startActivity(intent);
                                }
                                //Manager Case
                                else if (user.isManager().equalsIgnoreCase("true")) {
                                    intent = new Intent(Login.this, ManagerLanding.class);
                                    intent.putExtra("PrevPageData", user);
                                    startActivity(intent);
                                }

                            }
                            System.out.println("WE HAVE GOTTEN HERE");
                        }
                        if (!found) {
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
                            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

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
                    }
                });
            }
/*

//                accountManipulator.getStudentAccounts(new MyUserCallback() {
//                    @Override
//                    public void onCallback(Map<String, User> map) {
//                        for (Map.Entry<String, User> checkUser : map.entrySet()) {
//                            System.out.println("BEFORE EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
//                            System.out.println("ACTUAL: " + email.getText().toString()+ " " + password.getText().toString());
//                            if (checkUser.getValue().getEmail().equals(email.getText().toString()) &&
//                                    checkUser.getValue().getPassword().equals(password.getText().toString())) {
//                                System.out.println("AFTER EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
//                                System.out.println("ACTUAL: " + email.getText().toString()+ " " + password.getText().toString());
//                                user = checkUser.getValue();
//                                System.out.println(user.isManager());
//                                found = true;
//                                intent = new Intent(Login.this, StudentLanding.class);
//                                intent.putExtra("PrevPageData", user);
//                                startActivity(intent);
//                            }
//                            System.out.println("WE HAVE GOTTEN HERE");
//                        }
//                    }
//                });
//
//                accountManipulator.getManagerAccounts(new MyUserCallback() {
//                    @Override
//                    public void onCallback(Map<String, User> map) {
//                        for (Map.Entry<String, User> checkUser : map.entrySet()) {
//                            System.out.println("2 BEFORE EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
//                            System.out.println("2 ACTUAL: " + email.getText().toString()+ " " + password.getText().toString());
//                            if (checkUser.getValue().getEmail().equals(email.getText().toString()) &&
//                                    checkUser.getValue().getPassword().equals(password.getText().toString())) {
//                                System.out.println("2 AFTER EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
//                                System.out.println("2 ACTUAL: " + email.getText().toString()+ " " + password.getText().toString());
//                                user = checkUser.getValue();
//                                System.out.println(user.isManager());
//                                found = true;
//                                intent = new Intent(Login.this, ManagerLanding.class);
//                                intent.putExtra("PrevPageData", user);
//                                startActivity(intent);
//                            }
//                            System.out.println("2 WE HAVE GOTTEN HERE");
//                        }
//                    }
//                });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_REQUEST_CODE){
            Task<GoogleSignInAccount> loginTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount loginAcct = loginTask.getResult(ApiException.class);
                Toast.makeText(this, "You are now logged in", Toast.LENGTH_SHORT).show();
>>>>>>> cd8e4bf9d9f228bc78d74440b187ec3a159d7c9b
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
<<<<<<< HEAD
}

//package com.team13.trojancheckin_out.Layouts;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.team13.trojancheckin_out.Accounts.R;
//import com.team13.trojancheckin_out.Accounts.User;
//import com.team13.trojancheckin_out.Database.AccountManipulator;
//import com.team13.trojancheckin_out.Database.MyCallback;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Login extends AppCompatActivity {
//
//    private AccountManipulator accountManipulator = new AccountManipulator();
//    private EditText email, password;
//    private Button Login;
//    private Button Back;
//    private User user;
//    private Intent intent;
//    private boolean found = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        Login = (Button) findViewById(R.id.login);
//        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
//        password = (EditText) findViewById(R.id.editTextTextPassword3);
//
//        Login.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
////                for (User checkUser : accountManipulator.getStudentAccounts().values()) {
////                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)){
////                        user = checkUser;
////                        System.out.println(user.isManager());
////                        intent = new Intent(Login.this, StudentLanding.class);
////                        intent.putExtra("PrevPageData", user);
////                        startActivity(intent);
////                    }
////
////                }
////
////                for (User checkUser : accountManipulator.getManagerAccounts().values()) {
////                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)) {
////                        user = checkUser;
////                        System.out.println(user.isManager());
////                        intent = new Intent(Login.this, ManagerLanding.class);
////                        intent.putExtra("PrevPageData", user);
////                        startActivity(intent);
////                    }
////                }
//
//                accountManipulator.getStudentAccounts(new MyCallback() {
//                    @Override
//                    public void onCallback(Map<String, User> map) {
//                        for (Map.Entry<String, User> checkUser : map.entrySet()) {
//                            System.out.println("EXPECTED: " + checkUser.getValue().getEmail() + " " + checkUser.getValue().getPassword());
//                            System.out.println("ACTUAL: " + email.getText().toString()+ " " + password.getText().toString());
//
//                            if (checkUser.getValue().getEmail().equals(email.getText().toString()) &&
//                                    checkUser.getValue().getPassword().equals(password.getText().toString())) {
//                                user = checkUser.getValue();
//                                System.out.println(user.isManager());
//                                found = true;
//                                intent = new Intent(Login.this, StudentLanding.class);
//                                intent.putExtra("PrevPageData", user);
//                                startActivity(intent);
//                            }
//                            System.out.println("WE HAVE GOTTEN HERE");
//                        }
//                    }
//                });
//
//                if(!found){
//                    //reset the page here. user not found!
//                    System.out.println("LOGIN ERROR!");
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View popupView = inflater.inflate(R.layout.login_popup, null);
//                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
//                    Button registerButton = (Button) popupView.findViewById(R.id.button10);
//
//                    // create the popup window
//                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    boolean focusable = true; // lets taps outside the popup also dismiss it
//                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    popupWindow.setElevation(20);
//
//                    // show the popup window
//                    // which view you pass in doesn't matter, it is only used for the window token
//                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
//
//                    // dismiss the popup window when touched
//                    closeButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//
//                    //reroute to register
//                    registerButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            intent = new Intent(Login.this, Register.class);
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        });
//    }
//}
=======

 */
        });
    }
}