package com.team13.trojancheckin_out.Database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.HashMap;
import java.util.Map;

/**
 * This class generates and deletes user accounts, facilitates logins and logouts, and provides access
 * to the main list of student and managerial accounts. This is where most communication with the
 * databse will take place.
 */
public class AccountManipulator extends User {

    // Access the Firebase
    public static final FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    public static final DatabaseReference referenceUsers = rootNode.getReference("Users");

<<<<<<< HEAD
    private Map<String, User> studentAccounts = new HashMap<>();
    private Map<String, User> managerAccounts = new HashMap<>();
=======
    private static Map<String, User> studentAccounts;
    private static Map<String, User> managerAccounts;
    private static Map<String, User> allAccounts;
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
<<<<<<< HEAD
    public Map<String, User> getStudentAccounts() {
=======
    public void getAllAccounts(MyUserCallback myUserCallback) {
        allAccounts = new HashMap<>();

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    allAccounts.put(user.getId(), user);
                }

                myUserCallback.onCallback(allAccounts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
    public void getStudentAccounts(MyUserCallback myUserCallback) {
        studentAccounts = new HashMap<>();
        managerAccounts = new HashMap<>();

>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
        referenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.isManager().equalsIgnoreCase("false")) {
                            studentAccounts.put(user.getId(), user);
                        }
                    }

<<<<<<< HEAD
                    System.out.println("before leaving datachange");
=======
                    myUserCallback.onCallback(studentAccounts);
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }


        });
<<<<<<< HEAD
        System.out.println("BEFORE RETURN");
        for (User u : studentAccounts.values()) {
            System.out.println("My name: " + u.getName());
        }
        return studentAccounts;

=======
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
    }

    /**
     * @return the current list of registered student accounts. Same concept as getStudentAccounts.
     */
<<<<<<< HEAD
    public Map<String, User> getManagerAccounts() {
        referenceUsers.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);
                            if (user.isManager().equalsIgnoreCase("true")) {
                                managerAccounts.put(user.getId(), user);
                            }
                        }
=======
    public void getManagerAccounts(MyUserCallback myUserCallback) {
        studentAccounts = new HashMap<>();
        managerAccounts = new HashMap<>();

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.isManager().equalsIgnoreCase("true")) {
                        studentAccounts.put(user.getId(), user);
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
                    }
                }

                myUserCallback.onCallback(studentAccounts);
            }

<<<<<<< HEAD
        return managerAccounts;
=======
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
    }

    /**
     * @param email
     * @return true if the user email has been successfully verified.
     */
    public Boolean verifyEmail(String email) {
        return true;
    }

    /**
     * @return true if the user account has been successfully created.
     */
    public Boolean createAccount(User user) {
        referenceUsers.child(user.getId()).setValue(user);
<<<<<<< HEAD
        if (user.isManager().equalsIgnoreCase("true")) {
            managerAccounts.put(user.getId(), user);
        } else {
            studentAccounts.put(user.getId(), user);
        }
=======
//        if (user.isManager().equalsIgnoreCase("true")) {
//            managerAccounts.put(user.getId(), user);
//        } else {
//            studentAccounts.put(user.getId(), user);
//        }
>>>>>>> 7fc335daec752a962f124b432cc9e8e45eb2e024
        return true;
    }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) {
        referenceUsers.child(user.getId()).removeValue();
        if (user.isManager().equalsIgnoreCase("true")) {
            managerAccounts.remove(user.getId());
        } else {
            studentAccounts.remove(user.getId());
        }
        return true;
    }

    /**
     * @return true if the user has successfully logged in.
     */
    public Boolean login() {
        //gets authorization
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = rootNode.getCurrentUser();
            if(currentUser != null){
                reload();
            }
        }


        rootNode.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // nformation
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = rootNode.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            //sign in failed
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        return true;
    }

    /**
     * @return true if the user has successfully logged out.
     */
    public Boolean logout() { return true; }
}
