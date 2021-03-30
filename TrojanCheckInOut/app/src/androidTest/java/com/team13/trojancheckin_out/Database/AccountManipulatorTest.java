package com.team13.trojancheckin_out.Database;

import junit.framework.TestCase;


public class AccountManipulatorTest extends TestCase {
    private AccountManipulator accountManipulator;
    private BuildingManipulator buildingManipulator;

    public void testGetAllAccounts() {
    }

    public void testGetStudentAccounts() {
    }

    public void testGetManagerAccounts() {
    }

    public void testVerifyEmail() {
        String email = "moodye@usc.edu";
        if (accountManipulator.verifyEmail(email)){
            System.out.println("email verified");
        }
        else System.out.println("not verigied");
        assertTrue(accountManipulator.verifyEmail(email));


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