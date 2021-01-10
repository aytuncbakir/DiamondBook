package com.sivamalabrothers.cevher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dualar";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLO_DUA = "dua";
    private static final String ROW_ID = "id";
    private static final String ROW_KATEGORI = "kategori";
    private static final String ROW_ARAPCA = "arapca";
    private static final String ROW_TURKCE = "turkce";
    private static final String ROW_ANLAM = "anlam";
    private static final String ROW_ADET = "adet";


    public VeriTabani(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO_DUA + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_KATEGORI + " TEXT NOT NULL, "
                + ROW_ARAPCA + " TEXT NOT NULL, "
                + ROW_TURKCE + " TEXT NOT NULL, "
                + ROW_ANLAM+ " TEXT NOT NULL, "
                + ROW_ADET + " TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void VeriEkle(String kategori, String arapca, String turkce, String anlam, String adet){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_KATEGORI, kategori);
            cv.put(ROW_ARAPCA, arapca);
            cv.put(ROW_TURKCE, turkce);
            cv.put(ROW_ANLAM, anlam);
            cv.put(ROW_ADET, adet);

            db.insert(TABLO_DUA, null,cv);
        }catch (Exception e){
        }
        db.close();
    }

    public List<String> VeriListele(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID,ROW_KATEGORI,ROW_ARAPCA,ROW_TURKCE,ROW_ANLAM,ROW_ADET};
            Cursor cursor = db.query(TABLO_DUA, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)
                        + " - "
                        + cursor.getString(3)
                        + " - "
                        + cursor.getString(4)
                        + " - "
                        + cursor.getString(5));
            }
        }catch (Exception e){
        }
        db.close();
        return veriler;
    }

    public ArrayList<Dua> dualariAl(String kategori){
        ArrayList<Dua> dualar = new ArrayList<Dua>();
        Dua dua;
        String id;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID,ROW_KATEGORI,ROW_ARAPCA,ROW_TURKCE,ROW_ANLAM,ROW_ADET};
            String where = ROW_KATEGORI+" = '"+kategori+"'";
            Cursor cursor =  db.query(true, TABLO_DUA, stunlar, where, null, ROW_TURKCE, null, null, null);

            //db.query(TABLO_DUA, stunlar,where,null,null,null,null);

            while (cursor.moveToNext()){

                id = String.valueOf( cursor.getInt(0));
                dua = new Dua(id,cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5));
                dualar.add(dua);

            }
        }catch (Exception e){
        }
        db.close();
        return dualar;
    }

    public void VeriSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_ID + " = '" + id+"'" ;
            db.delete(TABLO_DUA,where,null);
        }catch (Exception e){
        }
        db.close();
    }

    public void VeriDuzenle(int id, String kategori, String arapca, String turkce, String anlam, String adet){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_KATEGORI, kategori);
            cv.put(ROW_ARAPCA, arapca);
            cv.put(ROW_TURKCE, turkce);
            cv.put(ROW_ANLAM, anlam);
            cv.put(ROW_ADET, adet);
            String where = ROW_ID +" = '"+ id + "'";
            db.update(TABLO_DUA,cv,where,null);
        }catch (Exception e){
        }
        db.close();
    }

}