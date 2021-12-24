package com.nananana.beratbadanideal;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int textSize = 100;
    TextView txtSeekBar;
    String jeniskelamin = "";
    EditText nama, usia, bb;
    SeekBar seekBar;
    RadioGroup jenis;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSeekBar =findViewById(R.id.tbvalue);
        nama = findViewById(R.id.nama);
        usia = findViewById(R.id.usia);
        bb = findViewById(R.id.bb);
        seekBar = findViewById(R.id.seekBar);
        jenis = findViewById(R.id.jeniskelamin);
        check = findViewById(R.id.checkbox);

        txtSeekBar.setText(Integer.toString(textSize));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                textSize = textSize + (progressValue - progress);
                progress = progressValue;
                txtSeekBar.setText(Integer.toString(textSize));
            }
        });
        jenis = findViewById(R.id.jeniskelamin);
        jenis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.lakilaki:
                        jeniskelamin = "Laki-laki";
                        break;
                    case R.id.perempuan:
                        jeniskelamin = "Perempuan";
                        break;
                }
            }
        });
    }

    public void bt_submit(View view) {
        showDialog();
    }

    private void showDialog(){

        if(nama.getText().toString().isEmpty() || usia.getText().toString().isEmpty() ||
                bb.getText().toString().isEmpty() || jeniskelamin == "") {
            Toast.makeText(getApplicationContext(),"Data belum lengkap",Toast.LENGTH_SHORT).show();
        }else if(!check.isChecked()){
            Toast.makeText(getApplicationContext(),"Mohon konfirmasi kembali apakah data sudah benar",Toast.LENGTH_SHORT).show();
        }
        else {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            BeratBadanIdeal beratBadanIdeal = new BeratBadanIdeal();
            beratBadanIdeal.setJenisKelamin(jeniskelamin);
            beratBadanIdeal.setNama(nama.getText().toString());
            beratBadanIdeal.setUsia(Integer.parseInt(usia.getText().toString()));
            beratBadanIdeal.setBeratBadan(Integer.parseInt(bb.getText().toString()));
            beratBadanIdeal.setTinggiBadan(Integer.parseInt(txtSeekBar.getText().toString()));
            boolean input;
            input = dbHelper.addData(beratBadanIdeal);
            if (input) {
                Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi!", Toast.LENGTH_SHORT).show();
            }
            dbHelper.close();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            String message = "";
            message+="Jenis Kelamin: " + jeniskelamin;
            message+="\nNama: " + nama.getText().toString();
            message+="\nUsia: " + usia.getText().toString();
            message+="\nTinggi Badan: " + bb.getText().toString();
            message+="\nBerat Badan: " + txtSeekBar.getText().toString();
            alertDialogBuilder.setTitle("Data Berat Badan Ideal");
            alertDialogBuilder
                    .setMessage(message)
                    .setIcon(R.drawable.file)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivity.this, ViewData.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}