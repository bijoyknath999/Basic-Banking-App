package com.dunkeydev.bankingsystem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class TransctionsBDControllers {

    private final SQLiteDatabase db;

    private static final String myemail = "bijoyknath999@gmail.com";

    public TransctionsBDControllers(Context context) {
        db = DBHelper.getInstance(context).getWritableDatabase();
    }

    public boolean insertTransctionsData(String sender, String receiver, int balance) {
        ContentValues cv = new ContentValues();

        cv.put(DBConstants.COLUMN_SENDER, sender);
        cv.put(DBConstants.COLUMN_RECEIVER, receiver);
        cv.put(DBConstants.COLUMN_BALANCE_T, balance);
        long result = db.insert(DBConstants.TABLE_NAME_TRANSCTIONS,null, cv);
        return result != -1;
    }

    public ArrayList<TransctionsModels> getAllTransctionsData() {


        String[] projection = {
                DBConstants.COLUMN_ID_T,
                DBConstants.COLUMN_SENDER,
                DBConstants.COLUMN_RECEIVER,
                DBConstants.COLUMN_BALANCE_T,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DBConstants.COLUMN_ID_T + " DESC";
        String selection = DBConstants.COLUMN_SENDER + "=?";
        String[] selectionArgs = {myemail};

        Cursor c = db.query(
                DBConstants.TABLE_NAME_TRANSCTIONS,  // The table name to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    private ArrayList<TransctionsModels> fetchData(Cursor c) {
        ArrayList<TransctionsModels> ntyDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int itemId = c.getInt(c.getColumnIndexOrThrow(DBConstants.COLUMN_ID_T));
                    String sender = c.getString(c.getColumnIndexOrThrow(DBConstants.COLUMN_SENDER));
                    String receiver = c.getString(c.getColumnIndexOrThrow(DBConstants.COLUMN_RECEIVER));
                    int balance = c.getInt(c.getColumnIndexOrThrow(DBConstants.COLUMN_BALANCE_T));

                    // wrap up data list and return
                    ntyDataArray.add(new TransctionsModels(itemId, sender, receiver, balance));
                } while (c.moveToNext());
            }
            c.close();
        }
        return ntyDataArray;
    }

    public void delete(String itemId,Context context) {
        long result = db.delete(DBConstants.TABLE_NAME_TRANSCTIONS, "id=?", new String[]{itemId});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM " + DBConstants.TABLE_NAME_TRANSCTIONS);
    }

}
