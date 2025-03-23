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

import com.example.booking.EventBooking.DaoEvents;
import com.example.booking.EventBooking.RoomDB;
import com.example.booking.EventBooking.UserAccounts_tbl;
import com.example.booking.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends MainApp {

    public ActivityMainBinding binding;
    public View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        DaoEvents dao = RoomDB.getInstance(this).daoEvents();

//        AsyncTask.execute(()->{
//            dao.insertAccounts(new UserAccounts_tbl("Username1", "password1", "Admin"));
//        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.loginUsernameTxt.getText().toString();
                String password = binding.loginPasswordTxt.getText().toString();

                if(username.equals("admin") && password.equals("admin")) {
                    role = "admin";
                    startActivity(new Intent(MainActivity.this, MainMenu.class)
                            .putExtra("role", "admin"));
                }
                db = RoomDB.getInstance(MainActivity.this);
                daoEvents = db.daoEvents();

                AsyncTask.execute(() -> {
                    List<UserAccounts_tbl> accounts = daoEvents.viewUserAccount();
                    for (UserAccounts_tbl tbl : accounts) {
                        if(username.equals(tbl.getUsername()) && password.equals(tbl.getPassword())) {
                            role = "customer";
                            user = tbl;
                            Log.d("qwe",(user == null) + "");
                            startActivity(new Intent(MainActivity.this, MainMenu.class)
                                    .putExtra("role", tbl.getRole()));
                        }
                    }
                });

            }
        });

        binding.regsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserAccounts.class));
            }
        });
    }
}