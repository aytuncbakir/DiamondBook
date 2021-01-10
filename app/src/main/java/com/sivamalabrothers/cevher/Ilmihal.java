package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class Ilmihal extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    ListView lv;
    ArrayList<String> arapcasi;
    ArrayList<Dua> dualar;
    VeriTabani veriTabani;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;
    private static final int  ALERT_DIALOG2_ID = 2;
    int gid = 0 ;
    String kategori = "";
    String arapca = "";
    String turkcem = "";
    String anlam = "" ;
    String adet = "";
    int tiklananPozisyon = 0;
    ArrayAdapter<String> adapter;

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilmihal_activity);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("İlmihal");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.ilay1);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();

        lv = findViewById(R.id.listview_cevsen);
        setArrayLists(dualar);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(view != null) {
                    if(Build.VERSION.SDK_INT>=21 ){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                        Intent intent = new Intent(getApplicationContext(), Ilmihalsayfasi.class);
                        intent.putExtra("position",position);
                        startActivity(intent,options.toBundle());
                    }else {
                        Intent intent = new Intent(getApplicationContext(), Ilmihalsayfasi.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }

                }

            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(),position+"pos",Toast.LENGTH_LONG).show();
                if(view != null) {
                    tiklananPozisyon = position;

                /* Toast.makeText(getApplicationContext(),tiklananPozisyon+" "+dualar.size()+" "+
                    dualar.get(tiklananPozisyon).getTurkce(),Toast.LENGTH_LONG).show();*/
                    veriTabani = new VeriTabani(Ilmihal.this);
                    dualar = veriTabani.dualariAl("ildua");
                    Dua dua = dualar.get(tiklananPozisyon);
                    //Toast.makeText(getApplicationContext(),dua.getArapca(),Toast.LENGTH_LONG).show();
                    gid = Integer.valueOf(dua.getId());
                    kategori = dua.getKategori();
                    arapca = dua.getArapca();
                    turkcem = dua.getTurkce();
                    anlam = dua.getAnlam();
                    adet = dua.getAdet();
                    showDialog(ALERT_DIALOG2_ID);
                }
                return true ;
            }
        });

        animasyonUygula();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.RIGHT);
            getWindow().setEnterTransition(enterTransition);
        }
    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setArrayLists(dualar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setArrayLists(dualar);
    }

    public void setArrayLists(ArrayList<Dua> dualar) {

        veriTabani = new VeriTabani(this);
        dualar = veriTabani.dualariAl("ildua");
        arapcasi = new ArrayList<String>();
        for (int i = 0; i < dualar.size(); i++) {
            arapcasi.add(dualar.get(i).getArapca());
        }
        adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.cevsen_liste,arapcasi);
        lv.setAdapter(adapter);
        lv.setLongClickable(true);

    }

    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){

            case ALERT_DIALOG2_ID:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Sayfa Yönlendirme.");
                builder2.setMessage("SilDüzenle sayfasına yönlendirileceksiniz.");
                builder2.setIcon(android.R.drawable.ic_dialog_alert);
                builder2.setCancelable(false);
                builder2.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent sil_duzenle = new Intent(getApplicationContext(),VeriTabaniSilDuzenleActivity.class);
                        sil_duzenle.putExtra("id",gid);
                        sil_duzenle.putExtra("kategori",kategori);
                        sil_duzenle.putExtra("arapca",arapca);
                        sil_duzenle.putExtra("turkce",turkcem);
                        sil_duzenle.putExtra("anlam",anlam);
                        sil_duzenle.putExtra("adet",adet);
                        startActivity(sil_duzenle);

                    }
                });

                builder2.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog2 = builder2.create();
                return dialog2;
            default:
                return super.onCreateDialog(id);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ayarlar){
            Intent ayarlar = new Intent(getApplicationContext(),Ayarlar.class);
            startActivity(ayarlar);
            return  true;
        }else  if(id == R.id.tesbih){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ilmihal.this);
                    Intent tef = new Intent(getApplicationContext(),TefsirMenu.class);
                    startActivity(tef,options.toBundle());
                }else {
                    Intent tef = new Intent(getApplicationContext(),TefsirMenu.class);
                    startActivity(tef);
                }
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ayarlari_yukle() {

        String pos = ayarlar.getString("arkaplan", "3");
        switch (Integer.valueOf(pos)) {
            case 0:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa1));
                break;
            case 1:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa2));
                break;
            case 2:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa3));
                break;
            case 3:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa4));
                break;
            case 4:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa5));
                break;
            case 5:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa6));
                break;
            case 6:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa7));
                break;
            case 7:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa8));
                break;
            case 8:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa9));
                break;
            case 9:
                arkaplan.setBackground(getDrawable(R.drawable.sayfa10));
                break;
        }


        sesDurumu = ayarlar.getBoolean("ses",false);
        titresimDurumu = ayarlar.getBoolean("titresim",false);
        ayarlar.registerOnSharedPreferenceChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ayarlari_yukle();
    }

}
