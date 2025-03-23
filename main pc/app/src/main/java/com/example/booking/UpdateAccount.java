package com.example.booking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.booking.EventBooking.DaoEvents;
import com.example.booking.EventBooking.RoomDB;

public class UpdateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DaoEvents dao = RoomDB.getInstance(this).daoEvents();

        int user_id = getIntent().getIntExtra("user_id", 0);
        String username = getIntent().getStringExtra("username");
        String role = getIntent().getStringExtra("role");

        //-- Initialization of edit text

        EditText edUsername = findViewById(R.id.edUpdateUsername);
        EditText edPassword = findViewById(R.id.edUpdatePassword);
        EditText edConfirmPass = findViewById(R.id.edUpdateConfirmPass);
        EditText edRole = findViewById(R.id.edUpdateRole);

        edUsername.setText(username);
        edRole.setText(role);

        //-- Initialization of edit text
        Button btnRegister = findViewById(R.id.btnRegisterAccount);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPass = edConfirmPass.getText().toString().trim();
                String role = edRole.getText().toString().trim();

                if(password.isEmpty() && confirmPass.isEmpty()){
                    AsyncTask.execute(()->{
                        dao.updateWithoutPass(username, role, user_id);
                    });
                }else{
                    AsyncTask.execute(() -> {
                        dao.updateUserAccount(username, password, role, user_id);
                    });
                }

                Intent intent = new Intent(UpdateAccount.this, ViewAccounts.class);
                startActivity(intent);

            }
        });

    }
}