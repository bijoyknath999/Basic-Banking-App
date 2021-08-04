package com.dunkeydev.bankingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;

public class MainActivity extends AppCompatActivity {

    private CardView ViewAllCustomers, ViewAllTransctions, MyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewAllCustomers = findViewById(R.id.view_all_customers);
        ViewAllTransctions = findViewById(R.id.view_all_transctions);
        MyProfile = findViewById(R.id.my_profile);

        ViewAllCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllCustomers.class));
            }
        });

        ViewAllTransctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransctionsActivity.class));
            }
        });

        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                //AddCustomers();
            }
        });

    }

    public void AddCustomers()
    {
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_customers);
        TextView NameText, EmailText, BalanceText;
        NameText = dialog.findViewById(R.id.name);
        EmailText = dialog.findViewById(R.id.email);

        Button dialogButton = (Button) dialog.findViewById(R.id.send);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankDBControllers bankDBControllers = new BankDBControllers(MainActivity.this);

                String name = NameText.getText().toString();
                String email = EmailText.getText().toString();

                Log.d("Details", "Name "+name+" Email : "+email);
                bankDBControllers.insertCustomersData(name,email,MainActivity.this);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}