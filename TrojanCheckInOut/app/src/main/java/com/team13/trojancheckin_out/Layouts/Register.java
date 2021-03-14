package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team13.trojancheckin_out.Accounts.R;

public class Register extends AppCompatActivity {

    private Button Register;
    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.signOut);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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