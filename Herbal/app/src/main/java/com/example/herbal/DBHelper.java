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

        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "contactsDB";
        public static final String TABLE_CONTACTS = "contacts";
    /*
        public static final String TABLE_IMAGES = "images";
        public static final String TABLE_NOTE = "note";*/

        public static final String EXTERNAL_KEY_ID = "_id";
        public static final String CONTACTS_KEY_NAME = "name";
        public static final String CONTACTS_KEY_EMAIL = "email";


       /* public static final String NOTE_KEY_TEXT = "text";
        public static final String NOTE_KEY_CREATEDATA = "data";

        public static final String IMAGE_KEY_IMAGE = "blob";
        public static final String IMAGE_FOREIGN_KEY_IDNOTE = "_idNote";*/


       /* public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }*/
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_CONTACTS + "(" +
                    EXTERNAL_KEY_ID + " integer primary key," +
                    CONTACTS_KEY_NAME + " text," +
                    CONTACTS_KEY_EMAIL + " text );");
            //CreateTableNote(db);
           // CreateTableImage(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CONTACTS);
            onCreate(db);
           /* if (oldVersion != newVersion ){
                DATABASE_VERSION = newVersion;
                Log.d("mLog", " --- onUpgrade database from " + oldVersion
                        + " to " + newVersion + " version --- ");
                //db.execSQL("drop table if exists " + TABLE_CONTACTS);
               // CreateContacts(db);
                CreateTableNote(db);
                CreateTableImage(db);


            }
            else{
               *//* db.execSQL("drop table if exists " + TABLE_CONTACTS);
                db.execSQL("drop table if exists " + TABLE_IMAGES);
                db.execSQL("drop table if exists " + TABLE_NOTE);
                onCreate(db);*//*
            }*/

        }

        /*private void CreateNewTables(){

        }
        private void CreateTableNote(SQLiteDatabase db){
            db.execSQL("create table " + TABLE_NOTE + "(" +
                    EXTERNAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    NOTE_KEY_TEXT + " TEXT," +
                    NOTE_KEY_CREATEDATA + " data );");
            Log.d("mLog", " --- CREATE NOTE ---- ");
        }
        private void CreateTableImage(SQLiteDatabase db){
            // создаем таблицу картинок
            db.execSQL("create table " + TABLE_IMAGES + "(" +
                    EXTERNAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    IMAGE_KEY_IMAGE + " blob," +
                    IMAGE_FOREIGN_KEY_IDNOTE + " INTEGER," +
                    "FOREIGN KEY (" + IMAGE_FOREIGN_KEY_IDNOTE + ") REFERENCES " + TABLE_NOTE + "(" + EXTERNAL_KEY_ID + ")" + " );");
            Log.d("mLog", " --- CREATE IMAGE ---- ");
        }
        private void CreateContacts(SQLiteDatabase db){
            db.execSQL("create table " + TABLE_CONTACTS + "(" +
                    EXTERNAL_KEY_ID + " integer primary key," +
                    CONTACTS_KEY_NAME + " text," +
                    CONTACTS_KEY_EMAIL + " text );");
        }*/
    }


