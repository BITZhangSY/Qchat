package com.bit_zt.proj_socket.Common;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bit_zt on 15/11/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private String sql_createRecordsTable;

    private Context context;

    public static final String SQL_createContactsTable = "create table contact ("
                            + "account text primary key, "
                            + "deviceName text, "
                            + "nickname text, "
                            + "sortLetter text, "
                            + "sortPinyin text) ";



    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_createContactsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
