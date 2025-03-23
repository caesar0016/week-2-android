package com.example.booking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.booking.Adapters.BookAdapter;
import com.example.booking.EventBooking.BookingTbl;
import com.example.booking.EventBooking.RoomDB;
import com.example.booking.databinding.ActivityBookingBinding;

import java.util.Arrays;
import java.util.List;

public class Booking extends MainApp {

    ActivityBookingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking.this, MainMenu.class));
            }
        });

        UpdateRV("Pending");

        binding.acceptedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRV("Accepted");
            }
        });

        binding.pendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRV("Pending");
            }
        });

        binding.declinedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRV("Declined");
            }
        });
    }

    public void UpdateRV(String filter) {
        db = RoomDB.getInstance(Booking.this);
        daoEvents = db.daoEvents();
        if (daoEvents == null) daoEvents = RoomDB.getInstance(Booking.this).daoEvents();
        AsyncTask.execute(() -> {
            List<BookingTbl> list = daoEvents.viewBookingFilter(filter);

            runOnUiThread(() -> {
                BookAdapter adapter = new BookAdapter(list, this);
                binding.bookingRv.setAdapter(adapter);
                binding.bookingRv.setLayoutManager(new LinearLayoutManager(this));
            });
        });

    }

    public void Accept(BookingTbl tbl) {
        db = RoomDB.getInstance(Booking.this);
        daoEvents = db.daoEvents();
        if (daoEvents == null) daoEvents = RoomDB.getInstance(this).daoEvents();

        AsyncTask.execute(() -> {
            daoEvents.updateBooking(tbl.getBooking_id(), "Accepted");

            runOnUiThread(() -> {
                UpdateRV("Pending");
            });
        });
    }

    public void Decline(BookingTbl tbl) {
        db = RoomDB.getInstance(Booking.this);
        daoEvents = db.daoEvents();
        if (daoEvents == null) daoEvents = RoomDB.getInstance(this).daoEvents();

        AsyncTask.execute(() -> {
            daoEvents.updateBooking(tbl.getBooking_id(), "Declined");

            runOnUiThread(() -> {
                UpdateRV("Pending");
            });
        });
    }
}