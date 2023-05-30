package com.example.myapplication.Database.Models;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.io.Serializable;


public class User implements Serializable
{
    public int userid;
    public String login;
    public String password;
    public String email;

    public User(){}

    /*public void setUserid(int userid) {this.userid = userid;}
    public int getUserId() {return userid;}*/

    public void setLogin(String login){this.login = login;}
    public String getLogin(){return login;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return password;}

    public void setEmail(String email){this.email = email;}
    public String getEmail(){return email;}

    public int getUserid(){return userid;}
    public void setUserid(int userid){this.userid = userid;}
}
