package com.example.tweak.danabusbooking.database_management.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CheckingTableExists {

    // This method is used for checking whether a table has been created or hasn't yet in DB
    // by counting the number of rows in the table, if we don't have any rows in return,
    // that means the table hasn't created yet so we have to insert default values
    public static boolean doesTableExists(SQLiteDatabase readableDatabase, String tableName, String[] columnsName) {
        Cursor cursor = readableDatabase.query(tableName,
                columnsName, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null && (cursor.getCount() > 0)) {
            return true;
        }

        return false;
    }

}
