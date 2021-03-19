package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.UPC.Building;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Database.AccountManipulator;

public class Register extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private EditText Email;
    private EditText Password;
    private EditText Confirm;
    private AccountManipulator accountManipulator;

    public Register(AccountManipulator accountManipulator) {
        this.accountManipulator = accountManipulator;
    }

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Register = (Button)findViewById(R.id.signOut);


        Email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Password = (EditText) findViewById(R.id.editTextTextPassword);
        Confirm = (EditText) findViewById(R.id.editTextTextPassword2);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Password.getText().equals(Confirm.getText())){

                }


                User user = new User(null, Email.getText().toString(), Password.getText().toString(), null, 0,
                false, null, null, null, true);

                accountManipulator.createAccount(user);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Category");
                reference.setValue("This is my set value");

                Intent intent = new Intent(Register.this, CompleteProfile.class);
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