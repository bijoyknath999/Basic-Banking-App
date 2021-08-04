package com.dunkeydev.bankingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;
import com.dunkeydev.bankingsystem.sqlite.BankModels;
import com.dunkeydev.bankingsystem.sqlite.TransctionsBDControllers;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView Name, Email, Balance;
    private Button AddBTn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("My Profile");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);

        Name = findViewById(R.id.profile_name);
        Email = findViewById(R.id.profile_email);
        Balance = findViewById(R.id.profile_balance);
        AddBTn = findViewById(R.id.profile_add_balance);

        loaddata();
        AddBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
    }

    private void showdialog() {
        BankDBControllers bankDBControllers = new BankDBControllers(ProfileActivity.this);
        int camount = bankDBControllers.getSingleCustomersData("bijoyknath999@gmail.com").get(0).getBalance();
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.add_money);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText Amount;
        Amount = dialog.findViewById(R.id.dialog_amount);
        Button dialogButton = dialog.findViewById(R.id.dialog_money_send);
        dialogButton.setText("Add money");
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = Integer.parseInt(Amount.getText().toString());
                bankDBControllers.updateBalance("bijoyknath999@gmail.com",amount+camount);
                dialog.dismiss();
                loaddata();
            }
        });
        dialog.show();
    }

    private void loaddata() {
        BankDBControllers bankDBControllers = new BankDBControllers(ProfileActivity.this);
        BankModels bankModels;
        bankModels = bankDBControllers.getSingleCustomersData("bijoyknath999@gmail.com").get(0);
        String name = bankModels.getName();
        String email = bankModels.getEmail();
        int balance = bankModels.getBalance();
        Name.setText("Name : "+name);
        Email.setText("Email : "+email);
        Balance.setText("Balance : $"+balance);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}