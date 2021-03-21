package com.team13.trojancheckin_out.UPC;

import com.team13.trojancheckin_out.Accounts.User;

import java.util.List;

import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;

/**
 * This class contains the instructions for creating a Building object. Each Building represents
 * a unique building structure on USC's University Park Campus. Building objects can facilitate
 * User activity by removing and admitting students as well as updating internal capacity.
 */
public class Building {

    private String abbreviation;
    private String name;
    private int capacity;
    private List<User> students;

    // Have to use an image builder, using String for now
    private String QRCode;

    /**
     * Accesses Building object via a default constructor.
     */
    public Building() { }

    /**
     * Accesses private data members of Building for building creation via constructor.
     * @param capacity
     * @param students
     * @param QRCode
     */
    public Building(String name, String abbreviation, int capacity, List<User> students, String QRCode) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.capacity = capacity;
        this.students = students;
        this.QRCode = QRCode;
    }

    /**
     * @return the name of the current building.
     */
    public String getName() { return this.name; }

    /**
     * @return the abbreviation of the name of the current building.
     */
    public String getAbbreviation() { return this.abbreviation; }

    /**
     * @return the number of students currently in the building.
     */
    public int getCurrentCount() { return this.students.size(); }

    /**
     * @return percentage of building filled up.
     */
    public int getPercent() {
        double cur = (double) this.getCurrentCount();
        double cap = (double) this.capacity;
        double perc = (cur/cap)*100;
        int percent = (int) perc;
        return percent;
    }

    /**
     * @return the building's QRCode.
     */
    public String getQRCode() { return this.QRCode; }

    /**
     * @return the building's capacity.
     */
    public int getCapacity() { return this.capacity; }

    /**
     * Sets the name of the building
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the building's list of admitted students.
     */
    public List<User> getCurrentStudents() { return this.students; }

    /**
     * Updates the building capacity.
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        referenceBuildings.child(abbreviation).child("capacity").setValue(capacity);
    }

    /**
     * @param user
     * @return true if the student has been successfully removed from the building.
     */
    public Boolean removeStudent(User user) {
        students.remove(user);
        referenceBuildings.child(abbreviation).child("students").setValue(user);
        return true;
    }

    /**
     * @param user
     * @return true if the student has been successfully added into the building.
     */
    public Boolean addStudent(User user) {
        students.add(user);
        referenceBuildings.child(name).child("students").setValue(user);
        return true;
    }
}
