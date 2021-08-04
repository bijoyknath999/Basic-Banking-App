package com.dunkeydev.bankingsystem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class BankDBControllers {

    private final SQLiteDatabase db;

    public static String myemail = "bijoyknath999@gmail.com";

    public BankDBControllers(Context context) {
        db = DBHelper.getInstance(context).getWritableDatabase();
    }

    public boolean insertCustomersData(String name, String email, Context context) {
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.COLUMN_NAME, name);
        cv.put(DBConstants.COLUMN_EMAIL, email);
        cv.put(DBConstants.COLUMN_BALANCE, 0);
        long result = db.insert(DBConstants.TABLE_NAME_BANK,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_LONG).show();
        }
        return result != -1;
    }

    public ArrayList<BankModels> getAllCustomersData() {


        String[] projection = {
                DBConstants.COLUMN_ID,
                DBConstants.COLUMN_NAME,
                DBConstants.COLUMN_EMAIL,
                DBConstants.COLUMN_BALANCE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DBConstants.COLUMN_ID + " DESC";

        Cursor c = db.query(
                DBConstants.TABLE_NAME_BANK,  // The table name to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    public ArrayList<BankModels> getSingleCustomersData(String email) {


        String[] projection = {
                DBConstants.COLUMN_ID,
                DBConstants.COLUMN_NAME,
                DBConstants.COLUMN_EMAIL,
                DBConstants.COLUMN_BALANCE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DBConstants.COLUMN_ID + " DESC";
        String selection = DBConstants.COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor c = db.query(
                DBConstants.TABLE_NAME_BANK,  // The table name to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    /*public ArrayList<notifymodel> getUnreadData() {


        String[] projection = {
                DbConstants.COLUMN_ID,
                DbConstants.COLUMN_TITLE,
                DbConstants.COLUMN_CONTENT,
                DbConstants.COLUMN_URL,
                DbConstants.COLUMN_READ_STATUS
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DbConstants.COLUMN_ID + " DESC";
        String selection = DbConstants.COLUMN_READ_STATUS + "=?";
        String[] selectionArgs = {UNREAD};

        Cursor c = db.query(
                DbConstants.TABLE_NAME_NOTIFY,  // The table name to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }*/

    private ArrayList<BankModels> fetchData(Cursor c) {
        ArrayList<BankModels> ntyDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int itemId = c.getInt(c.getColumnIndexOrThrow(DBConstants.COLUMN_ID));
                    String name = c.getString(c.getColumnIndexOrThrow(DBConstants.COLUMN_NAME));
                    String email = c.getString(c.getColumnIndexOrThrow(DBConstants.COLUMN_EMAIL));
                    int balance = c.getInt(c.getColumnIndexOrThrow(DBConstants.COLUMN_BALANCE));

                    // wrap up data list and return
                    ntyDataArray.add(new BankModels(itemId, name, email, balance));
                } while (c.moveToNext());
            }
            c.close();
        }
        return ntyDataArray;
    }

    public void updateBalance(String email, int balance) {

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_BALANCE, balance);

        // Which row to update, based on the ID
        String selection = DBConstants.COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        db.update(
                DBConstants.TABLE_NAME_BANK,
                values,
                selection,
                selectionArgs);
    }

    public void delete(String itemId,Context context) {
        long result = db.delete(DBConstants.TABLE_NAME_BANK, "id=?", new String[]{itemId});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM " + DBConstants.TABLE_NAME_BANK);
    }

    /*public void updateStatus(String id, String title, String url, String content,Context context) {

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.COLUMN_TITLE, title);
        cv.put(DbConstants.COLUMN_URL, url);
        cv.put(DbConstants.COLUMN_CONTENT, content);

        long result = db.update(DbConstants.TABLE_NAME_NOTIFY, cv, "_id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }*/
}
