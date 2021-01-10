package com.sivamalabrothers.cevher;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Fade;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UzunDualarGoruntule extends AppCompatActivity implements View.OnClickListener,
                                        SharedPreferences.OnSharedPreferenceChangeListener{

    EditText cvs;
    int position = 0;
    FloatingActionButton fabayarlar;
    ArrayList<Dua> dualar;
    XMLDOMParser xmldomParser;
    Bundle gelenVeri;



    SharedPreferences  ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    RelativeLayout arkaplan;
    private static int yaziTur = 0;

    String  gelenArapca = "",
            gelenAnlam= "",
            gelenTurkce= "",
            gelenAdet= "",
            gelenKategori= "";

    String arkaPlanAyarla = "";

    int gelenPosition = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uzun_dualar_goruntule);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.cslay1);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();
        initViews();
        duaGoruntule();
        animasyonUygula();

    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Fade enterTransition = new Fade();
            enterTransition.setDuration(1000);
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

        arkaPlanAyarla = ayarlar.getString("arkaplan", "3");
        switch (Integer.valueOf(arkaPlanAyarla)) {
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

    public void initViews(){
        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);

        cvs = findViewById(R.id.cevsentahtasi);

        xmldomParser = new XMLDOMParser(this);

        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null) {
            gelenKategori = gelenVeri.getString("uzunKategori");
            gelenPosition = gelenVeri.getInt("position");
        }
        String kategori = "";
        if(gelenKategori != "") {
            if(gelenKategori.equals("sekdua")) {
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenTurkce = dualar.get(0).getTurkce();
                gelenAnlam = dualar.get(0).getAnlam();
                gelenAdet = dualar.get(0).getAdet();
            }else if(gelenKategori.equals("hdeldua")) {
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseDuaXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenTurkce = dualar.get(gelenPosition).getTurkce();
                gelenAnlam = dualar.get(gelenPosition).getAnlam();
                gelenAdet = dualar.get(gelenPosition).getAdet();
            }else if(gelenKategori.equals("daria")) {
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseDuaXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenTurkce = dualar.get(gelenPosition).getTurkce();
                gelenAnlam = dualar.get(gelenPosition).getAnlam();
                gelenAdet = dualar.get(gelenPosition).getAdet();
            }else if(gelenKategori.equals("dariasayfadua")) {
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseDuaXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenTurkce = dualar.get(gelenPosition).getTurkce();
                gelenAnlam = dualar.get(gelenPosition).getAnlam();
                gelenAdet = dualar.get(gelenPosition).getAdet();
            }else if(gelenKategori.equals("bdua")) {
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseDuaXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenTurkce = dualar.get(gelenPosition).getTurkce();
                gelenAnlam = dualar.get(gelenPosition).getAnlam();
                gelenAdet = dualar.get(gelenPosition).getAdet();
            }
            else{
                kategori = "dualar/"+gelenKategori + ".xml";
                xmldomParser.parseXML(kategori, gelenKategori);
                dualar = xmldomParser.getDualar();
                gelenArapca = dualar.get(0).getArapca();
                gelenTurkce = dualar.get(0).getTurkce();
                gelenAnlam = dualar.get(0).getAnlam();
                gelenAdet = dualar.get(0).getAdet();
            }
        }


    }

    public void duaGoruntule(){

        cvs.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        cvs.setTextSize(14);
        cvs.setText(gelenTurkce);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                yaziTur = yaziTur % 3;
                if(yaziTur == 0){
                    if(gelenKategori.equals("sekdua")) {
                        cvs.setText("");
                        cvs.setBackground(getDrawable(R.drawable.sekine));
                    }else{

                        cvs.setBackground(null);
                        Typeface  font = Typeface.createFromAsset(getAssets(),"fonts/KuranKerimFontHamdullah.ttf");
                        cvs.setTypeface(font,Typeface.NORMAL);
                        cvs.setTextSize(24);
                        cvs.setText(gelenArapca);
                    }

                }else if(yaziTur == 1){

                    cvs.setBackground(null);
                    cvs.setTextSize(14);
                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                    cvs.setText(gelenTurkce);
                }else if(yaziTur == 2){
                    cvs.setBackground(null);
                    cvs.setTextSize(14);
                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                    cvs.setText(gelenAnlam);
                }
                yaziTur++;
                break;
        }
    }



}
