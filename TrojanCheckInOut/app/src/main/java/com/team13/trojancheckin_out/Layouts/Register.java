package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.signOut);

        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Building> buildingList = new ArrayList<>();
                String isManager = "true";
                User user = new User("Adam Levine", email.getText().toString(), password.getText().toString(),
                        "Photo", "123", false, null, buildingList,
                        "Business", isManager);

                Intent intent = new Intent(Register.this, CompleteProfile.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
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