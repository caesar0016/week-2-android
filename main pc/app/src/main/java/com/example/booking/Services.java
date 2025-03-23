package com.example.booking;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.booking.Adapters.ServiceAdapter;
import com.example.booking.EventBooking.BookingTbl;
import com.example.booking.EventBooking.RoomDB;
import com.example.booking.EventBooking.Services_tbl;
import com.example.booking.EventBooking.UserAccounts_tbl;
import com.example.booking.databinding.ActivityServicesBinding;
import com.example.booking.databinding.ModalBookServiceBinding;
import com.example.booking.databinding.ModalCreateServiceBinding;
import com.example.booking.databinding.ModalUpdateServiceBinding;

import java.util.List;

public class Services extends MainApp {

    ConstraintLayout createServiceLayout, bookServiceLayout;
    ModalCreateServiceBinding createServiceBinding;
    public ActivityServicesBinding binding;
    List<Services_tbl> list;
    ServiceAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityServicesBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, MainMenu.class));
            }
        });

        createServiceLayout = findViewById(R.id.create_service_layout);
        createServiceBinding = ModalCreateServiceBinding.bind(view);
        
        binding.servicesCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createServiceLayout.setVisibility(VISIBLE);
                createServiceBinding.createServiceNameTxt.setText("");
                createServiceBinding.createServiceDescTxt.setText("");
                createServiceBinding.createServicePriceTxt.setText("0");
            }
        });

        createServiceBinding.createServiceCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createServiceLayout.setVisibility(GONE);
            }
        });

        createServiceBinding.createServiceCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = createServiceBinding.createServiceNameTxt.getText().toString();
                String serviceDesc = createServiceBinding.createServiceDescTxt.getText().toString();
                double servicePrice = StringToDouble(createServiceBinding.createServicePriceTxt.getText().toString());

                AsyncTask.execute(() -> {
                    try {
                        if(daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();
                        daoEvents.insertService(new Services_tbl(serviceName, serviceDesc, servicePrice, false));
                        UpdateRV();
                        createServiceLayout.setVisibility(GONE);
                    } catch (Exception ex) {
                        Log.e("error", ex.getMessage());
                    }

                });
            }
        });


        UpdateRV();
    }

    public void UpdateRV() {
        AsyncTask.execute(() -> {
            if(daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();
            if(role.equals("customer")) {
                list = daoEvents.viewServices().stream().filter(x -> !x.isIs_archived()).toList();
                binding.servicesCreateBtn.setVisibility(GONE);
            } else {
                list = daoEvents.viewServices();
            }
            serviceAdapter = new ServiceAdapter(list, this);
            runOnUiThread(() -> {
                binding.servicesRv.setAdapter(serviceAdapter);
                binding.servicesRv.setLayoutManager(new LinearLayoutManager(this));
            });
        });
    }

    public void ShowUpdate(Services_tbl tbl) {
        ConstraintLayout updateLayout = findViewById(R.id.update_service_layout);
        updateLayout.setVisibility(VISIBLE);

        ModalUpdateServiceBinding updateBinding = ModalUpdateServiceBinding.bind(view);

        updateBinding.updateServiceNameTxt.setText(tbl.getServiceName());
        updateBinding.updateServiceDescTxt.setText(tbl.getDescription());
        updateBinding.updateServicePriceTxt.setText(tbl.getPrice() + "");

        updateBinding.updateServiceCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLayout.setVisibility(GONE);
            }
        });

        updateBinding.updateServiceUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();
                AsyncTask.execute(() -> {
                    try {
                        String serviceName = updateBinding.updateServiceNameTxt.getText().toString();
                        String serviceDesc = updateBinding.updateServiceDescTxt.getText().toString();
                        double servicePrice = StringToDouble(updateBinding.updateServicePriceTxt.getText().toString());

                        daoEvents.updateService(serviceName, serviceDesc, servicePrice, tbl.getService_id());
                        updateLayout.setVisibility(GONE);
                        UpdateRV();
                    } catch (Exception ex) {
                        Log.e("error", ex.getMessage());
                    }
                });
            }
        });
    }

    public void DeleteService(int id) {
        if(daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();
        AsyncTask.execute(() -> {
            try {
                daoEvents.deleteService(id);
                UpdateRV();
            } catch (Exception ex) {
                Log.e("error", ex.getMessage());
            }
        });
    }

    public void ArchiveRestoreService(Services_tbl tbl) {
        ShowConfirmation(tbl.isIs_archived() ? "RESTORE" : "ARCHIVE", "Are you sure you want to continue?",
                "Confirm", new Runnable() {
                    @Override
                    public void run() {
                        if (daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();
                        AsyncTask.execute(() -> {
                            try {
                                daoEvents.archivedRestoreService(tbl.isIs_archived() ? 0 : 1, tbl.getService_id());
                                ShowConfirm("SUCCESS", "You've successfully " + (tbl.isIs_archived() ? "RESTORE " : "ARCHIVED ") + tbl.getServiceName(),
                                        "Confirm", new Runnable() {
                                            @Override
                                            public void run() {
                                                HideConfirmation();
                                                UpdateRV();
                                            }
                                        });
                            } catch (Exception ex) {
                                Log.e("error", ex.getMessage());
                            }
                        });
                    }
                }, "Cancel", new Runnable() {
                    @Override
                    public void run() {
                        HideConfirmation();
                    }
                });

    }

    public void Book(Services_tbl tbl) {
        if(bookServiceLayout == null) bookServiceLayout = findViewById(R.id.book_service_layout);

        bookServiceLayout.setVisibility(VISIBLE);
        ModalBookServiceBinding bookBinding = ModalBookServiceBinding.bind(view);
        bookBinding.bookServiceBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(() -> {
                    try {
                        int day = bookBinding.datepicker.getDayOfMonth();
                        int month = bookBinding.datepicker.getMonth()+1; // Month is 0-indexed
                        int year = bookBinding.datepicker.getYear();

                        db = RoomDB.getInstance(Services.this);
                        daoEvents = db.daoEvents();
                        if (daoEvents == null) daoEvents = RoomDB.getInstance(Services.this).daoEvents();

                        String date = month + " - " + day + " - " + year;

                        daoEvents.insertBooking(new BookingTbl(user.getUser_id(), tbl.getService_id(), date, "Pending"));
                        runOnUiThread(() -> {
                            ShowConfirm("BOOKED", "You've successfully booked " + tbl.getServiceName(),
                                    "Confirm", new Runnable() {
                                        @Override
                                        public void run() {
                                            HideConfirmation();
                                            bookServiceLayout.setVisibility(GONE);
                                        }
                                    });
                        });
                    } catch (Exception ex) {
                        Log.e("error", ex.getMessage());
                    }
                });
            }
        });

        bookBinding.bookServiceCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookServiceLayout.setVisibility(GONE);
            }
        });
    }
}