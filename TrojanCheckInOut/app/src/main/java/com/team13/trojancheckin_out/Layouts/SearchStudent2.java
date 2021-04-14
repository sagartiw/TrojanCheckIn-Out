package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.Accounts.Manager;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchStudent2 extends AppCompatActivity {
    private Button Search;
    private Button Back;
    private User user;

    private EditText stuID;
    private Spinner majInput;
    private Spinner buildInput;
    private EditText time1;
    private EditText time2;
    private EditText date1;
    private EditText date2;
    private EditText fName1;
    private EditText lName1;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private BuildingManipulator buildingManipulator = new BuildingManipulator();
    private Manager manager = new Manager(buildingManipulator, accountManipulator);
    private List<User> foundStudents = new ArrayList<>();
    
    //a list to store all the products
    List<User> studentList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student2);
        user = (User) getIntent().getSerializableExtra("PrevPageData");
        Search = (Button)findViewById(R.id.button7);
        stuID = (EditText)findViewById(R.id.editTextTextPersonName);
        majInput = (Spinner)findViewById(R.id.spinner);
        buildInput = (Spinner)findViewById(R.id.spinner2);
        time1 = (EditText)findViewById(R.id.editTextTime3);
        time2 = (EditText)findViewById(R.id.editTextTime4);
        date1 = (EditText)findViewById(R.id.editTextDate);
        date2 = (EditText)findViewById(R.id.editTextDate2);
        fName1 = (EditText)findViewById(R.id.firstName);
        lName1 = (EditText)findViewById(R.id.lName2);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manager.searchStudents(
                //getting the recyclerview from xml
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchStudent2.this));
                StudentAdapter adapter = new StudentAdapter(SearchStudent2.this, studentList);
                recyclerView.setAdapter(adapter);

                studentList = new ArrayList<>();

                buildingManipulator.getCurrentBuildings(new MyBuildingCallback() {
                    @Override
                    public void onCallback(Map<String, Building> map) {
                        int t1 = -1;
                        int t2 = -1;
                        int d1 = -1;
                        int d2 = -1;
                        String id = null;
                        String major = null;
                        Building building = null;
                        String fName = null;
                        String lName = null;


                        boolean showAll = false;
                        boolean cond1 = true, cond2 = true, cond3 = true, cond4 = true, cond5 = true, cond6 = true, cond7 = true, cond8 = true;

                        // If student ID is not empty, cond1 is false
                        // If student ID is empty, cond1 is true
                        if(!stuID.getText().toString().equals("")) {
                            id = stuID.getText().toString();
                            cond1 = false;
                        }
                        if(!majInput.getSelectedItem().toString().equals("None")) {
                            major = majInput.getSelectedItem().toString();
                            cond2 = false;
                        }
                        if(!buildInput.getSelectedItem().toString().equals("None")) {
                            building = map.get(buildInput.getSelectedItem().toString());
                            cond2 = false;
                        }
                        if(!time1.getText().toString().equals("")) {
                            t1 = Integer.parseInt(time1.getText().toString());
                            cond3 = false;
                        }
                        if(!time2.getText().toString().equals("")) {
                            t2 = Integer.parseInt(time2.getText().toString());
                            cond4 = false;
                        }
                        if(!date1.getText().toString().equals("")) {
                            d1 = Integer.parseInt(date1.getText().toString());
                            cond5 = false;
                        }
                        if(!date2.getText().toString().equals("")) {
                            d2 = Integer.parseInt(date2.getText().toString());
                            cond6 = false;
                        }
                        if(!fName1.getText().toString().equals("")) {
                            fName = fName1.getText().toString();
                            cond6 = false;
                        }
                        if(!lName1.getText().toString().equals("")) {
                            lName = lName1.getText().toString();
                            cond6 = false;
                        }
                        // IF ALL FIELDS ARE EMPTY (Cond = true), SHOW ALL WOULD BE SET TRUE
                        if (cond1 && cond2 && cond3 && cond4 && cond5 && cond6) {
                           showAll = true;
                        }

                        System.out.println("CONDITIONS: " + cond1 + cond2 + cond3 + cond4 + cond5 + cond6);

                        if (showAll) {
                            accountManipulator.getAllAccounts(new MyUserCallback() {
                                @Override
                                public void onCallback(Map<String, User> map) {
                                    System.out.println("HELLO SHOW ALL!");
                                    for (Map.Entry<String, User> e : map.entrySet()) {
                                        User u = e.getValue();
                                        String name = u.getName();
                                        String email = u.getEmail();
                                        String pass = u.getPassword();
                                        String photo = u.getPhoto();
                                        String id = u.getId();
                                        boolean inBuilding = u.isInBuilding();
                                        Building currentBuilding = u.getCurrentBuilding();
                                        Map<String, String> history = u.getHistory();
                                        String major = u.getMajor();
                                        String isManager = u.isManager();
                                        boolean deleted = u.isDeleted();

                                        User user = new User(name, email, pass, photo, id, inBuilding,
                                                currentBuilding, history, major, isManager, deleted);
                                        System.out.println("HERE USER: " + name + " " + currentBuilding);
                                        studentList.add(user);
                                    }
                                }
                            });
                        }
                        else
                        {
                            studentList = manager.searchStudents(fName, lName, t1, t2, building, id, major, d1, d2); //date1, date2
                        }

                        //setting adapter to recyclerview
                        adapter.notifyDataSetChanged();
                    }

                });
            }
        });

        Back = (Button)findViewById(R.id.backer);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchStudent2.this, ManagerLanding.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Edit Profile");

        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        //menu.getMenu().add("Delete Account");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(SearchStudent2.this, StudentLanding.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(SearchStudent2.this, EditProfile.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(SearchStudent2.this, Startup.class);
                    startActivity(intent);
                }
//                if(item.getTitle().toString().equals("Delete Account")){
//                    // inflate the layout of the popup window
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View popupView = inflater.inflate(R.layout.delete_account_popup, null);
//                    Button closeButton = (Button) popupView.findViewById(R.id.button12);
//
//                    // create the popup window
//                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    boolean focusable = true; // lets taps outside the popup also dismiss it
//                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    popupWindow.setElevation(20);
//
//                    // show the popup window
//                    // which view you pass in doesn't matter, it is only used for the window token
//                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
//
//                    // dismiss the popup window when touched
//                    closeButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//                }
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
        //studentList = new ArrayList<>();

//        accountManipulator.getAllAccounts(new MyUserCallback() {
//            @Override
//            public void onCallback(Map<String, User> map) {
//                for (Map.Entry<String, User> e : map.entrySet()) {
//                    User u = e.getValue();
//                    String name = u.getName();
//                    String email = u.getEmail();
//                    String pass = u.getPassword();
//                    String photo = u.getPhoto();
//                    String id = u.getId();
//                    boolean inBuilding = u.isInBuilding();
//                    Building currentBuilding = u.getCurrentBuilding();
//                    Map<String, String> history = u.getHistory();
//                    String major = u.getMajor();
//                    String isManager = u.isManager();
//
//                    User user = new User(name, email, pass, photo, id, inBuilding,
//                            currentBuilding, history, major, isManager);
//
//                    studentList.add(user);
//                }
//            }
//        });

        //creating recyclerview adapter
        StudentAdapter adapter = new StudentAdapter(this, studentList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{
                "None", "Accounting",
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
        String[] items2 = new String[]{"None", "ANN", "BSR", "DML", "DMT", "ESH", "EVK", "FLT", "GFS", "JFF", "KAP" , "KDC", "LVL", "RTH", "SCA", "SGM", "TCC", "THH", "WPH", "ZHS"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        //set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter3);
    }

}