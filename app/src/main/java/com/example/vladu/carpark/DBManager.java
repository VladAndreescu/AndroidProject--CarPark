package com.example.vladu.carpark;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import java.net.PasswordAuthentication;

public class DBManager {

    private SQLiteDatabase sqlDB;
    static final String DBName = "CarPark";

    //Declaring variables for Users Table
    static final String UsersTable = "Users";
    static final String usernameCol = "Username";
    static final String passwordCol = "Password";
    static final String emailCol = "Email";
    static final int DBVersion = 1;

    //Declaring variables for CarPark offense Table





    //create table users(ID integer PRIMARY KEY AUTOINCREMENT, username text, Email text, Password text)

    static final String CreateTable = "CREATE TABLE IF NOT EXISTS " +UsersTable+"(ID integer PRIMARY KEY AUTOINCREMENT," +
            usernameCol+" text,"+emailCol+" text,"+passwordCol+" text);";

    static class DatabaseHelperUser extends SQLiteOpenHelper{
        Context context;
        DatabaseHelperUser(Context context){
            super(context,DBName,null,DBVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CreateTable);
            Toast.makeText(context,"Database is created",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("Drop table IF EXISTS "+ UsersTable);
            onCreate(db);
        }
    }

    public DBManager(Context context){


        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();

    }

    public long Insert(ContentValues values){

        long ID = sqlDB.insert(UsersTable,"",values);
        return ID;
    }
    // select username, password from Users where id =1
    public Cursor query(String[] projection, String selection, String[] selectionArgs, String order){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UsersTable);

        Cursor c = qb.query(sqlDB, projection, selection, selectionArgs,null,null,order);
        return c;



    }

}
