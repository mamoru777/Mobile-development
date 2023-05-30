package com.example.myapplication.Database.Storages;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserStorage
{
    DBHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "User";
    final String COLUMN_ID = "userid";
    final String COLUMN_LOGIN = "login";
    final String COLUMN_PASSWORD = "password";
    final String COLUMN_EMAIL = "email";

    public UserStorage (Context context)
    {
        sqlHelper = new DBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public UserStorage open()
    {
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public ArrayList<User> getFullList() {
        Cursor cursor = db.rawQuery("select * from " + TABLE, null);
        ArrayList<User> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            User user = new User();
            user.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            user.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            list.add(user);
        }
        while (cursor.moveToNext());
        cursor.close();
        return list;
    }

    public List<User> getFilteredList(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE, null);
        List<User> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            User obj = new User();
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.login = cursor.getString((int) cursor.getColumnIndex(COLUMN_LOGIN));
            obj.password = cursor.getString((int) cursor.getColumnIndex(COLUMN_PASSWORD));
            obj.email = cursor.getString((int) cursor.getColumnIndex(COLUMN_EMAIL));
            list.add(obj);
            cursor.moveToNext();
        }
        while (!cursor.isAfterLast());
        return list;
    }
    public boolean getUserByFiltr2 (String login, String password)
    {
        db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( /*"Select * from User where login = 'kop' AND password = 'kop'", null*/"Select * from User where login = '" + login + "'"+" AND password = '"+ password +"'", null);
        if (cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public User getUserByFiltr (String login, String password)
    {
        db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( /*"Select * from User where login = 'kop' AND password = 'kop'", null*/"Select * from User where login = '" + login + "'"+" AND password = '"+ password +"'", null/*"select * from " + TABLE + " where " + COLUMN_LOGIN + " = '" + login + "' AND " + COLUMN_PASSWORD + " = '" + password + "'", null*/);
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
    public User getUserByLogin(String login)
    {
        db = sqlHelper.getReadableDatabase();
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
    public int getId(String login)
    {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( "Select * from User where login = '" + login + "'", null);
        return cursor.getInt(cursor.getColumnIndex("userid"));

    }
    public User getElement(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_ID + " = " + model.userid, null);
        User obj = new User();
        if (!cursor.moveToFirst()) {
            return null;
        }
        obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
        obj.login = cursor.getString((int) cursor.getColumnIndex(COLUMN_LOGIN));
        obj.password = cursor.getString((int) cursor.getColumnIndex(COLUMN_PASSWORD));
        obj.email = cursor.getString((int) cursor.getColumnIndex(COLUMN_EMAIL));
        return obj;
    }
    public User getElementById(int id) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_ID + " = " + id, null);
        User obj = new User();
        if (!cursor.moveToFirst()) {
            return null;
        }
        obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
        obj.login = cursor.getString((int) cursor.getColumnIndex(COLUMN_LOGIN));
        obj.password = cursor.getString((int) cursor.getColumnIndex(COLUMN_PASSWORD));
        obj.email = cursor.getString((int) cursor.getColumnIndex(COLUMN_EMAIL));
        return obj;
    }

    public void create(User model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_LOGIN, model.login);
        content.put(COLUMN_PASSWORD, model.password);
        content.put(COLUMN_EMAIL, model.email);
        db.insert(TABLE, null, content);
    }

    public void update(User model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_ID, model.userid);
        content.put(COLUMN_LOGIN, model.login);
        content.put(COLUMN_PASSWORD, model.password);
        content.put(COLUMN_EMAIL, model.email);
        String where = COLUMN_ID + " = " + model.userid;
        db.update(TABLE, content, where, null);
    }

    public void delete(User model) {
        String where = COLUMN_ID + " = " + model.userid;
        db.delete(TABLE, where, null);
    }

    public void clear() {
        db.delete(TABLE, null, null);
    }

    public int getElementCount() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE);
    }

}
