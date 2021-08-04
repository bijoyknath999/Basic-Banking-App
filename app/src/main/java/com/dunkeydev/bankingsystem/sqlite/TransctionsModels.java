package com.dunkeydev.bankingsystem.sqlite;

public class TransctionsModels {

    private int id;
    private String sender;
    private String receiver;
    private int balance;

    public TransctionsModels(int id, String sender, String receiver, int balance) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
