package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team13.trojancheckin_out.Accounts.R;

public class EditProfile extends AppCompatActivity {

    private Button Back3; //id back3
    private Button editPFP; //id editPFP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Back3 = (Button)findViewById(R.id.back3);

        Back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, StudentLanding.class);
                startActivity(intent);
            }
        });
    }
}