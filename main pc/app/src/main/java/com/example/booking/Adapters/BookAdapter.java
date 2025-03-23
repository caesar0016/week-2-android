package com.example.booking.Adapters;

import static android.view.View.GONE;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.Booking;
import com.example.booking.EventBooking.BookingTbl;
import com.example.booking.EventBooking.RoomDB;
import com.example.booking.EventBooking.Services_tbl;
import com.example.booking.EventBooking.UserAccounts_tbl;
import com.example.booking.Services;
import com.example.booking.databinding.ItemServiceBinding;
import com.example.booking.databinding.QueuedBookingBinding;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    public BookAdapter(List<BookingTbl> list, Booking context) {
        this.list = list;
        this.context = context;
    }

    List<BookingTbl> list;
    Booking context;

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookAdapter.ViewHolder(QueuedBookingBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        BookingTbl model = list.get(position);

        AsyncTask.execute(() -> {
            context.db = RoomDB.getInstance(context);
            context.daoEvents = context.db.daoEvents();

            UserAccounts_tbl users = context.daoEvents.viewUserByID(model.getUser_id()).get(0);
            Services_tbl services = context.daoEvents.viewServices(model.getService_id());

            context.runOnUiThread(() -> {
                holder.itemBookBinding.itemBookServicenameTxt.setText(services.getServiceName());
                holder.itemBookBinding.itemBookUsernameTxt.setText(users.getUsername());
                holder.itemBookBinding.itemBookDateTxt.setText(model.getOnCreate());
                holder.itemBookBinding.itemBookingStatusTxt.setText(model.getStatus());
            });
        });

        if(model.getStatus().equalsIgnoreCase("Accepted") ||
                model.getStatus().equalsIgnoreCase("Declined")) {
            holder.itemBookBinding.itemBookingAcceptBtn.setVisibility(GONE);
            holder.itemBookBinding.itemBookingDeclineBtn.setVisibility(GONE);
        }

        holder.itemBookBinding.itemBookingAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Accept(model);
            }
        });

        holder.itemBookBinding.itemBookingDeclineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Decline(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public QueuedBookingBinding itemBookBinding;
        public ViewHolder(QueuedBookingBinding itemBookBinding) {
            super(itemBookBinding.getRoot());
            this.itemBookBinding = itemBookBinding;
        }
    }
}
