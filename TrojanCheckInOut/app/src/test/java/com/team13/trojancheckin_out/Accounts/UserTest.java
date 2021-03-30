package com.team13.trojancheckin_out.Accounts;

import junit.framework.TestCase;
import com.team13.trojancheckin_out.UPC.Building;
import static com.team13.trojancheckin_out.Database.AccountManipulator.referenceUsers;
import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class UserTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetUserFunctionality() {
        String name = "Liza";
        String email = "moodye@usc.edu" ;
        String password = "1234";
        String photo = "photo/path";
        String id = "1234567890";
        boolean inBuilding = false;
        Building cb = null;
        String major = "computer science";
        User liza = new User(name, email, password, photo, id, inBuilding, cb, null, major, "");
        assertEquals(name, liza.getName());
        assertEquals(email, liza.getEmail());
        assertEquals(password, liza.getPassword());
        assertEquals(photo, liza.getPhoto());
        assertEquals(major, liza.getMajor());
        assertEquals(id, liza.getId());
        assertFalse(liza.isInBuilding());
    }

    public void testSetUserFunctionality() {
        String name = "Liza";
        String email = "moodye@usc.edu" ;
        String password = "1234";
        String photo = "photo/path";
        String id = "1234567890";
        boolean inBuilding = false;
        Building cb = null;
        String major = "computer science";
        User liza = new User(name, email, password, photo, id, inBuilding, cb, null, major, "");
        name = "Liza Moody";
        email = "moodye1@usc.edu";
        major = "CSBA";
        password = "12345";
        photo = "liza/photo/path";
        id = "0987654321";
        inBuilding = true;
        liza.setName(name);
        liza.setEmail(email);
        liza.setMajor(major);
        liza.setPassword(password);
        liza.setId(id);
        liza.setPhoto(photo);
        liza.setInBuilding(inBuilding);

        assertEquals(name, liza.getName());
        assertEquals(email, liza.getEmail());
        assertEquals(password, liza.getPassword());
        assertEquals(photo, liza.getPhoto());
        assertEquals(major, liza.getMajor());
        assertEquals(id, liza.getId());
        assertTrue(liza.isInBuilding());

    }


    public void testBuilding() {

        User liza = new User("Liza", "moodye@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        Building SAL = new Building("SAL", "sal", 75, "qrCode/path");
        liza.setCurrentBuilding(SAL);
        liza.setInBuilding(true);
        assertEquals(SAL, liza.getCurrentBuilding());
        assertTrue(liza.isInBuilding());
        liza.setCurrentBuilding(null);
        liza.setInBuilding(false);
        assertEquals(null, liza.getCurrentBuilding());
        assertFalse(liza.isInBuilding());
        assertTrue(liza.checkOutManual());
    }
}