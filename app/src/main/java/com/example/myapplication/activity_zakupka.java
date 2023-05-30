package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Storages.KomplectStorage;
import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Storages.UserStorage;
import com.example.myapplication.Database.Models.Zakupka;
import com.example.myapplication.Database.Storages.ZakupkaStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class activity_zakupka extends AppCompatActivity {
    public static ArrayList<Komplect> komplects;
    public static ArrayList<Zakupka> zakupkas;
    private ArrayAdapter<Komplect> adapterKom;
    private ArrayAdapter<Zakupka> adapterZak;
    User user;
    Komplect komplect;
    Button buttonCreate;
    Button buttonUpdate;
    Button buttonDelete;
    UserStorage userStorage;
    KomplectStorage komplectStorage;
    ZakupkaStorage zakupkaStorage;
    EditText editTextPrice;
    Spinner spinner;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakupka);
        loadData();
    }
    public void loadData()
    {
        user = (User) getIntent().getSerializableExtra("user");
        userStorage = new UserStorage(this);
        komplectStorage = new KomplectStorage(this);
        zakupkaStorage = new ZakupkaStorage(this);
        listview = findViewById(R.id.listZak);
        spinner = findViewById(R.id.spinnerZak);
        komplects = komplectStorage.getFullList(user);
        zakupkas = zakupkaStorage.getFullList(user);
        adapterKom = new ArrayAdapter<>(this, R.layout.forlist, komplects);
        adapterZak = new ArrayAdapter<>(this, R.layout.forlist, zakupkas);
        spinner.setAdapter(adapterKom);
        listview.setAdapter(adapterZak);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapterZak.notifyDataSetChanged();
        adapterKom.notifyDataSetChanged();
        buttonCreate = findViewById(R.id.buttonCreateZak);
        buttonUpdate = findViewById(R.id.buttonUpdateZak);
        buttonDelete = findViewById(R.id.buttonDeleteZak);
        editTextPrice = findViewById(R.id.editTextPriceZak);
        buttonCreate.setOnClickListener(v ->
        {


                if (!editTextPrice.getText().toString().equals(""))
                {
                    Zakupka zakupka = new Zakupka();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        zakupka.setDate(LocalDateTime.now().toString()/*editTextDate.getText().toString()*/);
                    }
                    int price = Integer.parseInt(editTextPrice.getText().toString());
                    zakupka.setPrice(price);
                    zakupka.setUserid(user.getUserid());
                    komplect = adapterKom.getItem(spinner.getSelectedItemPosition());
                    zakupka.setKomplectid(komplect.getKomplectid());
                    zakupkaStorage.create(user, zakupka);
                    zakupkas = zakupkaStorage.getFullList(user);
                    adapterZak = new ArrayAdapter<>(this, R.layout.forlist, zakupkas);
                    listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    listview.setAdapter(adapterZak);
                }
                else
                {
                    Toast.makeText(activity_zakupka.this, "Введите цену", Toast.LENGTH_LONG).show();
                }


        });
        buttonUpdate.setOnClickListener(v ->
        {

                if (!editTextPrice.getText().toString().equals(""))
                {
                    SparseBooleanArray sparseBooleanArray = listview.getCheckedItemPositions();
                    if (sparseBooleanArray.size() > 0)
                    {
                        Zakupka zakupka = new Zakupka();

                        for (int i = 0; i < listview.getCount(); i++) {
                            if (sparseBooleanArray.get(i)) {
                                //komplectName = adapter.getItem(i).name;
                                zakupka = adapterZak.getItem(i);
                            }
                        }
                        int price = Integer.parseInt(editTextPrice.getText().toString());
                        komplect = adapterKom.getItem(spinner.getSelectedItemPosition());
                        zakupka.setKomplectid(komplect.getKomplectid());
                        zakupka.setPrice(price);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            zakupka.setDate(LocalDateTime.now().toString()/*editTextDate.getText().toString()*/);
                        }
                        zakupkaStorage.update(user, zakupka);
                        adapterZak.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(activity_zakupka.this, "Выберите закупку для изменения", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(activity_zakupka.this, "Введите цену", Toast.LENGTH_LONG).show();
                }

        });
        buttonDelete.setOnClickListener(v ->
        {
            SparseBooleanArray parsedList = listview.getCheckedItemPositions();
            if (parsedList.size() > 0)
            {
                for (int i = 0; i < listview.getCount(); i++) {
                    if (parsedList.get(i)) {
                        //komplectName = adapter.getItem(i).name;
                        zakupkaStorage.delete(adapterZak.getItem(i));
                        zakupkas.remove(i);
                    }
                }
                for (int i = 0; i < listview.getCount(); i++) {
                    listview.setItemChecked(i, false);
                }
                adapterZak.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(activity_zakupka.this, "Выберите закупку для удаления", Toast.LENGTH_LONG).show();
            }
        });
    }
}