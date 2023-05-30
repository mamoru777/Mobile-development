package com.example.myapplication.Database.Models;

public class SborkaKomplect
{
    public int sborkakomplectid;
    public int sborkaid;
    public int komplectid;

    public void setKomplectid(int komplectid){this.komplectid = komplectid;}
    public int getKomplectid(){return komplectid;}

    public void setSborkaid(int sborkaid){this.sborkaid = sborkaid;}
    public int getSborkaid(){return sborkaid;}
}
