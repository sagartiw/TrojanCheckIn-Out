package com.team13.trojancheckin_out.Database;

import com.team13.trojancheckin_out.UPC.Building;

import java.util.Map;

public interface MyBuildingCallback {
    void onCallback(Map<String, Building> map);
}
