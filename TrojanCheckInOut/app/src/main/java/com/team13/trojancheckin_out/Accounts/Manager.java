package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    public List<User> searchStudents(String fName, String lName, int startTime, int endTime, Building building, String id, String major, String startDate, String endDate) {

        // IF WE ARE NOT SEARCHING BY TIME, ENTER "-1" into the startTime parameter.
        List<User> list = new ArrayList<>();

        //Case 1: ONLY inputting id. If other things are filled, ID supercedes everything
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

            System.out.println("Return list 1");
            return list;
        }

        //CASE 2: null id with building chosen.
        else if (building != null) {

            //Case 2A: Major and Times filled. Need to add if Dates are filled too.
            if (major != null && startTime != -1 && endTime != 3000) {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                      for (Map.Entry<String, User> e : map.entrySet()) {
                          User user = e.getValue();
                          if (user.isInBuilding()) {
                              if (user.getCurrentBuilding().getAbbreviation().equals(building.getAbbreviation())) {
                                  String s = user.getHistory().get(building.getAbbreviation());
                                  String[] allHistory = s.split(", ");
                                  String[] startArr = allHistory[0].split("@");
                                  String[] endArr = startArr;

                                  // If there is an end date, update endArr to be the end date
                                  if (allHistory.length > 1) {
                                      endArr = allHistory[1].split("@");
                                  }

                                  try {
                                      Date startUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startArr[1]);
                                      Date startInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startDate);
                                      Date endUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endArr[1]);
                                      Date endInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endDate);

                                      if (startUser.compareTo(startInput) >= 0 && endUser.compareTo(endInput) <= 0) {
                                          if (user.getMajor().equals(major) && Integer.parseInt(startArr[0]) >= startTime && Integer.parseInt(endArr[0]) <= endTime) {

                                              // Check name
                                              if (searchName(user, fName, lName, list) != null) {
                                                  list.add(searchName(user, fName, lName, list));
                                                  continue;
                                              }
                                              list.add(user);
                                          }
                                      }

                                  } catch (ParseException ex) {
                                      ex.printStackTrace();
                                  }
                              }
                          }
                      }
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 2A");
                    }
                });
//                if (!list.isEmpty()) {
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
                return list;
            }
            //Case 2B: Major is filled. Time is null. Need to add if Dates are null. THIS SEEMS WRONG TO ME
            else if (major != null && startTime == -1 && endTime == -1) {
//                for (User user : building.getCurrentStudents()) {
//                    if (user.getMajor().equals(major)){
//
//                        // Check name
//                        if (searchName(user, fName, lName, list) != null) {
//                            list.add(searchName(user, fName, lName, list));
//                            continue;
//                        }
//
//                        list.add(user);
//                    }
//                }
//                if (!list.isEmpty()) {
//                    System.out.println("Name formatting: " + list.get(0).getName());
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
//                System.out.println("Return list 3");
//                return list;
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> e : map.entrySet()) {
                            User user = e.getValue();
                            if (user.isInBuilding()) {
                                if (user.getCurrentBuilding().getAbbreviation().equals(building.getAbbreviation())) {
                                    String s = user.getHistory().get(building.getAbbreviation());
                                    String[] allHistory = s.split(", ");
                                    String[] startArr = allHistory[0].split("@");
                                    String[] endArr = startArr;

                                    // If there is an end date, update endArr to be the end date
                                    if (allHistory.length > 1) {
                                        endArr = allHistory[1].split("@");
                                    }

                                    try {
                                        Date startUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startArr[1]);
                                        Date startInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startDate);
                                        Date endUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endArr[1]);
                                        Date endInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endDate);

                                        if (startUser.compareTo(startInput) >= 0 && endUser.compareTo(endInput) <= 0) {
                                            if (user.getMajor().equals(major)) {
                                                // Check name
                                                if (searchName(user, fName, lName, list) != null) {
                                                    list.add(searchName(user, fName, lName, list));
                                                    continue;
                                                }
                                                list.add(user);
                                            }
                                        }

                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 2B");
                    }
                });
            }
            //Case 2C: Major is null. Time is filled. Need to add if Dates are null/filled.
            else if (major == null && startTime != -1 && endTime != 3000) {
                //COMMENTING THIS
//                for (User user : building.getCurrentStudents()) {
//                    String s = user.getHistory().get(building.getAbbreviation());
//                    String[] ts = s.split(" ");
//                    if (Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[1]) <= endTime) {
//
//                        // Check name
//                        if (searchName(user, fName, lName, list) != null) {
//                            list.add(searchName(user, fName, lName, list));
//                            continue;
//                        }
//
//                        list.add(user);
//                    }
//                }
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> e : map.entrySet()) {
                            User user = e.getValue();
                            if (user.isInBuilding()) {
                                if (user.getCurrentBuilding().getAbbreviation().equals(building.getAbbreviation())) {
                                    String s = user.getHistory().get(building.getAbbreviation());
                                    String[] allHistory = s.split(", ");
                                    String[] startArr = allHistory[0].split("@");
                                    String[] endArr = startArr;

                                    // If there is an end date, update endArr to be the end date
                                    if (allHistory.length > 1) {
                                        endArr = allHistory[1].split("@");
                                    }

                                    try {
                                        Date startUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startArr[1]);
                                        Date startInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startDate);
                                        Date endUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endArr[1]);
                                        Date endInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endDate);

                                        if (startUser.compareTo(startInput) >= 0 && endUser.compareTo(endInput) <= 0) {
                                            if (Integer.parseInt(startArr[0]) >= startTime && Integer.parseInt(endArr[0]) <= endTime) {

                                                // Check name
                                                if (searchName(user, fName, lName, list) != null) {
                                                    list.add(searchName(user, fName, lName, list));
                                                    continue;
                                                }
                                                list.add(user);
                                            }
                                        }

                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 2C");
                    }
                });
