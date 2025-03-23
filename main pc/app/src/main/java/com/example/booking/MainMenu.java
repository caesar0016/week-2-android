package com.example.booking;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.booking.EventBooking.RoomDB;
import com.example.booking.databinding.ActivityMainMenuBinding;

public class MainMenu extends MainApp {

    public ActivityMainMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        binding.mainmenuServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Services.class)
                        .putExtra("role", role));
            }
        });

        binding.mainmenuQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Booking.class));
            }
        });

        binding.mainmenuAccountsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ViewAccounts.class));
            }
        });
        if(role.equals("customer")) {
            binding.mainmenuAccountsBtn.setVisibility(GONE);
            binding.mainmenuBookingBtn.setVisibility(GONE);
            binding.mainmenuQueueBtn.setVisibility(GONE);
        } else {
            binding.mainmenuBookingBtn.setVisibility(GONE);
        }
    }
}