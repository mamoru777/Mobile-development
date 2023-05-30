package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Storages.UserStorage;

public class activity_registration extends AppCompatActivity {
    UserStorage userStorage;
    DBHelper dbHelper;
    Button buttonRegistr;
    EditText EditTextLogin;
    EditText EditTextPassword;
    EditText EditTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loadData();
    }
    public void loadData()
    {
        dbHelper = new DBHelper(getBaseContext());
        userStorage = new UserStorage(getBaseContext());
        buttonRegistr = findViewById(R.id.buttonRegistrReg);
        EditTextLogin = findViewById(R.id.editTextLoginReg);
        EditTextPassword = findViewById(R.id.editTextPasswordReg);
        EditTextEmail = findViewById(R.id.editTextEmailReg);
        buttonRegistr.setOnClickListener(v ->
        {

            if (userStorage.getUserByLogin(EditTextLogin.getText().toString()) == null)
            {
                User user = new User();
                user.setLogin(EditTextLogin.getText().toString());
                user.setPassword(EditTextPassword.getText().toString());
                user.setEmail(EditTextEmail.getText().toString());
                userStorage.create(user);
                Intent intent = new Intent(activity_registration.this, activity_login.class);
                startActivity(intent);
            }
           else
            {
                Toast.makeText(activity_registration.this, "Уже есть пользователь с таким логином", Toast.LENGTH_LONG).show();
            }
        });

    }
}