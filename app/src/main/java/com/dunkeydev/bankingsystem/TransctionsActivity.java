package com.dunkeydev.bankingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dunkeydev.bankingsystem.adapters.CustomersAdapters;
import com.dunkeydev.bankingsystem.adapters.TransctionsAdapters;
import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;
import com.dunkeydev.bankingsystem.sqlite.BankModels;
import com.dunkeydev.bankingsystem.sqlite.TransctionsBDControllers;
import com.dunkeydev.bankingsystem.sqlite.TransctionsModels;

import java.util.ArrayList;

public class TransctionsActivity extends AppCompatActivity {

    public static RecyclerView RVTrancstions;
    public static TransctionsAdapters mAdapter;
    public static ArrayList<TransctionsModels> transctionsModels;
    public static TransctionsBDControllers transctionsBDControllers;
    private Toolbar mToolbar;
    public static MenuItem menuItemClearAll;
    public static TextView EmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transctions);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Transctions list");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        EmptyList = findViewById(R.id.transctions_empty);

        transctionsModels = new ArrayList<>();
        transctionsBDControllers = new TransctionsBDControllers(TransctionsActivity.this);

        RVTrancstions = findViewById(R.id.transtions_list);
        RVTrancstions.setLayoutManager(new LinearLayoutManager(TransctionsActivity.this));
        loadRV(TransctionsActivity.this);

    }

    public static void loadRV(Context context) {
        if(!transctionsModels.isEmpty())
        {
            transctionsModels.clear();
        }
        transctionsModels.addAll(transctionsBDControllers.getAllTransctionsData());
        mAdapter = new TransctionsAdapters(context, transctionsModels);
        RVTrancstions.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if (transctionsModels.isEmpty())
        {
            EmptyList.setVisibility(View.VISIBLE);
            if (menuItemClearAll!=null) {
                menuItemClearAll.setVisible(false);
            }
        }
        else
        {
            EmptyList.setVisibility(View.GONE);
            if (menuItemClearAll!=null) {
            menuItemClearAll.setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.menus_delete_all:
                showdialog();

        }
        return super.onOptionsItemSelected(item);
    }

    private void showdialog() {
        // custom dialog
        final Dialog dialog = new Dialog(TransctionsActivity.this);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button YesBTN, NoBTN;
        YesBTN = dialog.findViewById(R.id.delete_btn_yes);
        NoBTN = dialog.findViewById(R.id.delete_btn_no);

        YesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransctionsBDControllers transctionsBDControllers = new TransctionsBDControllers(TransctionsActivity.this);
                transctionsBDControllers.deleteAll();
                loadRV(TransctionsActivity.this);
                dialog.dismiss();
            }
        });

        NoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deleteall, menu);
        menuItemClearAll = menu.findItem(R.id.menus_delete_all);
        loadRV(TransctionsActivity.this);
        return true;
    }

}