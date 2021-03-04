package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Manager class is an extension of the User class. The primary difference is that a Manager
 * has access to certain facilitation mechanisms that would be absent from a regular user.
 */
public class Manager extends User {
    /**
     * @param file
     * @return true if the CSV file has been successfully imported.
     */
    public Boolean importCSV(File file) { return true; }

    /**
     * @param building
     * @param capacity
     * @return true if the building capacity has been successfully updated.
     */
    public Boolean updateBuildingCapacity(Building building, int capacity) { return true; }

    /**
     * @param building
     * @return a list of the current users in a selected building.
     */
    public List<User> getPeopleInBuilding(Building building) { return new ArrayList<User>(); }

    /**
     * @param building
     * @return true if the manager was successfully able to retrieve the appropriate building QRCode
     * and show it to the user in question.
     */
    public Boolean showQRCode(Building building){ return true; }

    /**
     * @param time
     * @param building
     * @param id
     * @param major
     * @return the searched student.
     */
    public User searchStudents(String time, Building building, int id, String major) { return new User(); }
}
