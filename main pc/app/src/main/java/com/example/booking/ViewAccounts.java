package com.example.booking;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.Adapters.AdapterViewAccounts;
import com.example.booking.EventBooking.UserAccounts_tbl;

import java.util.ArrayList;
import java.util.List;

import com.example.booking.EventBooking.DaoEvents;
import com.example.booking.EventBooking.RoomDB;

public class ViewAccounts extends AppCompatActivity {
    List<UserAccounts_tbl> listAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_accounts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        updateRv();


    }

    public void updateRv(){
        DaoEvents dao = RoomDB.getInstance(this).daoEvents();

        listAccounts = new ArrayList<>();

//        listAccounts.add(new UserAccounts_tbl("username1", "password1", "Admin"));

        AsyncTask.execute(()->{
            listAccounts = dao.viewUserAccount();

            runOnUiThread(() -> {
                AdapterViewAccounts adapterViewAccounts = new AdapterViewAccounts(listAccounts, ViewAccounts.this);
                RecyclerView rvAccount = findViewById(R.id.rvViewAccounts);

                rvAccount.setAdapter(adapterViewAccounts);
                rvAccount.setLayoutManager(new LinearLayoutManager(this));
            });
        });
    }
}