package com.example.myapplication.Database.Models;

import java.io.Serializable;

public class Komplect implements Serializable
{
    public int komplectid;
    public int userid;
    public String name;

    public Komplect(){}

    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public void setKomplectid(int komplectid){this.komplectid = komplectid;}
    public int getKomplectid(){return komplectid;}

    public void setUserid(int userid){this.userid = userid;}
    @Override
    public String toString() {
        return "Комплектующее {" + "Id = " + komplectid + "," + "Userid = " + userid  + ", Имя = '" + name + '\'' + "}";
    }
}
