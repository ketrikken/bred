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

        public static final int DATABASE_VERSION = 14;
        public static final String DATABASE_NAME = "contactsDB";

        public static final String EXTERNAL_KEY_ID = "_id";

    public static final String TABLE_CONTACTS = "contacts";
        public static final String CONTACTS_KEY_NAME = "name";
        public static final String CONTACTS_KEY_EMAIL = "email";


    public static final String TABLE_NOTE = "note";
        public static final String NOTE_KEY_TEXT = "text";
        public static final String NOTE_KEY_CREATEDATA = "data";
        public static final String NOTE_KEY_IMAGE = "image";
        public static final String NOTE_KEY_HEADER = "header";
        public static final String NOTE_KEY_NAME = "name";


    public static final String TABLE_THEME_NOTE = "theme_note";
        public static final String THEM_NOTE_KEY_HEADER = "header";



       /* public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }*/
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("mLog", "-------------------onCreate---------------");
            CreateContacts(db);
            CreateTableThemeNote(db);
            CreateTableNote(db);
            db.execSQL("insert into " + TABLE_THEME_NOTE + " VALUES (null, 'первая тема заметки');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion >  oldVersion){

                    Log.d("mLog", oldVersion + " " + newVersion);
                    Log.d("mLog", "-------------------onUpdate---------------");
                    db.execSQL("drop table if exists " + TABLE_CONTACTS);
                    db.execSQL("drop table if exists " + TABLE_NOTE);
                    db.execSQL("drop table if exists " + TABLE_THEME_NOTE);
                    onCreate(db);

            }

        }

        private void CreateTableNote(SQLiteDatabase db){
            db.execSQL("create table " + TABLE_NOTE + "(" +
                    EXTERNAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    NOTE_KEY_HEADER + " INTEGER, " +
                    NOTE_KEY_NAME + " TEXT , " +
                    NOTE_KEY_TEXT + " TEXT, " +
                    NOTE_KEY_IMAGE + " TEXT, " +
                    NOTE_KEY_CREATEDATA + " TEXT, " +
                    " FOREIGN KEY (" + NOTE_KEY_HEADER + ") REFERENCES " + TABLE_THEME_NOTE + "(" + EXTERNAL_KEY_ID + ")" + "ON DELETE CASCADE" + " );");
            Log.d("mLog", " --- CREATE NOTE ---- ");
        }
        private void CreateTableThemeNote(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_THEME_NOTE + "(" +
                EXTERNAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                THEM_NOTE_KEY_HEADER + " TEXT );");
        Log.d("mLog", " --- CREATE theme NOTE ---- ");
    }
        /*private void CreateTableImage(SQLiteDatabase db){
            // создаем таблицу картинок
            db.execSQL("create table " + TABLE_IMAGES + "(" +
                    EXTERNAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    IMAGE_KEY_IMAGE + " blob," +
                    IMAGE_FOREIGN_KEY_IDNOTE + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + IMAGE_FOREIGN_KEY_IDNOTE + ") REFERENCES " + TABLE_NOTE + "(" + EXTERNAL_KEY_ID + ")" + "ON UPDATE SET NULL" + " );");
            Log.d("mLog", " --- CREATE IMAGE ---- ");
        }*/
        private void CreateContacts(SQLiteDatabase db){
            db.execSQL("create table " + TABLE_CONTACTS + "(" +
                    EXTERNAL_KEY_ID + " integer primary key," +
                    CONTACTS_KEY_NAME + " text," +
                    CONTACTS_KEY_EMAIL + " text );");
        }
    }


