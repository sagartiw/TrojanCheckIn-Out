package com.team13.trojancheckin_out.Layouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;

import java.io.IOException;

public class CompleteProfile extends AppCompatActivity {

    private Button Register;
    private Button Back;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private User user;
    private EditText fName, lName, studentID;
    private String major;
    private RadioGroup radioGroup;
    private RadioButton studentButton;
    private RadioButton managerButton;
    private ImageButton profileImage;
    private ImageButton uploadProfImage;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth mAuth;
    private FirebaseUser curr;
    //https://firebase.google.com/docs/storage/android/upload-files
    public final static int PICK_PHOTO_CODE = 1046;
    //https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_complete_profile);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Register = (Button) findViewById(R.id.register3);

        // Grab currrent data for the user
        user = (User) getIntent().getSerializableExtra("PrevPageData");

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
            }
        });

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

                int radioChosen = radioGroup.getCheckedRadioButtonId();
                boolean checkConditions = true;
                if (radioChosen == -1) {
                    Toast.makeText(CompleteProfile.this, "Please select account type!", Toast.LENGTH_SHORT).show();
                    checkConditions = false;
                } else {

                    RadioButton chosen = (RadioButton) radioGroup.findViewById(radioChosen);
                    studentButton = radioGroup.findViewById(R.id.radioButton3);
                    managerButton = radioGroup.findViewById(R.id.radioButton2);

                    if (chosen.getId() == studentButton.getId()) {
                        user.setManager("false");
                    } else if (chosen.getId() == managerButton.getId()) {
                        user.setManager("true");
                    }
                }

                if (studentID.getText().toString().length() != 10) {
                    Toast.makeText(CompleteProfile.this, "All student IDs must be 10 digits!", Toast.LENGTH_SHORT).show();
                    checkConditions = false;
                }

                user.setId(studentID.getText().toString());

                user.setDeleted(false);


                // delete later
                /*
                Building building = new Building();
                building.setName("USC Campus");

                //user.setCurrentBuilding(building);
                user.getHistory().put("USC", "1234 0123");

                // delete later

                //Building building = new Building();

                building.setName("USC");
                user.setCurrentBuilding(building);
                */

                if (checkConditions) {
                    user.getHistory().put("SLH", "0123 2344");

                    // Push user to DB
                    accountManipulator.createAccount(user);
                    Intent intent;
                    if (user.isManager().equals("true")) {
                        intent = new Intent(CompleteProfile.this, ManagerLanding.class);
                    } else {
                        intent = new Intent(CompleteProfile.this, StudentLanding.class);
                    }

                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }

            }
        });

        Back = (Button) findViewById(R.id.back3);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteProfile.this, Register.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        curr = mAuth.getCurrentUser();

//////////////

        profileImage = (ImageButton) findViewById(R.id.imageButton);
        uploadProfImage = (ImageButton) findViewById(R.id.imageButton3);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



        uploadProfImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    //select image
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //Upload local image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}