package com.dunkeydev.bankingsystem.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dunkeydev.bankingsystem.AllCustomers;
import com.dunkeydev.bankingsystem.R;
import com.dunkeydev.bankingsystem.TransctionsActivity;
import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;
import com.dunkeydev.bankingsystem.sqlite.BankModels;
import com.dunkeydev.bankingsystem.sqlite.TransctionsBDControllers;
import com.dunkeydev.bankingsystem.sqlite.TransctionsModels;

import java.util.ArrayList;

public class TransctionsAdapters extends RecyclerView.Adapter<TransctionsAdapters.ViewHolder>{
    private final Context mContext;

    private final ArrayList<TransctionsModels> dataList;

    public TransctionsAdapters(Context context, ArrayList<TransctionsModels> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Text;
        private Button DelBTN;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            Text = itemView.findViewById(R.id.transctions_text);
            DelBTN = itemView.findViewById(R.id.transctions_delete);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_transctions, parent, false);
        return new ViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String sender = dataList.get(position).getSender();
        String receiver = dataList.get(position).getReceiver();
        int balance = dataList.get(position).getBalance();
        String id = String.valueOf(dataList.get(position).getId());


        holder.Text.setText("INR "+balance+" is transferred to \n"+receiver+" with \n"+sender);

        holder.DelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog(id);
            }
        });

    }

    public void showSuccessDialog(String id)
    {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button YesBTN, NoBTN;
        YesBTN = dialog.findViewById(R.id.delete_btn_yes);
        NoBTN = dialog.findViewById(R.id.delete_btn_no);

        YesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransctionsBDControllers transctionsBDControllers = new TransctionsBDControllers(mContext);
                transctionsBDControllers.delete(id,mContext);
                TransctionsActivity.loadRV(mContext);
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
    public int getItemCount() {
        return dataList.size();
    }
}