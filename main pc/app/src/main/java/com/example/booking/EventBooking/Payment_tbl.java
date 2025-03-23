package com.example.booking.EventBooking;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = BookingTbl.class,
        parentColumns = "booking_id",
        childColumns = "booking_id",
        onDelete = ForeignKey.SET_NULL),
})
public class Payment_tbl {
    @PrimaryKey(autoGenerate = true)
    private int payment_id;
    private int booking_id;
    private String description;
    private String status; //-- refund for cancelled

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment_tbl(int booking_id, String description, String status) {
        this.booking_id = booking_id;
        this.description = description;
        this.status = status;
    }

    @Ignore
    public Payment_tbl(int payment_id, int booking_id, String description, String status) {
        this.payment_id = payment_id;
        this.booking_id = booking_id;
        this.description = description;
        this.status = status;
    }
}
