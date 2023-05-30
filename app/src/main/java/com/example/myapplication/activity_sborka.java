package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Storages.KomplectStorage;
import com.example.myapplication.Database.Models.Sborka;
import com.example.myapplication.Database.Models.SborkaKomplect;
import com.example.myapplication.Database.Storages.SborkaKomplectStorage;
import com.example.myapplication.Database.Storages.SborkaStorage;
import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Storages.UserStorage;

import java.util.ArrayList;

public class activity_sborka extends AppCompatActivity {
    public static ArrayList<Komplect> komplects;
    public static ArrayList<Sborka> sborkas;
    private ArrayAdapter<Komplect> adapterKom;
    private ArrayAdapter<Sborka> adapterSbork;
    User user;
    Komplect komplect;
    Button buttonCreate;
    Button buttonUpdate;
    Button buttonDelete;
    Button buttonInfo;
    UserStorage userStorage;
    KomplectStorage komplectStorage;
    SborkaStorage sborkaStorage;
    SborkaKomplectStorage sborkaKomplectStorage;
    EditText editTextName;
    EditText editTextPrice;
    ListView listViewKom;
    ListView listViewSbork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sborka);
        loadData();
    }
    public void loadData()
    {
        user = (User) getIntent().getSerializableExtra("user");
        buttonCreate = findViewById(R.id.buttonCreateSbork);
        buttonUpdate = findViewById(R.id.buttonUpdateSbork);
        buttonDelete = findViewById(R.id.buttonDeleteSbork);
        buttonInfo = findViewById(R.id.buttonInfoSbork);
        listViewKom = findViewById(R.id.listSborkKom);
        listViewSbork = findViewById(R.id.listSborkSbork);
        editTextName = findViewById(R.id.editTextNameSbork);
        editTextPrice = findViewById(R.id.editTextPriceSbork);
        userStorage = new UserStorage(this);
        komplectStorage = new KomplectStorage(this);
        sborkaStorage = new SborkaStorage(this);
        sborkaKomplectStorage = new SborkaKomplectStorage(this);
        komplects = komplectStorage.getFullList(user);
        sborkas = sborkaStorage.getFullList(user);
        adapterKom = new ArrayAdapter<>(this, R.layout.forlist, komplects);
        adapterSbork = new ArrayAdapter<>(this, R.layout.forlist, sborkas);
        listViewKom.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewSbork.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewKom.setAdapter(adapterKom);
        listViewSbork.setAdapter(adapterSbork);
        buttonCreate.setOnClickListener(v ->
        {
            if (!editTextName.getText().toString().equals(""))
            {
                if (!editTextPrice.getText().toString().equals(""))
                {
                    if (sborkaStorage.getByName(user, editTextName.getText().toString()) == null)
                    {
                        Sborka sborka = new Sborka();
                        sborka.setName(editTextName.getText().toString());
                        int price = Integer.parseInt(editTextPrice.getText().toString());
                        sborka.setPrice(price);
                        sborka.setUserid(user.getUserid());
                        sborkaStorage.create(user, sborka);
                        SparseBooleanArray sparseBooleanArray = listViewKom.getCheckedItemPositions();
                        for (int i = 0; i < listViewKom.getCount(); i++) {
                            if (sparseBooleanArray.get(i))
                            {
                                SborkaKomplect sborkaKomplect = new SborkaKomplect();
                                sborkaKomplect.setKomplectid(adapterKom.getItem(i).getKomplectid());
                                sborkaKomplect.setSborkaid(sborkaStorage.getId(sborka));
                                sborkaKomplectStorage.create(sborkaKomplect);
                            }
                        }
                        sborkas = sborkaStorage.getFullList(user);
                        adapterSbork = new ArrayAdapter<>(this, R.layout.forlist, sborkas);
                        listViewSbork.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listViewSbork.setAdapter(adapterSbork);
                    }
                    else
                    {
                        Toast.makeText(activity_sborka.this, "Уже есть сборка с таким именем", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(activity_sborka.this, "Введите цену", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(activity_sborka.this, "Введите имя", Toast.LENGTH_LONG).show();
            }
        });
        buttonUpdate.setOnClickListener(v ->
        {
            if (!editTextName.getText().toString().equals(""))
            {
                if (!editTextPrice.getText().toString().equals(""))
                {
                    if (sborkaStorage.getByName(user, editTextName.getText().toString()) == null)
                    {
                        SparseBooleanArray sparseBooleanArray = listViewSbork.getCheckedItemPositions();
                        SparseBooleanArray sparseBooleanArray2 = listViewKom.getCheckedItemPositions();
                        if (sparseBooleanArray.size() > 0)
                        {
                            if (sparseBooleanArray.size() > 0)
                            {
                                Sborka sborka = new Sborka();
                                for (int i = 0; i < listViewSbork.getCount(); i++) {
                                    if (sparseBooleanArray.get(i)) {
                                        //komplectName = adapter.getItem(i).name;
                                        sborka = adapterSbork.getItem(i);
                                    }
                                }
                                sborkaKomplectStorage.deleteById(sborka);
                                for (int i = 0; i < listViewKom.getCount(); i++) {
                                    if (sparseBooleanArray2.get(i))
                                    {
                                        SborkaKomplect sborkaKomplect = new SborkaKomplect();
                                        sborkaKomplect.setKomplectid(adapterKom.getItem(i).getKomplectid());
                                        sborkaKomplect.setSborkaid(sborka.getSborkaId());
                                        sborkaKomplectStorage.create(sborkaKomplect);
                                    }
                                }
                                int price = Integer.parseInt(editTextPrice.getText().toString());
                                sborka.setName(editTextName.getText().toString());
                                sborka.setPrice(price);
                                sborkaStorage.update(sborka);
                                adapterSbork.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(activity_sborka.this, "Выберите комплектующие для изменения сборки", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(activity_sborka.this, "Выберите сборку для изменения", Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(activity_sborka.this, "Уже есть сборка с таким именем", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(activity_sborka.this, "Введите цену", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(activity_sborka.this, "Введите имя", Toast.LENGTH_LONG).show();
            }


        });
        buttonDelete.setOnClickListener(v ->
        {
            SparseBooleanArray parsedList = listViewSbork.getCheckedItemPositions();
            if (parsedList.size() > 0)
            {
                for (int i = 0; i < listViewSbork.getCount(); i++) {
                    if (parsedList.get(i)) {
                        sborkaKomplectStorage.deleteById(adapterSbork.getItem(i));
                        sborkaStorage.delete(adapterSbork.getItem(i));
                        sborkas.remove(i);
                    }
                }
                for (int i = 0; i < listViewSbork.getCount(); i++) {
                    listViewSbork.setItemChecked(i, false);
                }
                adapterSbork.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(activity_sborka.this, "Выберите сборку для удаления", Toast.LENGTH_LONG).show();
            }
        });
        buttonInfo.setOnClickListener(v ->
        {
            SparseBooleanArray sparseBooleanArray = listViewSbork.getCheckedItemPositions();
            if (sparseBooleanArray.size() > 0)
            {
                Sborka sborka = new Sborka();
                for (int i = 0; i < listViewSbork.getCount(); i++) {
                    if (sparseBooleanArray.get(i)) {
                        sborka = adapterSbork.getItem(i);
                    }
                }
                Intent intent = new Intent(activity_sborka.this, activity_sborka_info.class);
                intent.putExtra("sborka", sborka);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(activity_sborka.this, "Выберите сборку для просмотра", Toast.LENGTH_LONG).show();
            }
        });
    }
}