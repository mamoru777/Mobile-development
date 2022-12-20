package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    public MyList staticList = new MyList();
    fragment_add frgadd;
    FragmentTransaction fTrans;
    public ArrayList<String> listItems = staticList.getList();
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         ListView lv = findViewById(R.id.listview);
         EditText ed = findViewById(R.id.edittext);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.forlist, listItems);
         lv.setAdapter(adapter);
         lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
         Button addb=findViewById(R.id.buttonadd);
         //SparseBooleanArray sbArray = lv.getCheckedItemPositions();
         addb.setOnClickListener(v ->
         {
             setContentView(R.layout.fragment_add);
             frgadd = new fragment_add();
             //fTrans = getFragmentManager().beginTransaction();
             //fTrans.add(R.id.frgmCont, )
            //listItems.add(ed.getText().toString());
            //adapter.add(ed.getText().toString());
            //adapter.notifyDataSetChanged();
         });
         /*Button vid = findViewById(R.id.buttonvid);
         vid.setOnClickListener(v ->
         {
             SparseBooleanArray sbArray = lv.getCheckedItemPositions();
             for (int i = 0; i < sbArray.size(); i++)
             {
                 sbArray.setValueAt(i, true);
             }
             adapter.notifyDataSetChanged();
         });
         Button dellb=findViewById(R.id.buttonclear);
         dellb.setOnClickListener(v ->
         {
             SparseBooleanArray sbArray = lv.getCheckedItemPositions();
             for (int i = 0; i < sbArray.size(); i++)
             sbArray.setValueAt(i, false);
             adapter.notifyDataSetChanged();
             //lv.clearFocus();
         });*/
         /*Button toastb=findViewById(R.id.buttontoast);
         toastb.setOnClickListener(v ->
         {
             SparseBooleanArray sbArray = lv.getCheckedItemPositions();
             String temp = "";
             for (int i = 0; i < sbArray.size(); i++){
                 int key = sbArray.keyAt(i);
                 if(sbArray.get(key)){
                     temp += listItems.get(key) + "\n";
                 }
             }
             Toast.makeText(this, temp, Toast.LENGTH_LONG).show();
         });*/
         Button searchb = findViewById(R.id.buttonpoisk);
         searchb.setOnClickListener(v ->
         {
              Intent i = new Intent(this ,activity_login.class);
              //i.putExtra();
              startActivity(i);
         });
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listItems = savedInstanceState.getStringArrayList("listItems");
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("listItems", listItems);
    }

}