package com.example.projectmobilecse441;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UsersAccountHelper {
    public static ArrayList<UsersAccount> getAll(Context context) {
        ArrayList<UsersAccount> list = new ArrayList<>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from UsersAccount", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String userName = cursor.getString(1);
            String password = cursor.getString(2);
            String fullName = cursor.getString(3);
            int accountType = cursor.getInt(4);
            int totalSpent = cursor.getInt(5);
            int rewardPoint = cursor.getInt(6);
            int timesShopping = cursor.getInt(7);
            String address = cursor.getString(8);
            int phone = cursor.getInt(9);
            UsersAccount user = new UsersAccount(id,userName,password,fullName,accountType,totalSpent,rewardPoint,timesShopping,address,phone);
            list.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public static void update(Context context, int id, int timesShopping,int spent){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int totalSpent = 0;
        int rewardPoint = 0;
        Cursor cursor = db.rawQuery("Select * from UsersAccount", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int userId = cursor.getInt(0);
            if(userId==id){
                totalSpent = cursor.getInt(5);
                rewardPoint = cursor.getInt(6);
            }
            cursor.moveToNext();
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("TimesShopping",timesShopping+1);
        values.put("TotalSpent",spent+totalSpent);
        values.put("RewardPoint",(spent*0.05)+rewardPoint);
        db.update("UsersAccount",values,"ID=?",new String[]{id+""});

    }
}
