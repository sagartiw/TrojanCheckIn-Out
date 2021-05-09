package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.UPC.Building;

public class CapacityErrorPopup extends AppCompatActivity {
    private String error;

    private Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_error_popup);

       // building = ;

        Button submit = (Button) findViewById(R.id.buttonSubmit);

        //get building name for layout
        TextView building = (TextView)findViewById(R.id.textView26);
        building.setText("");

        error = (String) getIntent().getSerializableExtra("error");
        TextView errorMsg = (TextView)findViewById(R.id.textView20);
        errorMsg.setText(error);


    }
}