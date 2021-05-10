package com.team13.trojancheckin_out.Layouts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.AccountManipulator.resetFromStart;

public class EditProfile extends AppCompatActivity {

    private Button Back3; //id back3
    private Button yeetusDeletus; //id back3
    private User user;
    private TextView name;
    private TextView bigName;
    private TextView id;
    private TextView major;
    private ImageView pfp;
    private Button editpic;
    private ImageButton profileImage;
    private ImageButton uploadProfImage;
    private ImageView viewPFP;
    private Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private TextView progress;
    private TextView mText;
    private static final int PERMISSION_CODE = 1;
    private static final int PICK_IMAGE = 1;
    String filePath2;
    Map config = new HashMap();



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

        new DownloadImageTask((ImageView)findViewById(R.id.pfp)).execute(user.getPhoto());

        bigName = (TextView) findViewById(R.id.name);
        bigName.setText(user.getName());


        name = (TextView) findViewById(R.id.name2);
        name.setText(user.getName());

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
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                Button submitButton = (Button) popupView.findViewById(R.id.button11);


                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("closed popup w x");
                        popupWindow.dismiss();
                    }
                });

                //change password
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText oldPassword = (EditText) popupView.findViewById(R.id.editTextTextPassword);
                        EditText newPassword = (EditText) popupView.findViewById(R.id.editTextTextPassword4);
                        EditText confirmPassword = (EditText) popupView.findViewById(R.id.editTextTextPassword5);
                        System.out.println("old password edittext: " + oldPassword);
                        System.out.println("old password text: " + oldPassword.getText());
                        System.out.println("old password to string: " + oldPassword.getText().toString());


                        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                            System.out.println("EMAIL ERROR!");

                            Toast.makeText(EditProfile.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                            //Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                        }

                        // Check password size >= 4
                        else if (newPassword.getText().toString().length() < 4) {
                            Toast.makeText(EditProfile.this, "Passwords must be at least 4 characters", Toast.LENGTH_SHORT).show();
                            //System.out.println("I FUCKED UP SIZE");
                            //Toast.makeText(Register.this, "Password must be greater than 4 characters!", Toast.LENGTH_SHORT).show();
                        }

                        //check if new passwords match
                        else if (newPassword.getText().toString().equals(oldPassword.getText().toString())) {
                            System.out.println("same as old password");
                            Toast.makeText(EditProfile.this, "This is your current password, please choose a different one", Toast.LENGTH_SHORT).show();

                        }
                        //new password set
                        else if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                            System.out.println("confirm password change");
                            referenceUsers.child(user.getId()).child("password").setValue(newPassword.getText().toString());

                            popupWindow.dismiss();
                        }
                        else {
                            Toast.makeText(EditProfile.this, "Please choose a different password", Toast.LENGTH_SHORT).show();

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

                        Intent intent = new Intent(v.getContext(), Startup.class);
                        popupWindow.dismiss();
                        user = null;
                        intent.putExtra("PrevPageData", user);

                        System.out.println("DELETING USER HERE");
                        resetFromStart = true;

                        v.getContext().startActivity(intent);

//                        startActivity(new Intent(v.getContext(), Startup.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                        finishAndRemoveTask();
//                        finishAffinity();

                    }
                });

            }
        });

        ImageButton changePic = (ImageButton) findViewById(R.id.imageButton3);
        ImageView pfp = (ImageView) findViewById(R.id.pfp);
        StorageReference pfp2 = FirebaseStorage.getInstance().getReference().child(user.getPhoto());

        System.out.println("This is the user photo in student landing" + user.getPhoto());
        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.upload_image_popup, null);
                EditText urlInput = (EditText) popupView.findViewById(R.id.editTextURL);
                Button submitUrl = (Button) popupView.findViewById(R.id.button13);
                Button galleryUpload = (Button) popupView.findViewById(R.id.button15);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);

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

                submitUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Submit URL!");
                        String url = urlInput.getText().toString();
                        // only set url if it isn't empty
                        if (!url.equalsIgnoreCase("")) {
                            user.setPhoto(url);
                            referenceUsers.child(user.getId()).child("photo").setValue(url);

                        }

                        popupWindow.dismiss();
                    }
                });

                galleryUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        requestPermission();
                        popupWindow.dismiss();                    }
                });


                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


            }
        });

        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Bring up gallery to select a photo
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });
    }


    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (EditProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    EditProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery();
            } else {
                Toast.makeText(EditProfile.this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void accessTheGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }



    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    //select image
    private void chooseImage() {
        System.out.println("starting choose image");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO_CODE);
        System.out.println("finish choose image");
    }

    private void uploadImage() {
        System.out.println("filepath in Upload img: " + filePath);
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // StorageReference selectedFile = storageRef.child("Profile Pictures/");
            //"profile pics/ or images/" for ref?"
            //StorageReference ref = storageRef.child("Profile Pictures/"+ UUID.randomUUID().toString());
            StorageReference ref = storageRef.child("Profile Pictures/" + filePath.getLastPathSegment());

            System.out.println("upload image function");
            filePath = Uri.fromFile(new File(filePath.getPath()));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("upload failed..... function");

                            progressDialog.dismiss();
                            Toast.makeText(EditProfile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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