package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Database.Models.User;
import com.example.myapplication.Database.Models.Zakupka;
import com.example.myapplication.Database.Storages.UserStorage;
import com.example.myapplication.Database.Storages.ZakupkaStorage;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ArrayList<Zakupka> zakupkas;
    int userid;
    User user;
    Button buttonKomplect;
    Button buttonSborka;
    Button buttonZakupka;
    Button buttonClear;
    Button buttonOtchet;
    ListView listView;
    UserStorage userStorage;
    ZakupkaStorage zakupkaStorage;
    String login;
    DBHelper dbHelper;
    public MyList staticList = new MyList();
    public ArrayList<String> listItems = staticList.getList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }
    public void loadData()
    {
        dbHelper = new DBHelper(this);
        user = (User) getIntent().getSerializableExtra("user");
        buttonKomplect = findViewById(R.id.buttonkomplect);
        buttonSborka = findViewById(R.id.buttonsborka);
        buttonZakupka = findViewById(R.id.buttonzakupka);
        buttonClear = findViewById(R.id.buttonclear);
        buttonOtchet = findViewById(R.id.buttonothect);
        userStorage = new UserStorage(getBaseContext());
        zakupkaStorage = new ZakupkaStorage(getBaseContext());
        zakupkas = zakupkaStorage.getFullList(user);
        buttonKomplect.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, activity_komplect.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        buttonSborka.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, activity_sborka.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        buttonZakupka.setOnClickListener(v ->
        {
            //dbHelper.delete(this);
            Intent intent = new Intent(MainActivity.this, activity_zakupka.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        buttonClear.setOnClickListener(v ->
        {
            dbHelper.delete(this);
        });
        buttonOtchet.setOnClickListener(v ->
        {
            zakupkas = zakupkaStorage.getFullList(user);
            try {
                createPDF(zakupkas, "Pdf");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
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
    public void createPDF(java.util.List<Zakupka> receivingList, String name) throws IOException, DocumentException {
        Document document=new Document();  // create the document
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists()) {
            root.mkdirs();   // create root directory in sdcard
        }
        File file = new File("/storage/emulated/0/Download/PDF/"+ name +".pdf");
        PdfWriter.getInstance(document,new FileOutputStream(file));
        document.open();  // open the directory

        java.util.List<Paragraph> paragraphs = new ArrayList<>();
        final String FONT = "/assets/font/arialmt.ttf";

        BaseFont bf=BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font=new Font(bf,14,Font.NORMAL);


        for(Zakupka receiving:receivingList){
            Paragraph p1 = new Paragraph();
            p1.setFont(font);
            p1.add(receiving.toString());
            //p1.add(receiving.getDeliveryName()+"\n"+receiving.getPrice()+"\n"+receiving.getDateDispatch());
            paragraphs.add(p1);

        }
        Paragraph p2 = new Paragraph();
        p2.setFont(font);
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        p2.add("zakupkas");
        document.add(p2);
        for(Paragraph paragraph:paragraphs){
            document.add(paragraph);
        }
        document.addCreationDate();
        document.close();
    }

}