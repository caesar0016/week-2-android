package com.example.booking.EventBooking;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class UserAccounts_tbl {
    @PrimaryKey(autoGenerate = true)
    private int user_id;
    private String username;
    private String password;
    private String role = "Customer";

    public UserAccounts_tbl(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Ignore
    public UserAccounts_tbl(int user_id, String username, String password, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
