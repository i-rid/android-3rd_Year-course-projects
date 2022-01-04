package com.example.sqlitelogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Login.db";

    public DBHelper( Context context) {
        //this would create database of the name provided
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //this query would crate a database with these first column username second one password
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    //this method would insert these data
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //this object would insert columns
        contentValues.put("username", username);
        contentValues.put("password", password);
        //all the values of coontenValues will be put in database
        long result =  MyDB.insert("users", null,contentValues);
        //if insertion is fails return false else true
        if (result==-1)return false;
        else return  true;
    }
    //if uesr name already exists we will return true else false
    public  Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? ", new String[] {username});
        if(cursor.getCount() > 0)return  true;
        else return  false;
    }


    //if username and password exists return ture else false
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount() > 0)return  true;
        else return  false;
    }

}
