package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;

public class Login extends AppCompatActivity {

    private Button Login;
    private Button Back;

    private AccountManipulator accountManipulator = new AccountManipulator();
    private User user;
    private EditText email, password;
    private FirebaseAuth fauth;

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

    }
}