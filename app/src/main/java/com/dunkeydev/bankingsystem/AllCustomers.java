package com.dunkeydev.bankingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dunkeydev.bankingsystem.adapters.CustomersAdapters;
import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;
import com.dunkeydev.bankingsystem.sqlite.BankModels;

import java.util.ArrayList;

public class AllCustomers extends AppCompatActivity {

    public static RecyclerView RVCustomers;
    public static CustomersAdapters mAdapter;
    public static ArrayList<BankModels> customersList, getCustomersList;
    public static BankDBControllers bankDBControllers;
    private TextView CustomersName, CustomersEmail, CustomersBalance;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_customers);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("View customers");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);

        CustomersName = findViewById(R.id.customers_name);
        CustomersEmail = findViewById(R.id.customers_email);
        CustomersBalance = findViewById(R.id.customers_balance);


        customersList = new ArrayList<>();
        getCustomersList = new ArrayList<>();
        bankDBControllers = new BankDBControllers(AllCustomers.this);

        RVCustomers = findViewById(R.id.customers_list);
        RVCustomers.setLayoutManager(new LinearLayoutManager(AllCustomers.this));

        loadRV(AllCustomers.this);
        getCustomersList.addAll(bankDBControllers.getSingleCustomersData("bijoyknath999@gmail.com"));
        if (getCustomersList.get(0)!=null)
        {
            CustomersName.setText("Name : "+getCustomersList.get(0).getName());
            CustomersEmail.setText("Email : "+getCustomersList.get(0).getEmail());
            CustomersBalance.setText("Balance : $"+getCustomersList.get(0).getBalance());
        }
    }

    public static void loadRV(Context context)
    {
        if(customersList!=null)
        {
            customersList.clear();
        }
        customersList.addAll(bankDBControllers.getAllCustomersData());
        mAdapter = new CustomersAdapters(context, customersList);
        RVCustomers.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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