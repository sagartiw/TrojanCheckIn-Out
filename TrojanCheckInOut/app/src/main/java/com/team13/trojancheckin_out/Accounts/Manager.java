package com.team13.trojancheckin_out.Accounts;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Manager class is an extension of the User class. The primary difference is that a Manager
 * has access to certain facilitation mechanisms that would be absent from a regular user.
 */
public class Manager extends User {

    BuildingManipulator buildingManipulator;
    AccountManipulator accountManipulator;
    public Manager(BuildingManipulator buildingManipulator, AccountManipulator accountManipulator) {
        this.buildingManipulator = buildingManipulator;
        this.accountManipulator = accountManipulator;
    }

    /**
     * @param file
     * @return true if the CSV file has been successfully imported.
     */
    public Boolean importCSV(File file) {
        buildingManipulator.processCSV(file);
        return true;
    }

    /**
     * @param building
     * @param capacity
     * @return true if the building capacity has been successfully updated.
     */
    public Boolean updateBuildingCapacity(Building building, int capacity) {
        buildingManipulator.referenceBuildings.child(building.getAbbreviation()).child("capacity").setValue(capacity);
        return true;
    }

    /**
     * @param building
     * @return a list of the current users in a selected building.
     */
    public List<User> getPeopleInBuilding(Building building) {
        return buildingManipulator.getCurrentBuildings().get(building).getCurrentStudents();
    }

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
    public List<User> searchStudents(String time, Building building, String id, String major) {
        // TODO: time

        List<User> list = new ArrayList<>();
        if (id != null) {
            list.add(accountManipulator.getStudentAccounts().get(id));
            return list;
        } else if (building != null){
            return building.getCurrentStudents();
        } else if (major != null) {
            for (User user : accountManipulator.getStudentAccounts().values()) {
                if (user.getMajor().equals(major)) {
                    list.add(user);
                }
            }
            return list;
        }

        return list;
    }
}
