package com.example.booking;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.booking.EventBooking.DaoEvents;
import com.example.booking.EventBooking.RoomDB;
import com.example.booking.EventBooking.UserAccounts_tbl;
import com.example.booking.databinding.ModalConfirmBinding;
import com.example.booking.databinding.ModalCreateServiceBinding;

public class MainApp extends AppCompatActivity {
    ConstraintLayout confirmLayout;
    ModalConfirmBinding confirmBinding;
    public View view;
    public ImageButton backBtn;
    public RoomDB db;
    public DaoEvents daoEvents;
    public static UserAccounts_tbl user;

    public static String role;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);

        confirmLayout = findViewById(R.id.confirm_layout);
        confirmBinding = ModalConfirmBinding.bind(view);

        db = RoomDB.getInstance(this);
        daoEvents = db.daoEvents();

    }

    public double StringToDouble(String s) {
        double d;
        try {
            d = Double.parseDouble(s);
        } catch (Exception ex) {
            return -1;
        }
        return d;
    }
    public void ShowConfirm(String title, String desc, String txt, Runnable listener) {
        ShowConfirmation(title, desc, txt, listener, null, null);
    }
    public void ShowConfirmation(String title, String description, String positive, Runnable positiveRunnable, String negative, Runnable negativeRunnable) {
            if(confirmLayout == null) confirmLayout = findViewById(R.id.confirm_layout);
            if(confirmBinding == null) confirmBinding = ModalConfirmBinding.bind(view);
            confirmLayout.setVisibility(VISIBLE);
            confirmBinding.confirmationTitleTxt.setText(title);
            confirmBinding.confirmationDescTxt.setText(description);
            confirmBinding.confirmationPositiveBtn.setText(positive);
            confirmBinding.confirmationPositiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveRunnable.run();
                }
            });

            if(negative == null || negative.isEmpty() || negativeRunnable == null) {
                confirmBinding.confirmationNegativeBtn.setVisibility(GONE);
                return;
            }

            confirmBinding.confirmationNegativeBtn.setText(negative);
            confirmBinding.confirmationNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeRunnable.run();
                }
            });

    }

    public void HideConfirmation() {
        confirmLayout.setVisibility(GONE);
    }
}
