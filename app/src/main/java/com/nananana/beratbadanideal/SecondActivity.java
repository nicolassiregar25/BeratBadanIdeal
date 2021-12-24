package com.nananana.beratbadanideal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    TextView databbi, counter;
    int stopcounter=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        databbi = findViewById(R.id.databbi);
        databbi.setText(getIntent().getExtras().getString("data"));
        counter=findViewById(R.id.counter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, "Kembali ke insert", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Mohon kembali", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Selamat datang di tampil data", Toast.LENGTH_LONG).show();
        counter.setText(String.valueOf(stopcounter));
    }

    @Override
    protected void onStop() {
        stopcounter++;
        super.onStop();
    }
}