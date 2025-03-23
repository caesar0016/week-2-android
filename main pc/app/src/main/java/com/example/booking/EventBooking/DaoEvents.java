package com.example.booking.EventBooking;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoEvents {
    //----------------------------  Insert Section for the system -----------------------------
    @Insert
    void insertService(Services_tbl service);

    @Insert
    void insertAccounts(UserAccounts_tbl userAccount);

    @Insert
    void insertBooking(BookingTbl booking);

    @Insert
    void insertPayment(Payment_tbl payment);

    //----------------------------  Listing Data Section for the system -----------------------------

    @Query("Select * from Services_tbl")
    List<Services_tbl> viewServices();

    @Query("Select * from Services_tbl where service_id = :id")
    Services_tbl viewServices(int id);

    @Query("Select * from UserAccounts_tbl")
    List<UserAccounts_tbl> viewUserAccount();

    @Query("Select * from UserAccounts_tbl where user_id = :id")
    List<UserAccounts_tbl> viewUserByID(int id);

    @Query("Select * from BookingTbl")
    List<BookingTbl> viewBooking();

    @Query("Select * from BookingTbl where status = :filter")
    List<BookingTbl> viewBookingFilter(String filter);

    @Query("Select * from Payment_tbl")
    List<Payment_tbl> viewPayment();

    //----------------------------  Update Data Section for the system -----------------------------
    @Query("Update Services_tbl set serviceName = :name, description = :desc, price = :price where service_id = :id")
    void updateService(String name, String desc, double price, int id);

    @Query("Update Useraccounts_tbl set username = :username, password = :pass, role = :role where user_id = :id")
    void updateUserAccount(String username, String pass, String role, int id);

    @Query("Update BookingTbl set status = :status where booking_id = :booking_id")
    void updateBooking(int booking_id, String status);

    //----------------------------  Delete Data Section for the system -----------------------------

    @Query("Delete from Services_tbl where service_id = :id")
    void deleteService(int id);

    @Query("Update useraccounts_tbl set username = :username, role = :role where user_id = :id")
    void updateWithoutPass(String username, String role, int id);

    @Query("Delete from Useraccounts_tbl where user_id = :id")
    void deleteUser(int id);

    @Query("Delete from BookingTbl where booking_id = :id")
    void deleteBooking(int id);


    //----------------------------  Archived Data Section for the system -----------------------------
    @Query("Update services_tbl set is_archived = :status where service_id = :id")
    void archivedRestoreService(int status, int id);


}
