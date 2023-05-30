package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Storages.KomplectStorage;
import com.example.myapplication.Database.Models.Sborka;
import com.example.myapplication.Database.Models.SborkaKomplect;
import com.example.myapplication.Database.Storages.SborkaKomplectStorage;
import com.example.myapplication.Database.Storages.SborkaStorage;

import java.util.ArrayList;

public class activity_sborka_info extends AppCompatActivity {
    public static ArrayList<Komplect> komplects;
    public static ArrayList<SborkaKomplect> sborkaKomplects;
    private ArrayAdapter<Komplect> adapterKom;
    KomplectStorage komplectStorage;
    SborkaStorage sborkaStorage;
    Sborka sborka;
    SborkaKomplectStorage sborkaKomplectStorage;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sborka_info);
        loadData();
    }
    public void loadData()
    {
        komplects = new ArrayList<>();
        adapterKom = new ArrayAdapter<>(this, R.layout.forlist, komplects);
        listView = findViewById(R.id.listKomSborkInfo);
        listView.setAdapter(adapterKom);
        komplectStorage = new KomplectStorage(this);
        sborkaKomplectStorage = new SborkaKomplectStorage(this);
        sborka = (Sborka) getIntent().getSerializableExtra("sborka");
        sborkaKomplects = sborkaKomplectStorage.getListBySborkaId(sborka.getSborkaId());
        for (int i = 0; i < sborkaKomplects.size(); i++)
        {
            komplects.add(komplectStorage.getById(sborkaKomplects.get(i).getKomplectid()));
        }
        adapterKom.notifyDataSetChanged();
    }
}