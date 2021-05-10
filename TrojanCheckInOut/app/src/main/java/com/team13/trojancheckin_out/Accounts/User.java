package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.UPC.Building;

import java.io.Serializable;
import java.util.Map;

import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;


class TimeStamps {
    String checkInTime = "";
    String checkOutTime = "";
}

/**
 * This class contains the objects that define a user's account. Further, it establishes mechanisms
 * for the user to perform alterations to their own profile as well as interactions with buildings.
 */
public class User implements Serializable {
    private String name;
    private String email;
    private String password;

    // Have to use an image builder, using String for now
    private String photo;

    private String id;
    private boolean inBuilding;
    private Building currentBuilding;
    private Map<String, String> history;
    private String major;
    private String isManager;
    private String lastName;

    private boolean deleted;
    private boolean kickedOut = false;

    /**
     * User object via a default constructor.
     */
    public User() { }

    /**
     * Accesses private data members of User for account creation via a constructor.
     * @param name
     * @param email
     * @param password
     * @param photo
     * @param id
     * @param inBuilding
     * @param currentBuilding
     * @param history
     * @param major
     * @param isManager
     * @param deleted
     */
    public User (String name, String email, String password, String photo, String id,
                 boolean inBuilding, Building currentBuilding, Map<String, String> history,
                 String major, String isManager, boolean deleted) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.id = id;
        this.inBuilding = inBuilding;
        this.currentBuilding = currentBuilding;
        this.history = history;
        this.major = major;
        this.isManager = isManager;
        this.deleted = deleted;
        this.kickedOut = false;
        this.lastName = name.split(",")[0];
    }

    /**
     * Getters and Setters for the User class
     */
    public String getName() {
        return name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setName(String name) {

        System.out.println("I AM SETTING NAME");
        this.name = name;
        this.lastName = name.split(",")[0];
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {

        // ONLY RETURN IF VALID PHOTO

        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {

        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStringId(String id) {
        this.id = id;
    }

    public boolean isInBuilding() {
        return inBuilding;
    }

    public void setInBuilding(boolean inBuilding) {
        this.inBuilding = inBuilding;
    }

    public boolean isKickedOut() { return kickedOut; }

    public void setKickedOut(boolean setter) { this.kickedOut = setter; }

    public void setterKickedOut(boolean setter) {
        this.kickedOut = setter;
        referenceUsers.child(this.getId()).child("kickedOut").setValue(setter);
    }

    public void setterInBuilding(boolean inBuilding) {
        this.inBuilding = inBuilding;
        referenceUsers.child(this.getId()).child("inBuilding").setValue(inBuilding);
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

   public void setCurrentBuilding(Building currentBuilding) {

        this.currentBuilding = currentBuilding;
   }

   public void setterCurrentBuilding(Building currentBuilding) {
       this.currentBuilding = currentBuilding;
       Building b = new Building(currentBuilding.getName(), currentBuilding.getAbbreviation(), currentBuilding.getCapacity(), currentBuilding.getCurrentCount(), currentBuilding.getQRCode());
       referenceUsers.child(this.getId()).child("currentBuilding").setValue(b);
   }

    public Map<String, String> getHistory() {
        return history;
    }

    public void setHistory(Map<String, String> history) {
        this.history = history;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String isManager() {
        return isManager;
    }

    public void setManager(String isManager) {
        this.isManager = isManager;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the profile of a student. Going to use a String for now, but in the future
     * it will return an image in the GUI.
     */
    public String showProfile() { return "Profile"; }

    /**
     * Updates the profile photo of the current user's profile.
     * @param photo
     */
    public void updateProfilePicture(String photo) { this.photo = photo; }

    /**
     * @param QRCode
     * @return true if the user has successfully managed to retrieve and scan a building QRCode.
     */
    public Boolean scanQRCode(String QRCode) { return true; }

    /**
     * @return true if the user has successfully checked out of the building complex.
     */
    public Boolean checkOutManual() { return true; }

    /**
     * Updates the password of a current user's profile.
     * @param password
     */
    public void changePassword(String password) { this.password = password; }
}
