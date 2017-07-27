package com.example.herbal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алатиэль on 21.07.2017.
 */

    public class DBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "contactsDB";
        public static final String TABLE_CONTACTS = "contacts";

        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_EMAIL = "email";


       /* public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }*/
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_CONTACTS + "(" +
                    KEY_ID + " integer primary key," +
                    KEY_NAME + " text," +
                    KEY_EMAIL + " text );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("drop table if exists " + TABLE_CONTACTS);
            onCreate(db);
        }
    }


