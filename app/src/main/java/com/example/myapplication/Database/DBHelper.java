package com.example.myapplication.Database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Database.Models.User;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Kursach.db";
    private static final int SCHEMA = 1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS User (\n" + "    userid integer PRIMARY KEY AUTOINCREMENT,\n" + "    login TEXT,\n" + "    password TEXT,\n" + "     email TEXT);\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS Komplect (\n" + "    komplectid integer PRIMARY KEY AUTOINCREMENT,\n" +  "     userid integer,\n" + "    name TEXT,\n" + "    FOREIGN KEY (userid) REFERENCES User (userid));\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS Zakupka (\n" + "    zakupkaid integer PRIMARY KEY AUTOINCREMENT,\n" + "    userid integer,\n" + "    komplectid integer,\n" + "    price inetegr,\n" + "    date TEXT,\n" + "    FOREIGN KEY (userid) REFERENCES User (userid) ON DELETE CASCADE,\n" + "    FOREIGN KEY (komplectid) REFERENCES Komplect (komplectid));\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS Sborka (\n" + "    sborkaid integer PRIMARY KEY AUTOINCREMENT,\n" + "    userid integer,\n" + "   name TEXT,\n" + "    price integer,\n" + "    FOREIGN KEY (userid) REFERENCES User (userid));\n" );
        db.execSQL("CREATE TABLE IF NOT EXISTS SborkaKomplect (\n" + "    sborkakomplectid integer PRIMARY KEY AUTOINCREMENT,\n" + "    sborkaid integer,\n" + "    komplectid integer,\n" + "    FOREIGN KEY (sborkaid) REFERENCES Sborka (sborkaid),\n" + "    FOREIGN KEY (komplectid) REFERENCES Komplect (komplectid));\n" );
    }
    public User getUserByLogin(String login)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "Select * from User where login = '" + login + "'", null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do
        {
            User user = new User();
            user.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            user.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            return user;
        }
        while
        (
                cursor.moveToNext()
        );
    }
    /*public void updateWidget() {
        Intent intent = new Intent(DBHelper.this, my_widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), my_widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void delete(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
