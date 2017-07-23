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


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
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


        public List<String> selectAll(SQLiteDatabase db) {
            List<String> list = new ArrayList<String>();
            Cursor cursor = db.query(TABLE_CONTACTS, new String[] { "name" },
                    null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list;
        }

        public List<String> selectAllID(SQLiteDatabase db, String id) {
            List<String> list = new ArrayList<String>();

            String selection = KEY_NAME + " = ?";
            String[] selectionArgs =  new String[]{ /*String.valueOf(id)*/ id};
            Cursor c = db.query(TABLE_CONTACTS, new String[] { KEY_NAME }, selection, selectionArgs, null, null, null);
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(0));

                } while (c.moveToNext());
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
            return list;

        }
    }


