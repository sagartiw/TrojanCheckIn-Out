package com.team13.trojancheckin_out.UPC;

import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.BuildingManipulator;

import java.util.List;

/**
 * This class contains the instructions for creating a Building object. Each Building represents
 * a unique building structure on USC's University Park Campus. Building objects can facilitate
 * User activity by removing and admitting students as well as updating internal capacity.
 */
public class Building {
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
    public Building(int capacity, List<User> students, String QRCode) {
        this.capacity = capacity;
        this.students = students;
        this.QRCode = QRCode;
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
     * @return the building's list of admitted students.
     */
    public List<User> getCurrentStudents() { return this.students; }

    /**
     * Updates the building capacity.
     * @param capacity
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    /**
     * @param user
     * @return true if the student has been successfully removed from the building.
     */
    public Boolean removeStudent(User user) { return true; }

    /**
     * @param user
     * @return true if the student has been successfully added into the building.
     */
    public Boolean addStudent(User user) { return true; }
}
