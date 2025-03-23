package com.example.booking.Adapters;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.UpdateAccount;
import com.example.booking.ViewAccounts;
import com.example.booking.databinding.AccountsItemRowsBinding;
import com.example.booking.EventBooking.UserAccounts_tbl;

import com.example.booking.EventBooking.DaoEvents;
import com.example.booking.EventBooking.RoomDB;

import java.util.List;

public class AdapterViewAccounts extends RecyclerView.Adapter<AdapterViewAccounts.Viewholder>{
    List<UserAccounts_tbl> list;
    ViewAccounts context;
    DaoEvents dao = RoomDB.getInstance(context).daoEvents();
    public AdapterViewAccounts(List<UserAccounts_tbl> list, ViewAccounts context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(AccountsItemRowsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        UserAccounts_tbl model = list.get(position);
        holder.accountBinding.tvUsername.setText(model.getUsername());
        holder.accountBinding.tvRole.setText(model.getRole());
        holder.accountBinding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = model.getUser_id();
                context.startActivity(new Intent(context, UpdateAccount.class)
                        .putExtra("user_id", user_id)
                        .putExtra("username", model.getUsername())
                        .putExtra("role", model.getRole()));
            }
        });

        holder.accountBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = model.getUser_id();
                AsyncTask.execute(()->{
                    dao.deleteUser(user_id);
                });
                context.updateRv();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public AccountsItemRowsBinding accountBinding;
        public Viewholder(AccountsItemRowsBinding accountBinding) {
            super(accountBinding.getRoot());
            this.accountBinding = accountBinding;
        }
    }
}
