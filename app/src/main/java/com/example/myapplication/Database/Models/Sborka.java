package com.example.myapplication.Database.Models;

import java.io.Serializable;

public class Sborka implements Serializable
{
    public int sborkaid;
    public int userid;
    public String name;
    public int price;

    public Sborka(){}

    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public void setPrice(int price){this.price = price;}
    public int getPrice(){return price;}

    public void setUserid(int userid){this.userid = userid;}

    public int getSborkaId(){return sborkaid;}
    public void setSborkaid(int sborkaid){this.sborkaid = sborkaid;}
    @Override
    public String toString() {
        return "Сборка {" + "Id = " + sborkaid + "," + "Userid = " + userid  + ", Имя = " + name + ", Цена = " + price + "}";
    }
}
