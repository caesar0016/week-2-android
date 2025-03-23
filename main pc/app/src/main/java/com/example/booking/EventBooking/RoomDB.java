package com.example.booking.EventBooking;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Services_tbl.class, Payment_tbl.class, UserAccounts_tbl.class, BookingTbl.class}, version = 5)
public abstract class RoomDB extends RoomDatabase {
    public abstract DaoEvents daoEvents();
    public static RoomDB INSTANCE;

    public static RoomDB getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "database_name")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}
