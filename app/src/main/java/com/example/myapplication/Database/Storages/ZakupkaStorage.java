package com.example.myapplication.Database.Storages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Models.Zakupka;

import java.util.ArrayList;
import java.util.List;

public class ZakupkaStorage
{
    DBHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "Zakupka";
    final String COLUMN_ID = "zakupkaid";
    final String COLUMN_USERID = "userid";
    final String COLUMN_KOMPLECTID = "komplectid";
    final String COLUMN_PRICE = "price";
    final String COLUMN_DATE = "date";

    public ZakupkaStorage (Context context)
    {
        sqlHelper = new DBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public ZakupkaStorage open()
    {
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public ArrayList<Zakupka> getFullList(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_USERID + " = " + model.userid, null);
        ArrayList<Zakupka> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Zakupka obj = new Zakupka();
            obj.zakupkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            obj.price = cursor.getInt((int) cursor.getColumnIndex(COLUMN_PRICE));
            obj.date = cursor.getString((int) cursor.getColumnIndex(COLUMN_DATE));
            list.add(obj);

        }
        while (cursor.moveToNext());
        return list;
    }

    public List<Zakupka> getFilteredList(User model, Komplect model2) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_USERID + " = " + model.userid + " where " + COLUMN_KOMPLECTID + " = " + model2.komplectid, null);
        List<Zakupka> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Zakupka obj = new Zakupka();
            obj.zakupkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            obj.price = cursor.getInt((int) cursor.getColumnIndex(COLUMN_PRICE));
            obj.date = cursor.getString((int) cursor.getColumnIndex(COLUMN_DATE));
            list.add(obj);
            cursor.moveToNext();
        }
        while (!cursor.isAfterLast());
        return list;
    }
    public Zakupka getZakupkaById(int Id)
    {
        Cursor cursor = db.rawQuery( "Select * from Zakupka where zakupkaid = " + Id , null);
        if (!cursor.moveToFirst())
        {
            return null;
        }
        do {
            Zakupka zakupka = new Zakupka();
            zakupka.zakupkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            zakupka.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            zakupka.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            zakupka.price = cursor.getInt((int) cursor.getColumnIndex(COLUMN_PRICE));
            zakupka.date = cursor.getString((int) cursor.getColumnIndex(COLUMN_DATE));
            return zakupka;
        }
        while
        (
                cursor.moveToNext()
        );
    }
    /*public Zakupka getZakupka(User model, Komplect model2) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_ID + " = " + model.userid + " where " + COLUMN_USERID + " = " + model2.komplectid, null);
        Komplect obj = new Komplect();
        if (!cursor.moveToFirst()) {
            return null;
        }
        obj.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
        obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
        obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
        return obj;
    }*/

    public void create(User model, Zakupka model2)
    {
        ContentValues content = new ContentValues();
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_KOMPLECTID, model2.komplectid);
        content.put(COLUMN_PRICE, model2.price);
        content.put(COLUMN_DATE, model2.date);

        db.insert(TABLE, null, content);
    }

    public void update(User model, Zakupka model2) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_ID, model2.zakupkaid);
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_KOMPLECTID, model2.komplectid);
        content.put(COLUMN_PRICE, model2.price);
        content.put(COLUMN_DATE, model2.date);

        String where = COLUMN_ID + " = " + model2.zakupkaid;
        db.update(TABLE, content, where, null);
    }

    public void delete(Zakupka model2) {
        String where = COLUMN_ID + " = " + model2.zakupkaid;
        db.delete(TABLE, where, null);
    }
    public void deleteById(Komplect model)
    {
        Cursor cursor = db.rawQuery( "Select * from Zakupka where komplectid = " + model.komplectid , null);
        if (cursor.moveToFirst()) {


            do {
                delete(getZakupkaById(cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID))));
            }
            while
            (
                    cursor.moveToNext()
            );
        }
    }
    public void clear() {
        db.delete(TABLE, null, null);
    }

    public int getElementCount() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE);
    }
}