//                if (!list.isEmpty()) {
//                    System.out.println("Name formatting: " + list.get(0).getName());
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
//                System.out.println("Return list 3");
                return list;
            }
            //Case 2D: Everything but building is null.
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
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 3");
                    }
                });
//                if (!list.isEmpty()) {
//                    System.out.println("Name formatting: " + list.get(0).getName());
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
//                System.out.println("Return list 3");
                return list;
            }
        }
        //Case 3: Building and ID are null. Major is filled/dominating condition.
        else if (major != null) {
            System.out.println("HERE IS NAME1");
            //Case 3A: Time is also filled. Need to check if dates are null/filled.
            if (startTime != -1 && endTime != 3000) {
//                accountManipulator.getAllAccounts(new MyUserCallback() {
//                    @Override
//                    public void onCallback(Map<String, User> map) {
//                        for (Map.Entry<String, User> user : map.entrySet()) {
//                            String s = user.getValue().getHistory().get(building.getAbbreviation());
//                            String[] ts = s.split(" ");
//                            int timeEnder;
//                            if (s.length() <= 5){ timeEnder = 2359; }
//                            else{ timeEnder = Integer.parseInt(ts[1]);}
//                            if (user.getValue().getMajor().equals(major) && ((Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[0]) <= endTime) || (timeEnder >= startTime && timeEnder <= endTime))) {
//
//                                // Check name
//                                if (searchName(user.getValue(), fName, lName, list) != null) {
//                                    list.add(searchName(user.getValue(), fName, lName, list));
//                                    continue;
//                                }
//                                list.add(user.getValue());
//                            }
//                        }
//                    }
//                });
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            boolean poop = false;
                            for(String s : user.getValue().getHistory().values()){
                                System.out.println("WE ARE IN THE MAJOR/DATE/TIME CASE:" + s);
                                String[] allHistory = s.split(", ");
                                String[] startArr = allHistory[0].split("@");
                                String[] endArr = startArr;

                                // If there is an end date, update endArr to be the end date
                                if (allHistory.length > 1) {
                                    endArr = allHistory[1].split("@");
                                }
                                System.out.println("USER: " + user.getValue().getName() + " " + endArr[1]);

                                try {
                                    Date startUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startArr[1]);
                                    Date startInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startDate);
                                    Date endUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endArr[1]);
                                    Date endInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endDate);

                                    if (startUser.compareTo(startInput) >= 0 && endUser.compareTo(endInput) <= 0) {
                                        if (user.getValue().getMajor().equals(major) && Integer.parseInt(startArr[0]) >= startTime && Integer.parseInt(endArr[0]) <= endTime) {

                                            // Check name
                                            if (searchName(user.getValue(), fName, lName, list) != null) {
                                                poop = true;
                                                continue;
                                            }
                                            poop = true;
                                        }
                                    }

                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if(poop)
                            {
                                list.add(user.getValue());
                            }
                        }
                        System.out.println("LIST: " + list);
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 4");
                    }
                });
