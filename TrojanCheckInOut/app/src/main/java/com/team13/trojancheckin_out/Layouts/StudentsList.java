package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.ArrayList;
import java.util.List;

public class StudentsList extends AppCompatActivity {

    private Button Back;

    //a list to store all the products
    List<User> studentList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Back = (Button)findViewById(R.id.backer2);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsList.this, ManagerLanding.class);
                startActivity(intent);
            }
        });

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        studentList = new ArrayList<>();


        //adding some items to our list
        studentList.add(
                new User(
                        "Mindy Diep",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Arian Memari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Sagar Tiwari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Kabir Samra",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Annika Oeth",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Elizabeth Moody",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Mindy Diep",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Arian Memari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Sagar Tiwari",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Kabir Samra",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Annika Oeth",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        studentList.add(
                new User(
                        "Elizabeth Moody",
                        "mindydie@usc.edu",
                        "hello",
                        "photo",
                        "123456789",
                        true,
                        null,
                        null,
                        "CSBA",
                        "false"
                ));

        //creating recyclerview adapter
        StudentAdapter adapter = new StudentAdapter(this, studentList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}