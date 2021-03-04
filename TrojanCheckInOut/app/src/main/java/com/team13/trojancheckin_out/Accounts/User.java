package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.UPC.Building;

import java.util.List;

/**
 * This class contains the objects that define a user's account. Further, it establishes mechanisms
 * for the user to perform alterations to their own profile as well as interactions with buildings.
 */
public class User {
    private String name;
    private String email;
    private String password;

    // Have to use an image builder, using String for now
    private String photo;

    private int id;
    private boolean inBuilding;
    private Building currentBuilding;
    private List<Building> history;
    private String major;
    private boolean isManager;

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
     */
    public User (String name, String email, String password, String photo, int id,
                 boolean inBuilding, Building currentBuilding, List<Building> history,
                 String major, boolean isManager) {
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
