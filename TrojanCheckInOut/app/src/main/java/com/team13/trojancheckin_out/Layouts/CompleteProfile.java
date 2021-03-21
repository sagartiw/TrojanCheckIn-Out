package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;

public class CompleteProfile extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private User user;
    private EditText fName, lName, studentID;
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);

        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Register = (Button)findViewById(R.id.register3);

        // Grab currrent data for the user
        user = (User) getIntent().getSerializableExtra("PrevPageData");

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Grab data from this current page
                fName = (EditText) findViewById(R.id.editTextTextPersonName);
                lName = (EditText) findViewById(R.id.editTextTextPersonName2);
                major = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                studentID = (EditText) findViewById(R.id.editTextTextPersonName4);

                // Add data from this current page to complete the user object
                user.setName(fName.getText().toString() + " " + lName.getText().toString());
                user.setMajor(major);
                user.setManager("true");
                user.setId(studentID.getText().toString());

                Building building = new Building();
                building.setName("SAL");
                user.setCurrentBuilding(building);
                user.getHistory().add(building);

                // Push user to DB
                accountManipulator.createAccount(user);
                System.out.println("BEFORE STUDENT ACCOUNTS IS ACCESSED");
                for (User user : accountManipulator.getStudentAccounts().values()) {
                    System.out.println(user.getName());
                }
                Intent intent = new Intent(CompleteProfile.this, ManagerLanding.class);
                startActivity(intent);
            }
        });

        Back = (Button)findViewById(R.id.back3);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteProfile.this, Register.class);
                startActivity(intent);
            }
        });
    }
}