//                if (!list.isEmpty()) {
//                    System.out.println("Name formatting: " + list.get(0).getName());
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
//                System.out.println("Return list 4");
                return list;
            }
            //Case 3B: Everything is null except for major.
            else {
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
                                System.out.println("SHOULD BE HERE");
                                list.add(user.getValue());
                            }
                        }
                        System.out.println("LIST: " + list);
                        if (!list.isEmpty()) {
                            System.out.println("Name formatting: " + list.get(0).getName());
                            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                        }
                        System.out.println("Return list 5");
                    }
                });
//                if (!list.isEmpty()) {
//                    System.out.println("Name formatting: " + list.get(0).getName());
//                    Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//                }
//                System.out.println("Return list 5");
                return list;
            }
        }
        // Case 4: ID, Building, and Major are null. Time is filled. Need to check if dates are null.
        else if (startTime != -1 && endTime != 3000){
            System.out.println("TIME ONLY CASE");
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        boolean poop = false;
                        for(String s : user.getValue().getHistory().values()){
                            System.out.println("WE ARE IN THE TIME ONLY CASE:" + s);
                            String[] ts = s.split(" ");
                            String[] starter = ts[0].split("@");
                            int timeEnder = Integer.parseInt(starter[0]);
                            if (s.length() > 17) {
                                String[] endFull = ts[1].split("@");
                                timeEnder = Integer.parseInt(endFull[0]);
                            }

                            System.out.println("START: " + startTime);
                            if ((Integer.parseInt(starter[0]) >= startTime && timeEnder <= endTime)) {

                                // Check name if exists
                                if (lName != null && fName != null || lName != null && fName == null || lName == null && fName != null) {
                                    if (searchName(user.getValue(), fName, lName, list) != null) {
                                        System.out.println("NAME EXISTS");
                                        poop = true;
                                    }
                                    continue;
                                }
                                System.out.println("STILL HERE?");
                                poop = true;
                            }
                        }
                        if(poop) {
                            list.add(user.getValue());
                        }
                    }
                    System.out.println("LIST: " + list.toString());
                    if (!list.isEmpty()) {
                        System.out.println("Name formatting: " + list.get(0).getName());
                        Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                    }
                    System.out.println("Return list 6");
                }
            });
//            if (!list.isEmpty()) {
//                System.out.println("Name formatting: " + list.get(0).getName());
//                Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
//            }
//            System.out.println("Return list 6");
            return list;
        }
        //Case 5: Name is filled. I think that this should supercede everything but ID, so may we should move this up.
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
                    System.out.println("LIST: " + list);
                    if (!list.isEmpty()) {
                        System.out.println("Name formatting: " + list.get(0).getName());
                        Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                    }
                    System.out.println("Return list 7");
                }
            });
            return list;
        }
        // FINAL CASE: Only date, if at least one field is not default
        else if(startDate != "01.01.1970" || endDate != "31.12.2099")
        {
            accountManipulator.getAllAccounts(new MyUserCallback() {
                @Override
                public void onCallback(Map<String, User> map) {
                    for (Map.Entry<String, User> user : map.entrySet()) {
                        boolean poop = false;
                        for(String s : user.getValue().getHistory().values()){
                            System.out.println("WE ARE IN THE DATE ONLY CASE:" + s);
                            String[] allHistory = s.split(", ");
                            String[] startArr = allHistory[0].split("@");
                            String[] endArr = startArr;

                            // If there is an end date, update endArr to be the end date
                            if (allHistory.length > 1) {
                                endArr = allHistory[1].split("@");
                            }
                            System.out.println("USER: " + user.getValue().getName() + " " + endArr[1]);

                            try {
                                Date startUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startArr[1]);
                                Date startInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(startDate);
                                Date endUser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endArr[1]);
                                Date endInput = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(endDate);

                                System.out.println(startUser.toString() + startInput.toString() + endUser.toString() + endInput.toString());

                                if (startUser.compareTo(startInput) >= 0 && endUser.compareTo(endInput) <= 0) {
                                    poop = true;
                                }

                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(poop)
                        {
                            list.add(user.getValue());
                        }
                    }
                    System.out.println("LIST: " + list);
                    if (!list.isEmpty()) {
                        System.out.println("Name formatting: " + list.get(0).getName());
                        Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
                    }
                    System.out.println("Return date only list");
                }
            });
            return list;
        }

        System.out.println("PLSSS" + lName + fName);
        // There is something wrong (both times aren't filled, id filleed with other shit. etc)
        if (!list.isEmpty()) {
            System.out.println("Name formatting: " + list.get(0).getName());
            Collections.sort(list, (User u1, User u2) -> u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase()));
        }
        System.out.println("Return list 8");
        return list;
    }
}