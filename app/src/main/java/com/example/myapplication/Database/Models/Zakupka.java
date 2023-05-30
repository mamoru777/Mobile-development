package com.example.myapplication.Database.Models;

import java.util.Date;

public class Zakupka
{
    public int zakupkaid;
    public int komplectid;
    public int userid;
    public int price;
    public String date;

    public Zakupka(){}

    public void setPrice(int price){this.price = price;}
    public int getPrice(){return price;}

    public void setDate(String date){this.date = date;}
    public String getDate(){return date;}

    public void setUserid(int userid){this.userid = userid;}
    public void setKomplectid(int komplectid){this.komplectid = komplectid;}

    //public String getKomplectName(int komplectid){return}
    @Override
    public String toString() {
        return "Закупка {" + "Id = " + zakupkaid + "," + "Userid = " + userid  + ", Цена = " + price + ", Дата = " + date + ", Комплектующее Id = " + komplectid +"}";
    }
}
