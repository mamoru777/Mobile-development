package com.example.myapplication.Database.Storages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.Sborka;
import com.example.myapplication.Database.Models.SborkaKomplect;
import com.example.myapplication.Database.Models.User;

import java.util.ArrayList;

public class SborkaStorage
{
    DBHelper sqlHelper;
    SQLiteDatabase db;
    final String TABLE = "Sborka";
    final String COLUMN_ID = "sborkaid";
    final String COLUMN_USERID = "userid";
    final String COLUMN_NAME = "name";
    final String COLUMN_PRICE = "price";
    public static ArrayList<Sborka> sborkas = new ArrayList<>();

    //SborkaKomplectStorage sborkaKomplectStorage = new SborkaKomplectStorage(this);
    public SborkaStorage (Context context)
    {
        sqlHelper = new DBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public SborkaStorage open()
    {
        db = sqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public Sborka getById(int Id)
    {
        Cursor cursor = db.rawQuery("Select * from Sborka where sborkaid = " + Id , null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do
        {
            Sborka sborka = new Sborka();
            sborka.setSborkaid(cursor.getInt(cursor.getColumnIndex("sborkaid")));
            sborka.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            sborka.setName(cursor.getString(cursor.getColumnIndex("name")));
            sborka.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            return sborka;
        }
        while
        (
                cursor.moveToNext()
        );
    }

    public ArrayList<Sborka> getFullList(User model) {
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_USERID + " = " + model.userid, null);
        ArrayList<Sborka> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }
        do {
            Sborka obj = new Sborka();
            obj.sborkaid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            obj.userid = cursor.getInt((int) cursor.getColumnIndex(COLUMN_USERID));
            obj.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            obj.price = cursor.getInt((int) cursor.getColumnIndex(COLUMN_PRICE));
            list.add(obj);

        }
        while (cursor.moveToNext());
        return list;
    }
    public int getId(Sborka model)
    {
        int id;
        Cursor cursor = db.rawQuery("select * from " + TABLE + " where " + COLUMN_NAME + " = '" + model.name + "' AND " + COLUMN_PRICE + " = " + model.price, null);
        if (!cursor.moveToFirst()) {
            return 0;
        }
        do {
            id = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            return id;
        }
        while (cursor.moveToNext());
    }
    public void create(User model, Sborka model2) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_NAME, model2.name);
        content.put(COLUMN_PRICE, model2.price);
        db.insert(TABLE, null, content);
    }

    public void update(Sborka model) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_ID, model.sborkaid);
        content.put(COLUMN_USERID, model.userid);
        content.put(COLUMN_NAME, model.name);
        content.put(COLUMN_PRICE, model.price);
        String where = COLUMN_ID + " = " + model.sborkaid;
        db.update(TABLE, content, where, null);
    }

    public void delete(Sborka model) {
        String where = COLUMN_ID + " = " + model.sborkaid;
        db.delete(TABLE, where, null);
    }

    public void deleteById(int Id, ArrayList<SborkaKomplect> sborkaKomplects)
    {
        for (int i = 0; i < sborkaKomplects.size(); i++)
        {
            delete(getById(sborkaKomplects.get(i).getSborkaid()));
        }
    }
    public Sborka getByName(User model, String name)
    {
        db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Sborka where name = '" + name + "'"+" AND userid = "+ model.userid , null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do
        {
            Sborka sborka = new Sborka();
            sborka.setSborkaid(cursor.getInt(cursor.getColumnIndex("sborkaid")));
            sborka.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            sborka.setName(cursor.getString(cursor.getColumnIndex("name")));
            sborka.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            return sborka;
        }
        while
        (
                cursor.moveToNext()
        );
    }
    public void clear() {
        db.delete(TABLE, null, null);
    }
}
