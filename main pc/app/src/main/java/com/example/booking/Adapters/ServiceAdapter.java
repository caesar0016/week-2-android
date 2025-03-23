package com.example.booking.Adapters;

import static android.view.View.GONE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.EventBooking.Services_tbl;
import com.example.booking.Services;
import com.example.booking.databinding.ItemServiceBinding;
import com.example.booking.databinding.ModalUpdateServiceBinding;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    public ServiceAdapter(List<Services_tbl> list, Services context) {
        this.list = list;
        this.context = context;
    }

    List<Services_tbl> list;
    Services context;
    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemServiceBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        Services_tbl model = list.get(position);
        holder.itemServiceBinding.itemServiceNameTxt.setText(model.getServiceName());
        holder.itemServiceBinding.itemServiceDescTxt.setText(model.getDescription());
        holder.itemServiceBinding.itemServicePriceTxt.setText(model.getPrice() + "");

        holder.itemServiceBinding.itemServiceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ShowUpdate(model);
            }
        });

        holder.itemServiceBinding.itemServiceDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ShowConfirmation("DELETE", "Are you sure you want to delete?",
                        "Confirm", new Runnable() {
                            @Override
                            public void run() {
                                context.DeleteService(model.getService_id());
                                context.HideConfirmation();
                                context.ShowConfirm("SUCCESS", "You've successfully deleted " + model.getServiceName(),
                                        "Confirm", new Runnable() {
                                            @Override
                                            public void run() {
                                                context.HideConfirmation();
                                            }
                                        });
                            }
                        }, "Cancel", new Runnable() {
                            @Override
                            public void run() {
                                context.HideConfirmation();
                            }
                        });
            }
        });

        holder.itemServiceBinding.itemServiceArchiveBtn.setText(model.isIs_archived() ? "RESTORE" : "ARCHIVE");
        holder.itemServiceBinding.itemServiceArchiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemServiceBinding.itemServiceArchiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.ArchiveRestoreService(model);
                    }
                });
            }
        });

        holder.itemServiceBinding.itemServiceBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Book(model);
            }
        });
        if(context.role.equals("customer")) {
            holder.itemServiceBinding.adminLayout.setVisibility(GONE);
        }
        else if(context.role.equals("admin")) holder.itemServiceBinding.customerLayout.setVisibility(GONE);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemServiceBinding itemServiceBinding;
        public ViewHolder(ItemServiceBinding itemServiceBinding) {
            super(itemServiceBinding.getRoot());
            this.itemServiceBinding = itemServiceBinding;
        }
    }
}
