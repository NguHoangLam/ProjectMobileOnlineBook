package com.example.projectmobilecse441;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BillDetailsHelper {
    public static void insert(Context context, int billCode, String bookTitle, String author, int price, int quantitySale){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BillCode",billCode);
        values.put("BookTitle",bookTitle);
        values.put("Author",author);
        values.put("Price",price);
        values.put("QuantitySale",quantitySale);
        db.insert("billDetails",null,values);

    }
}
