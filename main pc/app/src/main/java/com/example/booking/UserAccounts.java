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

import com.example.booking.EventBooking.RoomDB;
import com.example.booking.EventBooking.UserAccounts_tbl;
import com.example.booking.databinding.ActivityUserAccountsBinding;

public class UserAccounts extends MainApp {

    public ActivityUserAccountsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountsBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);


        db = RoomDB.getInstance(this);
        daoEvents = db.daoEvents();
        if (daoEvents == null) daoEvents = RoomDB.getInstance(this).daoEvents();

        binding.btnRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = binding.edUsername.getText().toString();
                String pass = binding.edPassword.getText().toString();
                String confirm = binding.confirmPassword.getText().toString();
                if(!pass.equals(confirm)) {
                    ShowConfirm("INVALID CREDENTIALS", "The password is not the same",
                            "Confirm", new Runnable() {
                                @Override
                                public void run() {
                                    HideConfirmation();
                                }
                            });
                    return;
                }
                try {
                    AsyncTask.execute(() -> {
                        daoEvents.insertAccounts(new UserAccounts_tbl(user, pass, "customer"));
                    });

                    ShowConfirm("REGISTER SUCCESS", "You've successfully created an account", "Confirm",
                            new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(UserAccounts.this, MainActivity.class));
                                }
                            });
                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                }
            }
        });
        binding.registerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAccounts.this, MainActivity.class));
            }
        });
    }
}