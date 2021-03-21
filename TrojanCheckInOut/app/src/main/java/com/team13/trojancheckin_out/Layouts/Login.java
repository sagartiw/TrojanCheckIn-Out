package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;

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

                for (User checkUser : accountManipulator.getStudentAccounts().values()) {
                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)){
                        user = checkUser;
                        System.out.println(user.isManager());
                        intent = new Intent(Login.this, StudentLanding.class);
                        intent.putExtra("PrevPageData", user);
                        startActivity(intent);
                    }

                }

                for (User checkUser : accountManipulator.getManagerAccounts().values()) {
                    if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)) {
                        user = checkUser;
                        System.out.println(user.isManager());
                        intent = new Intent(Login.this, ManagerLanding.class);
                        intent.putExtra("PrevPageData", user);
                        startActivity(intent);
                    }
                }

                //reset the page here. user not found!

                /*
                referenceUsers.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);
                            if (user.isManager().equals("True")) {
                                System.out.println("Name: " + user.getName() + " Email: " + user.isManager());
                                managerAccounts.put(user.getId(), user);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
                * */
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