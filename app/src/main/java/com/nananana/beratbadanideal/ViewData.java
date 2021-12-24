package com.nananana.beratbadanideal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    private DBHelper MyDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList idList;
    private ArrayList jenisKelaminList;
    private ArrayList namaList;
    private ArrayList usiaList;
    private ArrayList beratBadanList;
    private ArrayList tinggiBadanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        idList = new ArrayList<>();
        jenisKelaminList = new ArrayList<>();
        namaList = new ArrayList<>();
        usiaList = new ArrayList<>();
        beratBadanList = new ArrayList<>();
        tinggiBadanList = new ArrayList<>();
        MyDatabase = new DBHelper(getBaseContext());
        recyclerView = findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(idList, jenisKelaminList, namaList, usiaList, beratBadanList, tinggiBadanList);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("Recycle")
    protected void getData() {
        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.getAllData();
        cursor.moveToFirst();

        for (int count = 0; count < cursor.getCount(); count++) {

            cursor.moveToPosition(count);
            idList.add(cursor.getInt(0));
            jenisKelaminList.add(cursor.getString(1));
            namaList.add(cursor.getString(2));
            usiaList.add(cursor.getInt(3));
            beratBadanList.add(cursor.getInt(4));
            tinggiBadanList.add(cursor.getInt(5));
        }
    }

    public void addData(View view) {
        Intent intent = new Intent(ViewData.this, MainActivity.class);
        startActivity(intent);
    }

}