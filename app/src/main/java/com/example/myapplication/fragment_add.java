package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class fragment_add extends Fragment {

    public MyList list = MainActivity.MyList;
    public ArrayList<String> listItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add, container, false);
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Button addb = view.findViewById(R.id.buttonadd);
        EditText ed = view.findViewById(R.id.edittext);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.forlist, listItems);
        addb.setOnClickListener(v ->
        {
            //fTrans = getFragmentManager().beginTransaction();
            //fTrans.add(R.id.frgmCont, )
            listItems.add(ed.getText().toString());
            //adapter.add(ed.getText().toString());
            //adapter.notifyDataSetChanged();
        });
    }
}