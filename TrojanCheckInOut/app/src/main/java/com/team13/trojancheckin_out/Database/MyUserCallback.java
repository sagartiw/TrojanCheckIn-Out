package com.team13.trojancheckin_out.Database;

import com.team13.trojancheckin_out.Accounts.User;

import java.util.Map;

public interface MyUserCallback {
    void onCallback(Map<String, User> map);
}
