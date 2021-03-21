package com.team13.trojancheckin_out.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
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
import java.util.Objects;

/**
 * This class generates and deletes user accounts, facilitates logins and logouts, and provides access
 * to the main list of student and managerial accounts. This is where most communication with the
 * databse will take place.
 */
public class AccountManipulator extends User {

    // Access the Firebase
    public static final FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    public static final DatabaseReference referenceUsers = rootNode.getReference("Users");

    private Map<String, User> studentAccounts = new HashMap<>();
    private Map<String, User> managerAccounts = new HashMap<>();

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
    public Map<String, User> getStudentAccounts() {
        referenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("I'm in onDataChange");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);

                        System.out.println("MY NAME IS: " + user.getName());
                        if (user.isManager().equalsIgnoreCase("False")) {
                            System.out.println("Name: " + user.getName() + " Email: " + user.isManager());
                            studentAccounts.put(user.getId(), user);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
        });

        return this.studentAccounts;
    }

    /**
     * @return the current list of registered student accounts. Same concept as getStudentAccounts.
     */
    public Map<String, User> getManagerAccounts() {
        referenceUsers.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);
                            if (user.isManager().equals("True")) {
                                System.out.println("Name: " + user.getName() + " Email: " + user.isManager());
                                managerAccounts.put(user.getId(), user);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

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
        referenceUsers.child(user.getId()).setValue(user);
//        if (user.isManager().equalsIgnoreCase("true")) {
//            managerAccounts.put(user.getId(), user);
//        }
//        else {
//            studentAccounts.put(user.getId(), user);
//        }
        return true;
    }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) {
        referenceUsers.child(user.getId()).removeValue();

//        if (user.isManager().equalsIgnoreCase("true")) {
//            managerAccounts.remove(user.getId());
//        }
//        else {
//            studentAccounts.remove(user.getId());
//        }
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
