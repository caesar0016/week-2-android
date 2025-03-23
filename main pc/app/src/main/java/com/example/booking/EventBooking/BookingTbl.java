package com.example.booking.EventBooking;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = UserAccounts_tbl.class,
                parentColumns = "user_id",
                childColumns = "user_id",
                onDelete = ForeignKey.SET_NULL),

                @ForeignKey(entity = Services_tbl.class,
                parentColumns = "service_id",
                childColumns = "service_id",
                onDelete = ForeignKey.SET_NULL)
        }
)
public class BookingTbl {
    //--------
    @PrimaryKey(autoGenerate = true)
    private int booking_id;
    private int user_id;
    private int service_id;

    private String onCreate;
    private String status = "Pending"; //-- Pending, Confirm, Cancelled, Completed

    public BookingTbl(int user_id, int service_id, String onCreate, String status) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.onCreate = onCreate;
        this.status = status;
    }

    @Ignore
    public BookingTbl(int booking_id, int user_id, int service_id, String onCreate, String status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.onCreate = onCreate;
        this.status = status;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(String onCreate) {
        this.onCreate = onCreate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
