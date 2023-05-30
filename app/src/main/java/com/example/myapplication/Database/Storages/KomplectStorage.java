package com.example.myapplication.Database.Storages;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Models.User;

import java.util.ArrayList;
import java.util.List;

public class KomplectStorage
{
    DBHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "Komplect";
    final String COLUMN_ID = "komplectid";
    final String COLUMN_USERID = "userid";
    final String COLUMN_NAME = "name";

    public KomplectStorage (Context context)
    {
        sqlHelper = new DBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public KomplectStorage open()
    {
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public ArrayList<Komplect> getFullList(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_USERID + " = " + model.userid, null);
        ArrayList<Komplect> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Komplect obj = new Komplect();
            obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            list.add(obj);

        }
        while (cursor.moveToNext());
        return list;
    }
    public Komplect getByName(User model, String name)
    {
        db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( /*"Select * from User where login = 'kop' AND password = 'kop'", null*/"Select * from Komplect where name = '" + name + "'"+" AND userid = "+ model.userid , null/*"select * from " + TABLE + " where " + COLUMN_LOGIN + " = '" + login + "' AND " + COLUMN_PASSWORD + " = '" + password + "'", null*/);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do
        {
            Komplect komplect = new Komplect();
            komplect.setKomplectid(cursor.getInt(cursor.getColumnIndex("komplectid")));
            komplect.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            komplect.setName(cursor.getString(cursor.getColumnIndex("name")));
            return komplect;
        }
        while
        (
                cursor.moveToNext()
        );
    }
    public List<Komplect> getFilteredList(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_USERID + " = " + model.userid, null);
        List<Komplect> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Komplect obj = new Komplect();
            obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            list.add(obj);
            cursor.moveToNext();
        }
        while (!cursor.isAfterLast());
        return list;
    }
    public Komplect getById(int Id)
    {
        Cursor cursor = db.rawQuery("Select * from Komplect where komplectid = " + Id , null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do
        {
            Komplect komplect = new Komplect();
            komplect.setKomplectid(cursor.getInt(cursor.getColumnIndex("komplectid")));
            komplect.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            komplect.setName(cursor.getString(cursor.getColumnIndex("name")));
            return komplect;
        }
        while
        (
                cursor.moveToNext()
        );
    }
    public Komplect getKomplect(User model, Komplect model2) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_ID + " = " + model.userid + " where " + COLUMN_USERID + " = " + model2.komplectid, null);
        Komplect obj = new Komplect();
        if (!cursor.moveToFirst()) {
            return null;
        }
        obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
        obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
        obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
        return obj;
    }

    public void create(User model, Komplect model2) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_NAME, model2.name);
        db.insert(TABLE, null, content);
    }

    public void update(Komplect model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_ID, model.komplectid);
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_NAME, model.name);
        String where = COLUMN_ID + " = " + model.komplectid;
        db.update(TABLE, content, where, null);
    }

    public void delete(Komplect model2) {
        String where = COLUMN_ID + " = " + model2.komplectid;
        db.delete(TABLE, where, null);
    }

    public void clear() {
        db.delete(TABLE, null, null);
    }

    public int getElementCount() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE);
    }
}
