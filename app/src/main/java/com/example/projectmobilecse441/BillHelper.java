package com.example.projectmobilecse441;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BillHelper {
    public static boolean insert(Context context, int billId, int customerId, int total, String date,String address,int phone){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID",billId);
        values.put("CustomerId",customerId);
        values.put("Total",total);
        values.put("Date",date);
        values.put("Address",address);
        values.put("Phone",phone);
        long row = db.insert("bills",null,values);
        return (row>0);
    }
}
