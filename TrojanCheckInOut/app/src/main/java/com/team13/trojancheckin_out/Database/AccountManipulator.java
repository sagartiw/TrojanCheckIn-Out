package com.team13.trojancheckin_out.Database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private static final FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    private static final DatabaseReference referenceUsers = rootNode.getReference("Users");

    private List<User> studentAccounts;
    private List<User> managerAccounts;

    /**
     * Accesses the Google Firebase to parse the JSON data into Java "User" objects and into
     * the studentAccounts and managerAccounts data structures.
     */
    public void jsonToJava() { }

    /**
     * @return the current list of registered student accounts.
     */
    public List<User> getStudentAccounts() { return this.studentAccounts; }

    /**
     * @return the current list of manager accounts.
     */
    public List<User> getManagerAccounts() { return this.managerAccounts; }

    /**
     * @param email
     * @return true if the user email has been successfully verified.
     */
    public Boolean verifyEmail(String email) { return true; }

    /**
     * @param email
     * @param password
     * @param id
     * @return true if the user account has been successfully created.
     */
    public Boolean createAccount(User user) {

        // Take in parameters and create a new user in the DB
        referenceUsers.child(Integer.toString(user.getId())).setValue(user);
        return true;
    }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) {

        // Delete the user from the DB
        referenceUsers.child(Integer.toString(user.getId())).removeValue();
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
