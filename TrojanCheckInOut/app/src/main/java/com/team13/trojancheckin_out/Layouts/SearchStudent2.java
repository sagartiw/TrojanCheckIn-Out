package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.ArrayList;
import java.util.List;

public class SearchStudent2 extends AppCompatActivity {
    private Button Search;
    private Button Back;

    //a list to store all the products
    List<User> studentList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student2);
        Search = (Button)findViewById(R.id.button7);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Back = (Button)findViewById(R.id.backer);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchStudent2.this, ManagerLanding.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(SearchStudent2.this, StudentLanding.class);
                    startActivity(intent);
                }
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
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

        //creating recyclerview adapter
        StudentAdapter adapter = new StudentAdapter(this, studentList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{
                "Accounting",
                "Acting for the Stage, Screen and New Media",
                "Aerospace Engineering",
                "American Popular Culture",
                "American Studies and Ethnicity (African American Studies)",
                "American Studies and Ethnicity (Asian American Studies)",
                "American Studies and Ethnicity",
                "American Studies and Ethnicity (Chicano/Latino Studies)",
                "Animation and Digital Arts",
                "Anthropology",
                "Applied and Computational Mathematics",
                "Applied Mechanics",
                "Archaeology",
                "Architectural Studies",
                "Architecture",
                "Art",
                "Art History",
                "Arts, Technology and the Business of Innovation",
                "Astronautical Engineering",
                "Astronomy",
                "Biochemistry",
                "Biological Sciences",
                "Biomedical Engineering",
                "Biophysics",
                "Business Administration",
                "Business Administration (Cinematic Arts)",
                "Business Administration (International Relations)",
                "Business Administration (Real Estate Finance)",
                "Business Administration (World Program)",
                "Central European Studies",
                "Chemical Engineering",
                "Chemistry",
                "Choral Music",
                "Cinema and Media Studies",
                "Cinematic Arts, Film and Television Production",
                "Civil Engineering",
                "Classics",
                "Cognitive Science",
                "Communication",
                "Comparative Literature",
                "Composition",
                "Computational Linguistics",
                "Computational Neuroscience",
                "Computer Engineering and Computer Science",
                "Computer Science",
                "Computer Science (Games)",
                "Computer Science/Business Administration",
                "Contemporary Latino and Latin American Studies",
                "Dance",
                "Data Science",
                "Dental Hygiene",
                "Design",
                "Earth Sciences",
                "East Asian Area Studies",
                "East Asian Languages and Cultures",
                "Economics",
                "Economics/Mathematics",
                "Electrical and Computer Engineering",
                "English",
                "Environmental Engineering",
                "Environmental Science and Health",
                "Environmental Studies",
                "Fine Arts",
                "French",
                "Gender and Sexuality Studies",
                "Geodesign",
                "Geological Sciences",
                "Global Geodesign",
                "Global Health Studies",
                "Global Studies",
                "Health and Human Sciences",
                "Health and Humanity",
                "Health Promotion and Disease Prevention Studies",
                "History",
                "History and Social Science Education",
                "Human Biology",
                "Human Development and Aging",
                "Human Security and Geospatial Intelligence",
                "Industrial and Systems Engineering",
                "Intelligence and Cyber Operations",
                "Interactive Entertainment",
                "International Relations",
                "International Relations (Global Business)",
                "International Relations and the Global Economy",
                "Italian",
                "Jazz Studies",
                "Jewish Studies",
                "Journalism",
                "Latin American and Iberian Cultures, Media and Politics",
                "Law, History, and Culture",
                "Lifespan Health",
                "Linguistics",
                "Linguistics and Cognitive Science",
                "Linguistics and East Asian Languages and Cultures",
                "Linguistics and Philosophy",
                "Mathematics",
                "Mathematics/Economics",
                "Mechanical Engineering",
                "Media Arts and Practice",
                "Middle East Studies",
                "Music",
                "Music Industry",
                "Music Production",
                "Musical Theatre",
                "Narrative Studies",
                "Neuroscience",
                "Non-Governmental Organizations and Social Change",
                "Occupational Therapy",
                "Performance",
                "Pharmacology and Drug Development",
                "Philosophy",
                "Philosophy and Physics",
                "Philosophy, Politics and Economics",
                "Philosophy, Politics and Law",
                "Physical Sciences",
                "Physics",
                "Physics/Computer Science",
                "Political Economy",
                "Political Science",
                "Psychology",
                "Public Policy",
                "Public Relations",
                "Quantitative Biology",
                "Real Estate Development",
                "Religion",
                "Russian",
                "Social Sciences",
                "Sociology",
                "Spanish",
                "Theatre",
                "Urban Studies and Planning",
                "Visual and Performing Arts Studies",
                "Writing for Screen and Television",
        };
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter2);

        //get the spinner from the xml.
        Spinner dropdown2 = findViewById(R.id.spinner2);
        //create a list of items for the spinner.
        String[] items2 = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        //set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter3);
    }

}