package com.team13.trojancheckin_out.UPC;

import junit.framework.TestCase;
import com.team13.trojancheckin_out.Accounts.User;


public class BuildingTest extends TestCase {
    public Building b = new Building("Salvatori", "", 50, "salQR");


    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testAbbreviation() {
        String abrev = "GFS";
        b.setAbbreviation(abrev);
        assertEquals(abrev, b.getAbbreviation());
    }

    public void testGetSetName() {
        String name = "Salvatori Hall";
        b.setName(name);
        assertEquals(name, b.getName());
        name = "Grace Ford Salvatori Hall";
        b.setName(name);
        assertEquals(name, b.getName());
    }


    public void testGetSetCapacity() {
        b.setCapacity(-1);
        assertEquals(0, b.getCapacity());
        b.setCapacity(50);
        assertEquals(50, b.getCapacity());
    }

    public void testSetCapacity() {
        int cap = 100;
        b.setCapacity(cap);
        assertEquals(cap, b.getCapacity());
    }

    public void testGetQRCode() {
        b.setQRCode("gfsQR");
        assertEquals("gfsQR", b.getQRCode());
        b.setQRCode("users/path/qrcode");
        assertEquals("users/path/qrcode", b.getQRCode());
    }

    public void testSetQRCode() {
        String code = "setQR";
        b.setQRCode(code);
        assertEquals(code, b.getQRCode());
    }

    public void testGetCurrentStudents() {

        User a = new User("will", "w@usc.edu", "123", "user/path", "1234567890",
        true, b, null, "meche", "true");
        User c = new User("cara", "c@usc.edu", "123", "user/path", "1234567890",
                false, b, null, "meche", "false");
       // b.addStudent(a);
      //  b.addStudent(c);
       // assertEquals(2, b.getCurrentCount());
        assert(b.isInBuilding(a));


    }

    public void testSetStudents() {
    }

    public void testGetCurrentCount() {
    }

    public void testGetPercent() {
    }



    public void testIsInBuilding() {
    }

    public void testRemoveStudent() {
    }

    public void testAddStudent() {
    }
}