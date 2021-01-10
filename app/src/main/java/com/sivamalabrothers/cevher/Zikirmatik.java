package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.sivamalabrothers.cevher.DualarSayfasi.heigthKatsayi;
import static com.sivamalabrothers.cevher.DualarSayfasi.widthKatsayi;

public class Zikirmatik extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,View.OnClickListener{

    Button btn ,btn1,btn2,yenile;
    EditText zikirSayi ;
    SharedPreferences preferences,ayarlar;
    int sayactsb,kontrol;
    boolean sesDurumu,titresimDurumu ;
    MediaPlayer ses = null;
    Vibrator titresim = null;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    RelativeLayout arkaplan;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);
        setContentView(R.layout.zikirmatik);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Zikirmatik");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.tsblay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        initViews();
        //resizeAppViews();
        ayarlari_yukle();
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

    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    public void resizeAppViews(){

        float h = DualarSayfasi.getScreenHeight()*1.0f/heigthKatsayi;
        float w =  DualarSayfasi.getScreenWidth()*1.0f/widthKatsayi;

        //Toast.makeText(getApplicationContext(),w+" "+h,Toast.LENGTH_LONG).show();

        Resources r = getResources();
       /* float btnpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        btnpx = btnpx * h;
        float btnwpx = btnpx * w;
        ViewGroup.LayoutParams btnLayoutParams = btn.getLayoutParams();
        btnLayoutParams.height =  (int)btnpx;
        btnLayoutParams.width = (int)btnwpx;
        btn.setLayoutParams(btnLayoutParams);

        float btn1px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        btn1px = btn1px * h;
        float btn1wpx = btn1px * w;
        ViewGroup.LayoutParams btn1LayoutParams = btn1.getLayoutParams();
        btn1LayoutParams.height =  (int)btn1px;
        btn1LayoutParams.width = (int)btn1wpx;
        btn1.setLayoutParams(btn1LayoutParams);

        float btn2px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        btn2px = btn2px * h;
        float btn2wpx = btn2px * w;
        ViewGroup.LayoutParams btn2LayoutParams = btn2.getLayoutParams();
        btn2LayoutParams.height =  (int)btn2px;
        btn2LayoutParams.width = (int)btn2wpx;
        btn2.setLayoutParams(btn2LayoutParams);*/

        float yenilepx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        float yenilenpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, r.getDisplayMetrics());
        yenilepx = yenilepx * h;
        yenilenpx = yenilenpx * w;
        ViewGroup.LayoutParams yenileLayoutParams = yenile.getLayoutParams();
        yenileLayoutParams.height =  (int)yenilepx;
        yenileLayoutParams.width = (int)yenilenpx;
        yenile.setLayoutParams(yenileLayoutParams);


    }

    private void initViews(){
        ses  = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        btn = findViewById(R.id.tesbih);
        btn1 = findViewById(R.id.tesbih1);
        btn2 = findViewById(R.id.tesbih2);
        yenile = findViewById(R.id.yenile);
        zikirSayi = findViewById(R.id.zikirSayi);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        yenile.setOnClickListener(this);


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sayactsb = preferences.getInt("sayactsbAnahtari",1);
        kontrol = preferences.getInt("kontrolbAnahtari",1);

        preferences.registerOnSharedPreferenceChangeListener(this);

        zikirSayi.setText(sayactsb+"");
        btn.setText(sayactsb+"");
        btn1.setText(""+sayactsb);
        btn2.setText(""+sayactsb);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  ayarlari_yukle(){

        sesDurumu = ayarlar.getBoolean("ses",false);
        titresimDurumu = ayarlar.getBoolean("titresim",false);


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

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sayactsbAnahtari",sayactsb);
        editor.putInt("kontrolbAnahtari",kontrol);
        editor.commit();
        super.onPause();
    }

    private void reset() {
        sayactsb = 1;
        kontrol =1;
        btn.setText(""+sayactsb);
        btn1.setText(""+sayactsb);
        btn2.setText(""+sayactsb);
        zikirSayi.setText(""+sayactsb);


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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Zikirmatik.this);
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


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tesbih || id == R.id.tesbih1 || id == R.id.tesbih2) {
            sayactsb = Integer.valueOf(btn1.getText().toString());
            sayactsb++;
            kontrol++;
            btn.setText("" + sayactsb);
            btn1.setText("" + sayactsb);
            btn2.setText("" + sayactsb);
            zikirSayi.setText("" + sayactsb);

            if (sesDurumu)
                ses.start();

            if (titresimDurumu)
                    titresim.vibrate(250);



        }else if(id == R.id.yenile){
            reset();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
            sayactsb = Integer.valueOf(btn1.getText().toString());
            sayactsb++;
            kontrol++;
            btn.setText("" + sayactsb);
            btn1.setText("" + sayactsb);
            btn2.setText("" + sayactsb);
            zikirSayi.setText("" + sayactsb);

            if (sesDurumu)
                ses.start();

            if (titresimDurumu)
                titresim.vibrate(250);




        }
        return super.onKeyDown(keyCode,event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ayarlari_yukle();
    }
}
