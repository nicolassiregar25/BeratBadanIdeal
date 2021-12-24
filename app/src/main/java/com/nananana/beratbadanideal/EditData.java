package com.nananana.beratbadanideal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditData extends AppCompatActivity {
    TextView txtSeekBar;
    String jeniskelamin = "";
    EditText nama, usia, bb;
    SeekBar seekBar;
    RadioGroup jenis;
    RadioButton lakilaki, perempuan;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        txtSeekBar =findViewById(R.id.tbvalue);
        nama = findViewById(R.id.nama);
        usia = findViewById(R.id.usia);
        bb = findViewById(R.id.bb);
        seekBar = findViewById(R.id.seekBar);
        jenis = findViewById(R.id.jeniskelamin);
        check = findViewById(R.id.checkbox);
        lakilaki = findViewById(R.id.lakilaki);
        perempuan = findViewById(R.id.perempuan);

        nama.setText(getIntent().getExtras().getString("nama"));
        usia.setText(String.valueOf(getIntent().getExtras().getInt("usia")));
        bb.setText(String.valueOf(getIntent().getExtras().getInt("beratBadan")));
        jeniskelamin = getIntent().getExtras().getString("jenisKelamin");

        txtSeekBar.setText(String.valueOf(getIntent().getExtras().getInt("tinggiBadan")));
        seekBar.setProgress(getIntent().getExtras().getInt("tinggiBadan")-100);
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
                progress = progressValue;
                txtSeekBar.setText(String.valueOf(progress+100));
            }
        });

        if(jeniskelamin.equals("Perempuan")){
            perempuan.setChecked(true);
        }
        else{
            lakilaki.setChecked(true);
        }

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

    public void submit(View view) {
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
            input = dbHelper.updateData(beratBadanIdeal, getIntent().getExtras().getInt("id"));
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
                            Intent intent = new Intent(EditData.this, ViewData.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}