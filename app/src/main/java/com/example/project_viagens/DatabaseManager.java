package com.example.project_viagens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login_data.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS login_info (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS login_info;");
        onCreate(db);
    }

    public void insertLoginInfo(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM login_info");  // Remover entradas antigas antes de adicionar novas
        db.execSQL("INSERT INTO login_info (email, password) VALUES (?, ?)", new String[]{email, password});
        db.close();
    }

    public boolean hasSavedLoginInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login_info", null);
        boolean hasData = cursor.moveToFirst();
        cursor.close();
        return hasData;
    }

    @SuppressLint("Range")
    public String getSavedEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM login_info", null);
        String email = "";
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
        }
        cursor.close();
        return email;
    }

    @SuppressLint("Range")
    public String getSavedPassword() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM login_info", null);
        String password = "";
        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        return password;
    }

    public void open() {
        getWritableDatabase();
    }
}
