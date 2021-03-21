package com.team13.trojancheckin_out.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.team13.trojancheckin_out.Database.AccountManipulator.rootNode;

/**
 * This class communicates with the Buildings database to determine the configuration of the
 * buildings located on UPC. This class can read from CSV files, check QRCode validity, and
 * return a list of available buildings and their accompanying QRCodes.
 */
public class BuildingManipulator {

    public static final DatabaseReference referenceBuildings = rootNode.getReference("Buildings");

    private Map<String, Building> currentBuildings;
    private List<String> currentQRCodes;
    private File file;

    /**
     * @return a list of the currently established buildings.
     */
    public Map<String, Building> getCurrentBuildings() {
        referenceBuildings.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Building building = ds.getValue(Building.class);
                            currentBuildings.put(building.getAbbreviation(), building);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        return this.currentBuildings;
    }

    /**
     * @return a list of the currently established QRCodes.
     */
    public List<String> getCurrentQRCodes() { return this.currentQRCodes; }

    /**
     * @param file
     * @return true if the CSV file has been successfully processed.
     */
    public Boolean processCSV(File file) {
        try {
            this.file = file;
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                // CSV files will put unwanted double quotes around each line of data
                line = line.replaceAll("\"","");

                // <Abbreviation>|<Full Name>|<Capacity>
                String[] data = line.split("@");

                Building building = new Building(data[0], data[1], Integer.parseInt(data[2]),
                        new ArrayList<>(), "QR");

                // Store in DB
                referenceBuildings.child(data[0]).setValue(building);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param QRCode
     * @return true if the given QRCode has been successfully matched with an existing one.
     */
    public Boolean QRMatch(String QRCode) { return true; }
}
