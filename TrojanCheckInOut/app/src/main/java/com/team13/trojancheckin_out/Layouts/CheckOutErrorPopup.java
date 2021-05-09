package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.QRCodeScanner;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.UPC.Building;

public class CheckOutErrorPopup extends AppCompatActivity {
    private User user;
    private Building match;

    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_error_popup);

        user = (User) getIntent().getSerializableExtra("PrevPageData");
        match = (Building) getIntent().getSerializableExtra("building");

        Button submit = (Button) findViewById(R.id.buttonSubmit);

        //get building name for layout
        TextView building = (TextView)findViewById(R.id.textView26);
        building.setText(match.getName());

        error = (String) getIntent().getSerializableExtra("error");
        TextView errorMsg = (TextView)findViewById(R.id.textView20);
        errorMsg.setText(error);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to qr code scanner
                Intent intent = new Intent(CheckOutErrorPopup.this, QRCodeScanner.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });
    }
}