package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Storages.UserStorage;

public class activity_login extends AppCompatActivity {
    UserStorage userStorage;
    Button buttonLogin;
    Button buttonRegistr;

    EditText editTextPassword;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadData();
    }
    public void loadData()
    {
        userStorage = new UserStorage(getBaseContext());
        buttonLogin = findViewById(R.id.buttonLoginLog);
        buttonRegistr = findViewById(R.id.buttonRegistrLog);
        EditText editTextLogin = findViewById(R.id.editTextLoginLog);
        editTextPassword = findViewById(R.id.editTextPasswordLog);
        buttonLogin.setOnClickListener(v ->
        {
            if (!editTextLogin.getText().toString().equals(""))

            {
                if(!editTextPassword.getText().toString().equals(""))
                {
                    user = userStorage.getUserByFiltr(editTextLogin.getText().toString(), editTextPassword.getText().toString());
                    if (user != null)
                    {
                        Intent intent = new Intent(activity_login.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(activity_login.this, "Нет пользователя с таким логином и паролем", Toast.LENGTH_LONG).show();
                    }
                }

                else
                {
                    Toast.makeText(activity_login.this, "Введите пароль", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(activity_login.this, "Введите логин", Toast.LENGTH_LONG).show();
            }

        });
        buttonRegistr.setOnClickListener(v ->
        {
            Intent intent = new Intent(activity_login.this, activity_registration.class);
            startActivity(intent);
        });
    }
}