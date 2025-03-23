package com.example.booking.EventBooking;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Services_tbl {
    @PrimaryKey(autoGenerate = true)
    private int service_id;
    private String serviceName;
    private String description;
    private double price = 0;
    private boolean is_archived = false;

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIs_archived() {
        return is_archived;
    }

    public void setIs_archived(boolean is_archived) {
        this.is_archived = is_archived;
    }

    public Services_tbl(String serviceName, String description, double price, boolean is_archived) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.is_archived = is_archived;
    }

    @Ignore
    public Services_tbl(int service_id, String serviceName, String description, double price, boolean is_archived) {
        this.service_id = service_id;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.is_archived = is_archived;
    }
}
