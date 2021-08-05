package com.dunkeydev.bankingsystem.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dunkeydev.bankingsystem.AllCustomers;
import com.dunkeydev.bankingsystem.MainActivity;
import com.dunkeydev.bankingsystem.R;
import com.dunkeydev.bankingsystem.sqlite.BankDBControllers;
import com.dunkeydev.bankingsystem.sqlite.BankModels;
import com.dunkeydev.bankingsystem.sqlite.TransctionsBDControllers;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CustomersAdapters extends RecyclerView.Adapter<CustomersAdapters.ViewHolder>{
    private final Context mContext;

    private final ArrayList<BankModels> dataList;

    public CustomersAdapters(Context context, ArrayList<BankModels> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Name;
        private TextView Email;
        private TextView Balance;
        private Button SendMoney;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            Name = itemView.findViewById(R.id.customers_name);
            Email = itemView.findViewById(R.id.customers_email);
            Balance = itemView.findViewById(R.id.customers_balance);
            SendMoney = itemView.findViewById(R.id.customers_send_money);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_customers, parent, false);
        return new ViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = dataList.get(position).getName();
        String email = dataList.get(position).getEmail();
        int balance = dataList.get(position).getBalance();

        Log.d("Adaters Data : ","Name : "+name+" Email : "+email+" Balance: "+balance);

        if (name != null) {
           holder.Name.setText("Name : "+name);
        }
        if (email != null) {
            if (email.equals("bijoyknath999@gmail.com"))
            {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
            }
            holder.Email.setText(" Email: "+email);
        }
        holder.Balance.setText("Balance : INR "+balance);

        holder.SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmoney(email);
            }
        });

    }

    public void sendmoney(String email)
    {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.add_money);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText Amount;
        Amount = dialog.findViewById(R.id.dialog_amount);
        Button dialogButton = dialog.findViewById(R.id.dialog_money_send);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = Integer.parseInt(Amount.getText().toString());
                Log.d("Details", "Amount "+amount);
                BankDBControllers bankDBControllers = new BankDBControllers(mContext);
                int cmount = bankDBControllers.getSingleCustomersData(email).get(0).getBalance();
                int senderamount = bankDBControllers.getSingleCustomersData("bijoyknath999@gmail.com").get(0).getBalance();

                if (amount>senderamount)
                {
                    Toast.makeText(mContext,"Insufficient Balance!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    bankDBControllers.updateBalance(email,amount+cmount);
                    bankDBControllers.updateBalance("bijoyknath999@gmail.com",senderamount-amount);
                    TransctionsBDControllers transctionsBDControllers = new TransctionsBDControllers(mContext);
                    transctionsBDControllers.insertTransctionsData("bijoyknath999@gmail.com",email,amount);
                    dialog.dismiss();
                    notifyDataSetChanged();
                    AllCustomers.loadRV(mContext);
                    showSuccessDialog("bijoyknath999@gmail.com",email,amount);
                }
            }
        });
        dialog.show();
    }

    public void showSuccessDialog(String sender, String receiver, int amount)
    {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.successfully_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView SuccessText;
        SuccessText = dialog.findViewById(R.id.success_dialog_text);
        SuccessText.setText("INR "+amount+" is transferred to \n"+receiver+" with "+sender);
        Button dialogButton = dialog.findViewById(R.id.success_dialog_Ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
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
