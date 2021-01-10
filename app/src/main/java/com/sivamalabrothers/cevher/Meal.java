package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.content.Context;
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

public class Meal extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    ListView lv;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;
    Bundle gelenMenu;
    String menu;
    String kuran[];
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_activity);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Meal");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.klay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ayarlari_yukle();


        gelenMenu = getIntent().getExtras();
        if(gelenMenu != null)
            menu = gelenMenu.getString("menu");


        kuran = getResources().getStringArray(R.array.kuran_listesi);



        lv = findViewById(R.id.listview_cevsen);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.cevsen_liste,kuran);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                   if(Build.VERSION.SDK_INT>=21 ){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                        intent = new Intent(getApplicationContext(),Mealsayfasi.class);
                        intent.putExtra("menu",menu);
                        intent.putExtra("position",position);
                        startActivity(intent,options.toBundle());
                    }else {
                        intent = new Intent(getApplicationContext(),Mealsayfasi.class);
                       intent.putExtra("menu",menu);
                        intent.putExtra("position",position);
                        startActivity(intent);
                    }



            }
        });

        animasyonUygula();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.LEFT);
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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Meal.this);
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



}
