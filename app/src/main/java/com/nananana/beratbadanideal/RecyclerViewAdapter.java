package com.nananana.beratbadanideal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final ArrayList idList;
    private final ArrayList jenisKelaminList;
    private final ArrayList namaList;
    private final ArrayList usiaList;
    private final ArrayList beratBadanList;
    private final ArrayList tinggiBadanList;
    private Context context;
    private View view;

    public RecyclerViewAdapter(ArrayList idList, ArrayList jenisKelaminList, ArrayList namaList, ArrayList usiaList, ArrayList beratBadanList, ArrayList tinggiBadanList) {
        this.idList = idList;
        this.jenisKelaminList = jenisKelaminList;
        this.namaList = namaList;
        this.usiaList = usiaList;
        this.beratBadanList = beratBadanList;
        this.tinggiBadanList = tinggiBadanList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView jenisKelamin;
        private final TextView nama;
        private final TextView usia;
        private ImageButton edit, delete;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            jenisKelamin = itemView.findViewById(R.id.jenisKelamin);
            nama = itemView.findViewById(R.id.nama);
            usia = itemView.findViewById(R.id.usia);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final int id = (Integer) idList.get(position);
        final String jenisKelamin = (String) jenisKelaminList.get(position);
        final String nama = (String) namaList.get(position);
        final Integer usia = (Integer) usiaList.get(position);
        final Integer beratBadan = (Integer) beratBadanList.get(position);
        final Integer tinggiBadan = (Integer) tinggiBadanList.get(position);
        holder.jenisKelamin.setText("Jenis Kelamin: "+jenisKelamin);
        holder.nama.setText("Nama: "+nama);
        holder.usia.setText("Usia: "+usia+" tahun");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                intent.putExtra("data","Jenis Kelamin: " + jenisKelamin+
                        "\nNama: " + nama+
                        "\nUsia: " + usia+
                        "\nBerat Badan: " + beratBadan+
                        "\nTinggi Badan: " + tinggiBadan);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Integer idBbi = id;
                String message ="Yakin menghapus data "+nama+"?";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Hapus Data");
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                DBHelper dbHelper = new DBHelper(context.getApplicationContext());
                                dbHelper.deleteData(idBbi);
                                Toast.makeText(context.getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                dbHelper.close();
                                Intent intent = new Intent(view.getContext(), ViewData.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), EditData.class);
                intent.putExtra("id", id);
                intent.putExtra("jenisKelamin", jenisKelamin);
                intent.putExtra("nama", nama);
                intent.putExtra("usia", usia);
                intent.putExtra("beratBadan", beratBadan);
                intent.putExtra("tinggiBadan", tinggiBadan);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }
}

