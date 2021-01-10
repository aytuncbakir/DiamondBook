package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class GirisSayfasi extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    RecyclerView recyclerView;
    LinearLayout listItem;
    TextView tv;
    RelativeLayout arkaplan;
    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    Boolean updateEdildiMi;
    private static final int  CUSTOM_DIALOG_ID1 = 2;

    private InterstitialAd interstitial;
    private static final String REKLAM_ID4 = "ca-app-pub-3183404528711365/7274045518";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_sayfasi);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.giris_layout);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        updateEdildiMi = preferences.getBoolean("updateEt",false);

        if(!updateEdildiMi)
            update();

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }

        reklam_yukle();

        recyclerView = findViewById(R.id.recyclerview_giris);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fofbb_reg.ttf");
        tv = findViewById(R.id.tv);
        tv.setTextSize(32);
        tv.setTypeface(font,Typeface.BOLD);
        tv.setText("C E V H E R");

        GirisMenuCustomAdapter girisMenuCustomAdapter =
                new GirisMenuCustomAdapter(this,GirisMenuItem.getGirisMenuItems());
        recyclerView.setAdapter(girisMenuCustomAdapter);

       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);  */

        animasyonUygula();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(400);
            enterTransition.setSlideEdge(Gravity.BOTTOM);
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
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("updateEt",updateEdildiMi);
        editor.commit();

    }

    public void update(){

        VeriTabani veriTabani = new VeriTabani(this);
        XMLDOMParser xmldomParser = new XMLDOMParser(this);
        xmldomParser.parseXML("dualar/saldua.xml","saldua");
        ArrayList<Dua> dualar = xmldomParser.getDualar();

        for (int i = 0; i < dualar.size(); i++) {

            veriTabani.VeriEkle(dualar.get(i).getKategori(), dualar.get(i).getArapca(),
                    dualar.get(i).getTurkce(), dualar.get(i).getAnlam(), dualar.get(i).getAdet());
        }

        updateEdildiMi = true;

    }

    private void reklam_yukle(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID4);

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });
    }

    public  void tiklananMenuItem(int position) {

        switch(position){
            case 0 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent krn = new Intent(getApplicationContext(),KuranMenu.class);
                    startActivity(krn,options.toBundle());
                }else {
                    Intent krn = new Intent(getApplicationContext(),KuranMenu.class);
                    startActivity(krn);
                }

                break;
            case 1 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                    startActivity(mMenu,options.toBundle());
                }else {
                    Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                    startActivity(mMenu);
                }

                break;
            case 2 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent tef = new Intent(getApplicationContext(),TefsirMenu.class);
                    startActivity(tef,options.toBundle());
                }else {
                    Intent tef = new Intent(getApplicationContext(),TefsirMenu.class);
                    startActivity(tef);
                }

                break;
            case 3 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent cvsn = new Intent(getApplicationContext(),Cevsen.class);
                    startActivity(cvsn,options.toBundle());
                }else {
                    Intent cvsn = new Intent(getApplicationContext(),Cevsen.class);
                    startActivity(cvsn);
                }

                break;
            case 4 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent main1 = new Intent(getApplicationContext(),DualarSayfasi.class);
                    startActivity(main1,options.toBundle());
                }else {
                    Intent main1 = new Intent(getApplicationContext(),DualarSayfasi.class);
                    startActivity(main1);
                }

                break;
            case 5 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent nmz = new Intent(getApplicationContext(),NamazVakitleri.class);
                    startActivity(nmz,options.toBundle());
                }else {
                    Intent nmz = new Intent(getApplicationContext(),NamazVakitleri.class);
                    startActivity(nmz);
                }

                break;
            case 6 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent tes = new Intent(getApplicationContext(),TabActivity.class);
                    startActivity(tes,options.toBundle());
                }else {
                    Intent tes = new Intent(getApplicationContext(),TabActivity.class);
                    startActivity(tes);
                }

                break;
            case 7 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent tsbh = new Intent(getApplicationContext(),Tesbih.class);
                    startActivity(tsbh,options.toBundle());
                }else {
                    Intent tsbh = new Intent(getApplicationContext(),Tesbih.class);
                    startActivity(tsbh);
                }

                break;
            case 8 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent manuel = new Intent(getApplicationContext(),Manueldua.class);
                    startActivity(manuel,options.toBundle());
                }else {
                    Intent manuel = new Intent(getApplicationContext(),Manueldua.class);
                    startActivity(manuel);
                }

                break;
            case 9 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent zkr = new Intent(getApplicationContext(),Zikirmatik.class);
                    startActivity(zkr,options.toBundle());
                }else {
                    Intent zkr = new Intent(getApplicationContext(),Zikirmatik.class);
                    startActivity(zkr);
                }

                break;
            case 10 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent tcvd = new Intent(getApplicationContext(), Tecvid.class);
                    startActivity(tcvd,options.toBundle());
                }else {
                    Intent tcvd = new Intent(getApplicationContext(), Tecvid.class);
                    startActivity(tcvd);
                }

                break;
            case 11 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent ilm = new Intent(getApplicationContext(),Ilmihal.class);
                    startActivity(ilm,options.toBundle());
                }else {
                    Intent ilm = new Intent(getApplicationContext(),Ilmihal.class);
                    startActivity(ilm);
                }

                break;
            case 12 :
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GirisSayfasi.this);
                    Intent ilm = new Intent(getApplicationContext(),RisaleSayfasi.class);
                    startActivity(ilm,options.toBundle());
                }else {
                    Intent ilm = new Intent(getApplicationContext(),RisaleSayfasi.class);
                    startActivity(ilm);
                }
                break;


        }

    }

    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){

            case CUSTOM_DIALOG_ID1:

                final Dialog kaydetDialog = new Dialog(this);

                kaydetDialog.setTitle(Html.fromHtml(getResources().getString(R.string.html_title)));
                kaydetDialog.setContentView(R.layout.bilgilendirme);

                final EditText bilgilendirme = kaydetDialog.findViewById(R.id.bilgilendirme);
                bilgilendirme.setText(Html.fromHtml(getResources().getString(R.string.html)));

                return kaydetDialog;
            default:
                return super.onCreateDialog(id);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ayarlari_yukle();
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
}
