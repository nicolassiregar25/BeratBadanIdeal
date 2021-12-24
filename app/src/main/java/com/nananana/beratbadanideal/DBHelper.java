package com.nananana.beratbadanideal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "bbideal.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bbideal_table(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "jenis_kelamin TEXT, nama TEXT, usia INTEGER, berat_badan INTEGER, " +
                "tinggi_badan INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bbideal_table");
    }

    public boolean addData(BeratBadanIdeal beratBadanIdeal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis_kelamin", beratBadanIdeal.getJenisKelamin());
        cv.put("nama", beratBadanIdeal.getNama());
        cv.put("usia", beratBadanIdeal.getUsia());
        cv.put("berat_badan", beratBadanIdeal.getBeratBadan());
        cv.put("tinggi_badan", beratBadanIdeal.getTinggiBadan());

        return db.insert("bbideal_table", null, cv) > 0;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + "bbideal_table", null);
    }

    public boolean updateData(BeratBadanIdeal beratBadanIdeal, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis_kelamin", beratBadanIdeal.getJenisKelamin());
        cv.put("nama", beratBadanIdeal.getNama());
        cv.put("usia", beratBadanIdeal.getUsia());
        cv.put("berat_badan", beratBadanIdeal.getBeratBadan());
        cv.put("tinggi_badan", beratBadanIdeal.getTinggiBadan());
        return db.update("bbideal_table", cv, "id" + "=" + id,
                null) > 0;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("bbideal_table", "id" + "=" + id, null);
    }
}
