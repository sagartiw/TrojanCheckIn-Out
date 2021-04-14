package com.team13.trojancheckin_out.Layouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;

import java.io.IOException;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;

public class EditProfile extends AppCompatActivity {

    private Button Back3; //id back3
    private Button yeetusDeletus; //id back3
    private User user;
    private TextView name, bigName;
    private TextView name2;
    private TextView id;
    private TextView major;
    private ImageView pfp;
    private Button editpic;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private AccountManipulator accountManipulator = new AccountManipulator();

    //https://firebase.google.com/docs/storage/android/upload-files
    public final static int PICK_PHOTO_CODE = 1046;
    private int x;
    //https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = (User) getIntent().getSerializableExtra("PrevPageData");


        name = (TextView) findViewById(R.id.name2);
        name.setText(user.getName());

        bigName = (TextView) findViewById(R.id.name);
        bigName.setText(user.getName());

        id = (TextView) findViewById(R.id.name3);
        id.setText(user.getId());

        major = (TextView) findViewById(R.id.name4);
        major.setText(user.getMajor());

//        //error... we need to change lines 79-85 to firebase storage access
//        pfp = (ImageView) findViewById(R.id.pfp);
//        int imageRe = -1;
//        imageRe = getResources().getIdentifier(user.getPhoto(), null, getPackageName());
//        if(imageRe != -1){
//            Drawable d =  getResources().getDrawable(imageRe);
//            pfp.setImageDrawable(d);
//        }



        Back3 = (Button)findViewById(R.id.back3);

        Back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, StudentLanding.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        Button changePass = (Button) findViewById(R.id.changePassword);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.change_password_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                Button submitter = (Button) popupView.findViewById(R.id.button11);
                EditText oldP = (EditText) popupView.findViewById(R.id.editTextTextPassword);
                EditText newP = (EditText) popupView.findViewById(R.id.editTextTextPassword4);
                EditText confP = (EditText) popupView.findViewById(R.id.editTextTextPassword5);


                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                submitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(oldP.getText().toString() + " : oldP");
                        System.out.println(user.getPassword() + " : user pass");
                        System.out.println(newP.getText().toString() + " : newP");
                        System.out.println(confP.getText().toString() + " : confP");

                        if(oldP.getText().toString().matches(user.getPassword()) && newP.getText().toString().matches(confP.getText().toString())){
                            System.out.println(oldP.getText().toString() + " : oldP");
                            System.out.println(user.getPassword() + " : user pass");
                            System.out.println(newP.getText().toString() + " : newP");
                            System.out.println(confP.getText().toString() + " : confP");
                            referenceUsers.child(user.getId()).child("password").setValue(newP.getText().toString());
                            submitter.setText("Success");
                            popupWindow.dismiss();
                        }
                    }
                });

            }
        });

        Button deleteAccount = (Button) findViewById(R.id.deleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.delete_account_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);
                Button submit = (Button) popupView.findViewById(R.id.button10);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //referenceUsers.child(user.getId()).removeValue();
                        // call delete account
                        accountManipulator.deleteAccount(user);
                        currentUser = null;
                        //popupWindow.dismiss();

                        // Intent intent = new Intent(v.getContext(), Startup.class);
                        // v.getContext().startActivity(intent);
                        startActivity(new Intent(v.getContext(), Startup.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finishAndRemoveTask();
                        finishAffinity();

                    }
                });

            }
        });

        ImageButton changePic = (ImageButton) findViewById(R.id.imageButton3);
        ImageView pfp = (ImageView) findViewById(R.id.pfp);
        StorageReference pfp2 = FirebaseStorage.getInstance().getReference().child(user.getPhoto());

        System.out.println("This is the user photo in student landing" + user.getPhoto());
        Glide.with(getApplicationContext()).load(pfp2).into(pfp);
//        changePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // inflate the layout of the popup window
//                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.choose_profile_pic, null);
//                ImageView tommy = (ImageView) popupView.findViewById(R.id.man);
//                ImageView hecuba = (ImageView) popupView.findViewById(R.id.woman);
//                ImageView traveller = (ImageView) popupView.findViewById(R.id.horse);
//                Button closeButton = (Button) popupView.findViewById(R.id.button6);
//
//                // create the popup window
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                popupWindow.setElevation(20);
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window token
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//                tommy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("CLICKED TOMMY!");
//                        String tommy = "@drawable/usc_day_in_troy_mcgillen_012917_3907";
//                        user.setPhoto(tommy);
//                        referenceUsers.child(user.getId()).child("photo").setValue(tommy);
//                        popupWindow.dismiss();
//                        Intent intent = new Intent(v.getContext(), EditProfile.class);
//                        intent.putExtra("PrevPageData", user);
//                        startActivity(intent);
//                    }
//                });
//
//                hecuba.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("CLICKED HECUBA!");
//                        String hecuba = "@drawable/hecuba";
//                        user.setPhoto(hecuba);
//                        popupWindow.dismiss();
//                        referenceUsers.child(user.getId()).child("photo").setValue(hecuba);
//                        Intent intent = new Intent(v.getContext(), EditProfile.class);
//                        intent.putExtra("PrevPageData", user);
//                        startActivity(intent);
//                    }
//                });
//
//                traveller.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("CLICKED TRAVELLER!");
//                        String traveller = "@drawable/traveller";
//                        user.setPhoto(traveller);
//                        referenceUsers.child(user.getId()).child("photo").setValue(traveller);
//                        popupWindow.dismiss();
//                        Intent intent = new Intent(v.getContext(), EditProfile.class);
//                        intent.putExtra("PrevPageData", user);
//                        startActivity(intent);
//                    }
//                });
//
//
//                // dismiss the popup window when touched
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });
//            }
//        });

        changePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Bring up gallery to select a photo
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            String filepath = photoUri.getPath();
            System.out.println("This is the filepath of the local file: " + filepath);

            StorageReference selectedFile = storageRef.child("Profile Pictures/" + photoUri.getLastPathSegment());
            UploadTask uploadTask = selectedFile.putFile(photoUri);

            user = (User) getIntent().getSerializableExtra("PrevPageData");

            user.setPhoto("Profile Pictures/" + photoUri.getLastPathSegment());

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });

        }
    }
}