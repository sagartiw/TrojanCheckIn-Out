package com.team13.trojancheckin_out.Database;

import com.team13.trojancheckin_out.Accounts.User;

import java.util.List;

/**
 * This class generates and deletes user accounts, facilitates logins and logouts, and provides access
 * to the main list of student and managerial accounts. This is where most communication with the
 * databse will take place.
 */
public class AccountManipulator extends User {
    private List<User> studentAccounts;
    private List<User> managerAccounts;

    /**
     * @return the current list of registered student accounts.
     */
    public List<User> getStudentAccounts() { return this.studentAccounts; }

    /**
     * @return the current list of manager accounts.
     */
    public List<User> getManagerAccounts() { return this.managerAccounts; }

    /**
     * @param email
     * @return true if the user email has been successfully verified.
     */
    public Boolean verifyEmail(String email) { return true; }

    /**
     * @param email
     * @param password
     * @param id
     * @return true if the user account has been successfully created.
     */
    public Boolean createAccount(String email, String password, int id) { return true; }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) { return true; }

    /**
     * @return true if the user has successfully logged in.
     */
    public Boolean login() { return true; }

    /**
     * @return true if the user has successfully logged out.
     */
    public Boolean logout() { return true; }
}
