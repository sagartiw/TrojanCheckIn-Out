package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team13.trojancheckin_out.R;

public class StudentLanding extends AppCompatActivity {
    private Button SignOut;

    private View Scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        SignOut = (Button)findViewById(R.id.signOut);
        Scan = findViewById(R.id.Scan);


        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, Startup.class);
                startActivity(intent);
            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

    }
}