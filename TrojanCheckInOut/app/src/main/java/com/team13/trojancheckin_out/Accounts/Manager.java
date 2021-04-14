package com.team13.trojancheckin_out.Accounts;

import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.BuildingManipulator;
<<<<<<< HEAD
=======
import com.team13.trojancheckin_out.Database.MyUserCallback;
>>>>>>> 78892f1c1a32bcc0e8799e3567a8faf95634d420
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
     * @param building
     * @param id
     * @param major
     * @return the searched student.
     */
    public List<User> searchStudents(String time, Building building, String id, String major) {
        // TODO: add constraints for time

        List<User> list = new ArrayList<>();
//        if (id != null) {
//            list.add(accountManipulator.getStudentAccounts().get(id));
//            return list;
//        } else if (building != null){
//            if (major != null) {
//                for (User user : building.getCurrentStudents()) {
//                    if (user.getMajor().equals(major)){
//                        list.add(user);
//                    }
//                }
//                return list;
//            } else {
//                return building.getCurrentStudents();
//            }
//        } else if (major != null) {
//            for (User user : accountManipulator.getStudentAccounts().values()) {
//                if (user.getMajor().equals(major)) {
//                    list.add(user);
//                }
//            }
//            return list;
//        }

        if (id != null) {
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
        } //First case was for id search only. this next set is if a building is chosen
        else if (building != null) {
            //major and times filled
            if (major != null && startTime != -1 && endTime != -1) {
                for (User user : building.getCurrentStudents()) {
                    String s = user.getHistory().get(building.getAbbreviation());
                    String[] ts = s.split(" ");
                    if (user.getMajor().equals(major) && Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[1]) <= endTime) {
                        list.add(user);
                    }
                }
                return list;
            }
            //major only
            else if (major != null && startTime == -1 && endTime == -1) {
                for (User user : building.getCurrentStudents()) {
                    if (user.getMajor().equals(major)){
                        list.add(user);
                    }
                }
                return list;
            }
            //everything but building is null
            else {
                return building.getCurrentStudents();
            }
        } //major is the dominating condition
        else if (major != null) {
            if (startTime != -1 && endTime != -1) {
                accountManipulator.getAllAccounts(new MyUserCallback() {
                    @Override
                    public void onCallback(Map<String, User> map) {
                        for (Map.Entry<String, User> user : map.entrySet()) {
                            String s = user.getValue().getHistory().get(building.getAbbreviation());
                            System.out.println(s);
                            String[] ts = s.split(" ");
                            int timeEnder;
                            if (s.length() <= 5){ timeEnder = 2359; }
                            else{ timeEnder = Integer.parseInt(ts[1]);}
                            if (user.getValue().getMajor().equals(major) && ((Integer.parseInt(ts[0]) >= startTime && Integer.parseInt(ts[0]) <= endTime) || (timeEnder >= startTime && timeEnder <= endTime))) {
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
                                list.add(user.getValue());
                            }
                        }
                    }
                });
                return list;
            }
        } //only time is a condition
        else if (startTime != -1 && endTime != -1){
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
                                list.add(user.getValue());
                            }
                        }
//                        String b = user.getValue().getCurrentBuilding().getAbbreviation();
//                        String s = user.getValue().getHistory().get(b);
//                        user.getValue().get
                    }
                }
            });
            return list;
        } // there is something wrong (both times aren't filled, id filleed with other shit. etc)
        return list;
    }
}



 //       else if(startTime != -1 && endTime != -1){
//                accountManipulator.getAllAccounts(new MyUserCallback() {
//                    @Override
//                    public void onCallback(Map<String, User> map) {
//
//                        for(Map.Entry<String, User> user : map.entrySet()){
//                            String s = user.getValue().getHistory();
//                        }
////                        map.get(user.getId()).getHistory();
////                        for (Map.Entry<String, String> e : map.get(user.getId()).getHistory().entrySet()) {
////                            String[] comp = e.getValue().split(" ");
////                            String [] components = new String[2];
////                            components[0] = comp[0];
////                            if(comp.length < 2)
////                            {
////                                components[1] = " ";
////                            }
////                            else
////                            {
////                                components[1] = comp[1];
////                            }
////                            History history = new History(e.getKey(), "In: " + components[0], "Out: " + components[1]);
////                            System.out.println("HISTORY: " + e.getKey() + components[0] + components[1]);
////                            historyList.add(history);
////                        }
//                    }
//                });
//            }