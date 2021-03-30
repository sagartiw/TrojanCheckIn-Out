package com.team13.trojancheckin_out.Database;

import junit.framework.TestCase;


public class AccountManipulatorTest extends TestCase {
    private AccountManipulator accountManipulator;
    private BuildingManipulator buildingManipulator;

    public void testAccountAccess() {


    }

    public void testVerifyEmail() {
        String email = "moodye@usc.edu";
        assertTrue(accountManipulator.verifyEmail(email));
        email = "moodye@gmail.com";
        assertFalse(accountManipulator.verifyEmail(email));


    }

    public void testCreateAccount() {
    }

    public void testDeleteAccount() {
    }

    public void testLogin() {
    }

    public void testLogout() {
    }
}