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

import java.util.Map;

public class UserTest extends TestCase {

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

    public void testUpdateStudentHistory() {

        User liza = new User("Liza", "moodye@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        Building SAL = new Building("SAL", "sal", 75, "qrCode/path");
        Building doheny = new Building("Doheny Library", "d", 75, "qrCode/path");
        Building EVK = new Building("Everybody's Kitchen", "EVK", 75, "qrCode/path");
        Building tutorCenter = new Building("Tutor Campus Center", "TCC", 140, "qrCode/path");
        Map<String, String> history = liza.getHistory();
        history.put(SAL.getName(), SAL.getAbbreviation());
        history.put(doheny.getName(), doheny.getAbbreviation());
        history.put(tutorCenter.getName(), tutorCenter.getAbbreviation());
        history.put(EVK.getName(), EVK.getAbbreviation());
        assertTrue(history.containsKey(SAL.getName()));
        assertTrue(history.containsKey(doheny.getName()));
        assertTrue(history.containsKey(EVK.getName()));
        assertTrue(history.containsKey(tutorCenter.getName()));
    }

    public void testManagerStatus(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        assertEquals("no", user.isManager());

    }

    public void testChangeManagerStatus(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        assertEquals("no", user.isManager());
        user.setManager("yes");
        assertEquals("yes", user.isManager());
    }
    public void testChangePassword(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        assertEquals("1234", user.getPassword());
        user.setPassword("4321");
        assertEquals("4321", user.getPassword());
    }

    public void testReturnEmail(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        assertEquals("user@usc.edu", user.getEmail());

    }
    public void testChangeEmail(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        user.setEmail("newUser@usc.edu");
        assertEquals("newUser@usc.edu", user.getEmail());
    }
    public void testReturnMajor(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        assertEquals("CSBA", user.getMajor());

    }
    public void testUpdateMajor(){
        User user = new User("User", "user@usc.edu", "1234", "userpath/photo", "1234567890", false, null, null, "CSBA", "no");
        user.setMajor("computer science");
        assertEquals("computer science", user.getMajor());
    }



}