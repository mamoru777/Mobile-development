package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Models.Komplect;
import com.example.myapplication.Database.Storages.KomplectStorage;
import com.example.myapplication.Database.Models.User;


public class UpdateKomplectFragment extends DialogFragment {
    KomplectStorage komplectStorage;
    Komplect komplect;
    User user;
    EditText editTextKomplectName;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_komplect, null);
        komplectStorage = new KomplectStorage(getContext());
        Bundle bundle = this.getArguments();
        komplect = (Komplect) bundle.getSerializable("komplect");
        user = (User) bundle.getSerializable("user");
        if (komplect != null)
        {
            Toast.makeText(getContext(), komplect.getName(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getContext(), "Нет комплектующего", Toast.LENGTH_LONG).show();
        }
        //user = (User) bundle.getSerializable("user");
        //Toast.makeText(getContext(), user.getLogin(), Toast.LENGTH_LONG).show();
        getDialog().setTitle("Update komplect");
        Button buttonSuccess = v.findViewById(R.id.buttonSuccess);
        editTextKomplectName = v.findViewById(R.id.editTextKomplectNameFragment);
        //TextView textViewName =  v.findViewById(R.id.editTextKomplectNameFragment);
        //textViewName.setText(workerName);

        buttonSuccess.setOnClickListener(u -> {
            //resultWorkerName = textViewName.getText().toString();
            //resultWorkerAge = workerAge;
            if (!editTextKomplectName.getText().toString().equals(""))
            {
                if (komplectStorage.getByName(user, editTextKomplectName.getText().toString()) == null)
                {
                    komplect.setName(editTextKomplectName.getText().toString());
                    komplectStorage.update(komplect);
                    dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), "Уже есть комплектующее с таким именем", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Введите новое имя", Toast.LENGTH_LONG).show();
            }

        });
        return v;
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}