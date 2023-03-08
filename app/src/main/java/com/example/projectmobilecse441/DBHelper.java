package com.example.projectmobilecse441;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(
                context,"BookStoreDB",null,1
        );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String cmd ="Create table Carts(ID integer primary key autoincrement,OrderId integer, BookId integer,BookTitle text,Author text, Img BLOB, Quantity integer, Price integer, SubTotalPrice integer,QuantityStorage integer)";
        sqLiteDatabase.execSQL(cmd);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists Carts");
        onCreate(sqLiteDatabase);
    }
}
