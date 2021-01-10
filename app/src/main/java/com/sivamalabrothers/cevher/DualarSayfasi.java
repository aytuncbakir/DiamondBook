package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class DualarSayfasi extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    RecyclerView recyclerView;
    TextView tv;
    RelativeLayout arkaplan;
    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    VeriTabani veriTabani;
    String id;
    XMLDOMParser xmldomParser;
    ArrayList<Dua> dualar;


    boolean vtYukluMu;

    private InterstitialAd interstitial;
    private static final String REKLAM_ID3 = "ca-app-pub-3183404528711365/2954816114";

    private static final int  CUSTOM_DIALOG_ID1 = 2;
    private static final int  ALERT_DIALOG2_ID = 1;


    static final int widthKatsayi = 720;
    static final int heigthKatsayi = 1280;

    int screnWidth;
    int screenHeight;

    DualarMenuCustomAdapter dualarMenuCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualar_sayfasi);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        screnWidth = getScreenWidth();
        screenHeight= getScreenHeight();

        initViews();
        setDualarMenuCustomAdapter();
        //reklam_yukle();
        animasyonUygula();
    }

    public  void setDualarMenuCustomAdapter(){
        dualarMenuCustomAdapter = new DualarMenuCustomAdapter(this,DualarMenuItem.getDualarMenuItems());
        recyclerView.setAdapter(dualarMenuCustomAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);  */
    }

    public void initViews(){

        arkaplan = findViewById(R.id.dualar_layout);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        veriTabani = new VeriTabani(this);

        xmldomParser = new XMLDOMParser(this);

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vtYukluMu = preferences.getBoolean("veritabaniYukle",false);
        if(!vtYukluMu)
            vtYukle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }

        recyclerView = findViewById(R.id.recyclerview_dualar);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fofbb_reg.ttf");
        tv = findViewById(R.id.tv);
        tv.setTextSize(32);
        tv.setTypeface(font,Typeface.BOLD);
        tv.setText("D U A L A R");
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putInt("sayacAnahtari",sayac);
        editor.putBoolean("veritabaniYukle",vtYukluMu);
        editor.commit();

    }

    public  void vtYukle(){
        VeriTabani vt = new VeriTabani(DualarSayfasi.this);
        for(int j = 0; j < 15; j++) {

            if(j == 0){
                xmldomParser.parseDuaXML("dualar/adua.xml","adua");
                dualar = xmldomParser.getDualar();
            }else if(j == 1){
                xmldomParser.parseDuaXML("dualar/asdua.xml","asdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 2){
                xmldomParser.parseDuaXML("dualar/cdua.xml","cdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 3){
                xmldomParser.parseDuaXML("dualar/esdua.xml","esdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 4){
                xmldomParser.parseDuaXML("dualar/gdua.xml","gdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 5){
                xmldomParser.parseDuaXML("dualar/pdua.xml","pdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 6){
                xmldomParser.parseDuaXML("dualar/psdua.xml","psdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 7){
                xmldomParser.parseDuaXML("dualar/sdua.xml","sdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 8){
                xmldomParser.parseDuaXML("dualar/tdua.xml","tdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 9){
                xmldomParser.parseDuaXML("dualar/tudua.xml","tudua");
                dualar = xmldomParser.getDualar();
            }else if(j == 10){
                xmldomParser.parseDuaXML("dualar/hdsdua.xml","hdsdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 11){
                xmldomParser.parseDuaXML("dualar/bdua.xml","bdua");
                dualar = xmldomParser.getDualar();
            }else if(j == 12){
                xmldomParser.parseDuaXML("dualar/saldua.xml","saldua");
                dualar = xmldomParser.getDualar();
            }else if(j == 13){
                xmldomParser.parseDuaXML("dualar/hdeldua.xml","hdeldua");
                dualar = xmldomParser.getDualar();
            }else if(j == 14){
                xmldomParser.parseXML("dualar/ildua.xml","ildua");
                dualar = xmldomParser.getDualar();
            }

            for (int i = 0; i < dualar.size(); i++) {

                vt.VeriEkle(dualar.get(i).getKategori(), dualar.get(i).getArapca(),
                        dualar.get(i).getTurkce(), dualar.get(i).getAnlam(), dualar.get(i).getAdet());
            }
        }
        vtYukluMu = true;
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


    private void reklam_yukle(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID3);

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

        if(position < 14 && position !=11 || position == 20 || position == 21){
            if(Build.VERSION.SDK_INT>=21){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DualarSayfasi.this);
                Intent krn = new Intent(getApplicationContext(),DualarListesi.class);
                krn.putExtra("position",position);
                startActivity(krn,options.toBundle());
            }else{
                Intent krn = new Intent(getApplicationContext(),DualarListesi.class);
                krn.putExtra("position",position);
                startActivity(krn);
            }
        }else if(position >= 14 || position == 11){
            if(Build.VERSION.SDK_INT>=21){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DualarSayfasi.this);
                Intent krn = new Intent(getApplicationContext(),UzunDualarGoruntule.class);
                if(position == 11)
                    krn.putExtra("uzunKategori","bdua");
                else if(position == 14)
                    krn.putExtra("uzunKategori","bedir");
                else  if(position == 15)
                    krn.putExtra("uzunKategori","uhud");
                else  if(position == 16)
                    krn.putExtra("uzunKategori","tahdua");
                else  if(position == 17)
                    krn.putExtra("uzunKategori","deldua");
                else  if(position == 18)
                    krn.putExtra("uzunKategori","sekdua");
                else  if(position == 19)
                    krn.putExtra("uzunKategori","tevdua");

                startActivity(krn,options.toBundle());
            }else{
                Intent krn = new Intent(getApplicationContext(),UzunDualarGoruntule.class);
                if(position == 14)
                    krn.putExtra("uzunKategori","bedir");
                else  if(position == 15)
                    krn.putExtra("uzunKategori","uhud");
                else  if(position == 16)
                    krn.putExtra("uzunKategori","tahdua");
                else  if(position == 17)
                    krn.putExtra("uzunKategori","deldua");
                else  if(position == 18)
                    krn.putExtra("uzunKategori","sekdua");
                else  if(position == 19)
                    krn.putExtra("uzunKategori","tevdua");

                startActivity(krn);
            }
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

            case ALERT_DIALOG2_ID:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(Html.fromHtml(getResources().getString(R.string.html_yonlendir)));
                builder2.setMessage(Html.fromHtml(getResources().getString(R.string.html_yonlendirmetin)));
                builder2.setIcon(android.R.drawable.ic_dialog_alert);
                builder2.setCancelable(false);
                builder2.setPositiveButton("Manuel Tesbih", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent manueldua = new Intent(getApplicationContext(),Manueldua.class);
                        //manueldua.putExtra("turkce",turkcem);
                       // manueldua.putExtra("adet",adet);
                        startActivity(manueldua);
                        dialogInterface.cancel();
                    }
                });

                builder2.setNegativeButton("Sil Düzenle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent sil_duzenle = new Intent(getApplicationContext(),VeriTabaniSilDuzenleActivity.class);
                        /*sil_duzenle.putExtra("id",gid);
                        sil_duzenle.putExtra("kategori",kategori);
                        sil_duzenle.putExtra("arapca",arapca);
                        sil_duzenle.putExtra("turkce",turkcem);
                        sil_duzenle.putExtra("anlam",anlam);
                        sil_duzenle.putExtra("adet",adet);*/
                        startActivity(sil_duzenle);
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog2 = builder2.create();
                return dialog2;

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

    public static int getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



}
