package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.myapplication.Database.Storages.ZakupkaStorage;

import java.util.ArrayList;

public class activity_komplect extends AppCompatActivity {

    UpdateKomplectFragment updateKomplectFragment;
    public static ArrayList<Komplect> komplects;
    public static ArrayList<SborkaKomplect> sborkaKomplects;
    public static ArrayList<Sborka> sborkas;
    private ArrayAdapter<Komplect> adapter;
    User user;
    Button buttonCreate;
    Button buttonUpdate;
    Button buttonDelete;
    ListView listView;
    UserStorage userStorage;
    KomplectStorage komplectStorage;
    ZakupkaStorage zakupkaStorage;
    SborkaStorage sborkaStorage;
    SborkaKomplectStorage sborkaKomplectStorage;
    EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komplect);
        loadData();
    }
    public void loadData()
    {

        //dbHelper = new DBHelper(this);
        sborkaKomplectStorage = new SborkaKomplectStorage(this);
        sborkaStorage = new SborkaStorage(this);
        zakupkaStorage = new ZakupkaStorage(this);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listView = findViewById(R.id.list);
        editTextName = findViewById(R.id.nameText);
        userStorage = new UserStorage(getBaseContext());
        komplectStorage = new KomplectStorage(getBaseContext());
        //user = (User) getIntent().getSerializableExtra("user");
        //login = getIntent().getStringExtra("login");
        user = (User) getIntent().getSerializableExtra("user");
        sborkaKomplects = new ArrayList<SborkaKomplect>();
        sborkas = new ArrayList<Sborka>();
        komplects = new ArrayList<Komplect>();
        komplects = komplectStorage.getFullList(user);
        adapter = new ArrayAdapter<>(this, R.layout.forlist, komplects);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_LONG).show();
        //komplects = komplectStorage.getFullList(user);
        //adapter.notifyDataSetChanged();
        buttonCreate.setOnClickListener(v ->
        {
            if (!editTextName.getText().toString().equals(""))
            {

                if (komplectStorage.getByName(user, editTextName.getText().toString()) == null)
                {
                    Komplect komplect = new Komplect();
                    komplect.setName(editTextName.getText().toString());
                    komplect.setUserid(user.getUserid());
                    komplectStorage.create(user, komplect);
                    komplects = komplectStorage.getFullList(user);
                    adapter = new ArrayAdapter<>(this, R.layout.forlist, komplects);
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    listView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(activity_komplect.this, "Уже есть комплектующее с таким именем", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(activity_komplect.this, "Введите имя комплектующего", Toast.LENGTH_LONG).show();
            }
        });
        buttonUpdate.setOnClickListener(v ->
        {
            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            if (sparseBooleanArray.size() > 0)
            {
                updateKomplectFragment = new UpdateKomplectFragment();
                Komplect komplect = new Komplect();
                Bundle bundle = new Bundle();
                for (int i = 0; i < listView.getCount(); i++) {
                    if (sparseBooleanArray.get(i)) {
                        komplect = adapter.getItem(i);
                    }
                }
                bundle.putSerializable("user", user);
                bundle.putSerializable("komplect", komplect);
                adapter.notifyDataSetChanged();
                updateKomplectFragment.setArguments(bundle);
                updateKomplectFragment.show(getSupportFragmentManager(), "updateKomplect");
            }
            else
            {
                Toast.makeText(activity_komplect.this, "Выберите комплектующее для изменения", Toast.LENGTH_LONG).show();
            }
        });
        buttonDelete.setOnClickListener(v ->
        {
            SparseBooleanArray parsedList = listView.getCheckedItemPositions();
            if (parsedList.size() > 0)
            {
                for (int i = 0; i < listView.getCount(); i++) {
                    if (parsedList.get(i)) {
                        sborkaKomplects = sborkaKomplectStorage.getListByKomplectId(adapter.getItem(i).getKomplectid());
                        for (int j = 0; j < sborkaKomplects.size(); j++)
                        {
                            sborkaKomplectStorage.deleteById(sborkaStorage.getById(sborkaKomplects.get(j).getSborkaid()));
                        }
                        sborkaStorage.deleteById(adapter.getItem(i).getKomplectid(), sborkaKomplects);
                        zakupkaStorage.deleteById(adapter.getItem(i));
                        komplectStorage.delete(adapter.getItem(i));
                        komplects.remove(i);
                    }
                }
                for (int i = 0; i < listView.getCount(); i++) {
                    listView.setItemChecked(i, false);
                }
                adapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(activity_komplect.this, "Выберите комплектующее для удаления", Toast.LENGTH_LONG).show();
            }

        });
    }
}