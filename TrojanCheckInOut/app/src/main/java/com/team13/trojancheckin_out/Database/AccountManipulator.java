package com.team13.trojancheckin_out.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.HashMap;
import java.util.List;
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

    private List<User> studentAccounts;
    private List<User> managerAccounts;

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
    public List<User> getStudentAccounts() {

        // If isManager == false, then that account is a student account
        referenceUsers.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        i++;
                        User user = ds.getValue(User.class);
                        System.out.println(i + ": NAME " + user.getEmail());
                        System.out.println(i + ": Definitely Manager: " + user.isManager());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // read query is cancelled.
                }
        });

        return this.studentAccounts;
    }

    /**
     * @return the current list of registered student accounts. Same concept as getStudentAccounts.
     */
    public List<User> getManagerAccounts() {
        return this.managerAccounts;
    }

    /**
     * @param email
     * @return true if the user email has been successfully verified.
     */
    public Boolean verifyEmail(String email) { return true; }

    /**
     * @return true if the user account has been successfully created.
     */
    public Boolean createAccount(User user) {

        // Take in parameters and create a new user in the DB
        referenceUsers.child(user.getId()).setValue(user);
        return true;
    }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) {

        // Delete the user from the DB
        referenceUsers.child(user.getId()).removeValue();
        return true;
    }

    /**
     * @return true if the user has successfully logged in.
     */
    public Boolean login() { return true; }

    /**
     * @return true if the user has successfully logged out.
     */
    public Boolean logout() { return true; }
}
