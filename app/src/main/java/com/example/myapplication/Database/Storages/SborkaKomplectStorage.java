package com.example.myapplication.Database.Storages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.Sborka;
import com.example.myapplication.Database.Models.SborkaKomplect;

import java.util.ArrayList;

public class SborkaKomplectStorage
{
    DBHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "SborkaKomplect";
    final String COLUMN_ID = "sborkakomplectid";
    final String COLUMN_SBORKAID = "sborkaid";
    final String COLUMN_KOMPLECTID = "komplectid";
    public SborkaKomplectStorage (Context context)
    {
        sqlHelper = new DBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public SborkaKomplectStorage open()
    {
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public ArrayList<SborkaKomplect> getListByKomplectId(int Id)
    {
        ArrayList<SborkaKomplect> sborkaKomplects = new ArrayList<>();
        Cursor cursor = db.rawQuery( "Select * from SborkaKomplect where komplectid = " + Id , null);
        if (!cursor.moveToFirst())
        {
            return sborkaKomplects;
        }
        do {
            SborkaKomplect sborkaKomplect = new SborkaKomplect();
            sborkaKomplect.sborkakomplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            sborkaKomplect.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            sborkaKomplect.sborkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_SBORKAID));
            sborkaKomplects.add(sborkaKomplect);
        }
        while
        (
                cursor.moveToNext()
        );
        return  sborkaKomplects;
    }
    public ArrayList<SborkaKomplect> getListBySborkaId(int Id)
    {
        ArrayList<SborkaKomplect> sborkaKomplects = new ArrayList<>();
        Cursor cursor = db.rawQuery( "Select * from SborkaKomplect where sborkaid = " + Id , null);
        if (!cursor.moveToFirst())
        {
            return sborkaKomplects;
        }
        do {
            SborkaKomplect sborkaKomplect = new SborkaKomplect();
            sborkaKomplect.sborkakomplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            sborkaKomplect.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            sborkaKomplect.sborkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_SBORKAID));
            sborkaKomplects.add(sborkaKomplect);
        }
        while
        (
                cursor.moveToNext()
        );
        return  sborkaKomplects;
    }

    public SborkaKomplect getSborkaKomplectById(int Id)
    {
        Cursor cursor = db.rawQuery( "Select * from SborkaKomplect where sborkakomplectid = " + Id , null);
        if (!cursor.moveToFirst())
        {
            return null;
        }
        do {
            SborkaKomplect sborkaKomplect = new SborkaKomplect();
            sborkaKomplect.sborkakomplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            sborkaKomplect.komplectid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_KOMPLECTID));
            sborkaKomplect.sborkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_SBORKAID));
            return sborkaKomplect;
        }
        while
        (
                cursor.moveToNext()
        );
    }

    public void create(SborkaKomplect model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_SBORKAID, model.sborkaid);
        content.put(COLUMN_KOMPLECTID, model.komplectid);
        db.insert(TABLE, null, content);
    }

    public void update(SborkaKomplect model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_ID, model.sborkakomplectid);
        content.put(COLUMN_SBORKAID, model.sborkaid);
        content.put(COLUMN_KOMPLECTID, model.komplectid);
        String where = COLUMN_ID + " = " + model.sborkakomplectid;
        db.update(TABLE, content, where, null);
    }

    public void delete(SborkaKomplect model2) {
        String where = COLUMN_ID + " = " + model2.sborkakomplectid;
        db.delete(TABLE, where, null);
    }
    public void deleteById(Sborka model)
    {
        Cursor cursor = db.rawQuery( "Select * from SborkaKomplect where sborkaid = " + model.sborkaid , null);
        if (cursor.moveToFirst()) {


            do {
                delete(getSborkaKomplectById(cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID))));
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
}
