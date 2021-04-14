package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyBuildingCallback;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        //return buildingManipulator.getCurrentBuildings().get(building).getCurrentStudents();
        return new ArrayList<User>();
    }

    /**
     * @param building
     * @return true if the manager was successfully able to retrieve the appropriate building QRCode
     * and show it to the user in question.
     */
    public Boolean showQRCode(Building building){ return true; }

    /**
     * @param user
     * @param fName
     * @param lName
     * @param list
     * @return the searched name in the list of users
     */
    public User searchName(User user, String fName, String lName, List<User> list) {

        System.out.println("SEARCH NAME: " + user.getName() + " " + fName + " " + lName + " " + list);
        if (lName == null && fName == null) {
            return null;
        }

        // Checking name parameters: name[0] = last, name[1] = first
        String[] name = user.getName().split(", ");

        if (lName != null && fName == null) {
            System.out.println("lName: " + lName.toLowerCase() + " name[0]: " + name[0] + " Condition: " + name[0].toLowerCase().contains(lName.toLowerCase()));
            if (name[0].toLowerCase().contains(lName.toLowerCase())) {
                System.out.println("LAST NAME: " + lName);
                return user;
            }
        } else if (lName == null && fName != null) {
            if (name[1].toLowerCase().contains(fName.toLowerCase())) {
                System.out.println("FIRST NAME: " + fName);
                return user;
            }
        } else {
            if (name[1].toLowerCase().contains(fName.toLowerCase()) && name[0].toLowerCase().contains(lName.toLowerCase())) {
                System.out.println("NAME MATCH: " + fName + lName);
                return user;
            }
        }

        return null;
    }

    /**
     * @param building
     * @param id
     * @param major
     * @return the searched student.
     */
    public List<User> searchStudents(String fName, String lName, int startTime, int endTime, Building building, String id, String major, int startDate, int endDate) {
        // IF WE ARE NOT SEARCHING BY TIME, ENTER "-1" into the startTime parameter.

//        // Order based on name
//        List<String> orderedList = new ArrayList<>();
//        for (Map.Entry<String, User> user : map.entrySet()) {
//            String[] lastName = user.getValue().getName().split(", ");
//            orderedList.add(lastName[0]);
//        }
//        Collections.sort(orderedList);

        System.out.println("WHAT THE HECk");

        List<User> list = new ArrayList<>();

        // Name Comparator
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        if (id != null) {
            System.out.println("MAN WHAT");
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        if (id.equals(user.getValue().getId())) {
                            list.add(user.getValue());
                        }
                    }
                }
            });

            return list;
        }
        //First case was for id search only. this next set is if a building is chosen
        else if (building != null) {
            System.out.println("HERE IS NAME2");
            // Major and times filled
            if (major != null && startTime != -1 && endTime != -1) {
                for (User user : building.getCurrentStudents()) {
                    String s = user.getHistory().get(building.getAbbreviation());
                    String[] ts = s.split(" ");

                    if (user.getMajor().equals(major) && Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[1]) <= endTime) {

                        // Check name
                        if (searchName(user, fName, lName, list) != null) {
                            list.add(searchName(user, fName, lName, list));
                            continue;
                        }

                        // Check date

                        list.add(user);
                    }
                }
                return list;
            }

            // Major only
            else if (major != null && startTime == -1 && endTime == -1) {
                for (User user : building.getCurrentStudents()) {
                    if (user.getMajor().equals(major)){

                        // Check name
                        if (searchName(user, fName, lName, list) != null) {
                            list.add(searchName(user, fName, lName, list));
                            continue;
                        }

                        list.add(user);
                    }
                }
                return list;
            }
            else if (major == null && startTime != -1 && endTime != -1) {
                for (User user : building.getCurrentStudents()) {
                    String s = user.getHistory().get(building.getAbbreviation());
                    String[] ts = s.split(" ");
                    if (Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[1]) <= endTime) {

                        // Check name
                        if (searchName(user, fName, lName, list) != null) {
                            list.add(searchName(user, fName, lName, list));
                            continue;
                        }

                        list.add(user);
                    }
                }
            }
            //everything but building is null
            else {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> e : map.entrySet()) {
                            User user = e.getValue();
                            System.out.println("E NAME: " + user.getName());
                            if (user.isInBuilding()) {
                                System.out.println("EEE: " + user.getName());
                                if (user.getCurrentBuilding().getAbbreviation().equals(building.getAbbreviation())) {
                                    System.out.println("COCK!");
                                    list.add(new User(user.getName(), user.getEmail(), user.getPassword(), user.getPhoto(), user.getId(), user.isInBuilding(), user.getCurrentBuilding(), user.getHistory(), user.getMajor(), user.isManager(), user.isDeleted()));
                                    System.out.println("USER NAME: " + user.getName());
                                }
                            }

                        }
                    }
                });
            }
        } //major is the dominating condition
        else if (major != null) {
            System.out.println("HERE IS NAME1");
            if (startTime != -1 && endTime != -1) {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            String s = user.getValue().getHistory().get(building.getAbbreviation());
                            String[] ts = s.split(" ");
                            int timeEnder;
                            if (s.length() <= 5){ timeEnder = 2359; }
                            else{ timeEnder = Integer.parseInt(ts[1]);}
                            if (user.getValue().getMajor().equals(major) && ((Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[0]) <= endTime) || (timeEnder >= startTime && timeEnder <= endTime))) {

                                // Check name
                                if (searchName(user.getValue(), fName, lName, list) != null) {
                                    list.add(searchName(user.getValue(), fName, lName, list));
                                    continue;
                                }
                                list.add(user.getValue());
                            }
                        }
                    }
                });
                return list;
            } else {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            if (user.getValue().getMajor().equals(major)) {
                                System.out.println("YOOOO");
                                // Check name
                                if (searchName(user.getValue(), fName, lName, list) != null) {
                                    System.out.println("ANYTHING?" + user.getValue().getName());
                                    list.add(searchName(user.getValue(), fName, lName, list));
                                    continue;
                                }
                            }
                        }
                    }
                });
                return list;
            }
        }

        // Only time is a condition
        else if (startTime != -1 && endTime != -1){
            System.out.println("TIME ONLY CASE");
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        for(String s : user.getValue().getHistory().values()){
                            System.out.println("WE ARE IN THE TIME ONLY CASE:" + s);
                            String[] ts = s.split(" ");
                            int timeEnder;
                            if (s.length() <= 5){ timeEnder = 2359; }
                            else{ timeEnder = Integer.parseInt(ts[1]);}
                            if ((Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[0]) <= endTime) || (timeEnder >= startTime && timeEnder <= endTime)) {

                                // Check name if exists
                                if (lName != null && fName != null || lName != null && fName == null || lName == null && fName != null) {
                                    if (searchName(user.getValue(), fName, lName, list) != null) {
                                        System.out.println("NAME EXISTS");
                                        list.add(searchName(user.getValue(), fName, lName, list));
                                        continue;
                                    }
                                }

                                list.add(user.getValue());
                            }
                        }
                    }
                }
            });
            return list;
        }

        // Only name is a condition
        else if (lName != null && fName != null || lName != null && fName == null || lName == null && fName != null){
            System.out.println("HERE IS NAMEEEE");
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        System.out.println("USER HUH " + user.getValue().getName());
                        User use = searchName(user.getValue(), fName, lName, list);
                        if(use != null)
                        {
                            list.add(use);
                        }
                    }
                }
            });
            return list;
        }

        System.out.println("PLSSS" + lName + fName);
        // There is something wrong (both times aren't filled, id filleed with other shit. etc)
        return list;
    }
}