package com.sivamalabrothers.cevher;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Tecvidsayfasi extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        View.OnClickListener{

    EditText cvs;
    int position = 0;
    ArrayList<String> arapcasi;
    ArrayList<String> anlami;
    ArrayList<String> turkcesi;
    XMLDOMParser xmldomParser;
    Bundle gelenposition;
    float  tecvidpunto = 14;
    FloatingActionButton fabayarlar;
    static int renksec = 1;
    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    RelativeLayout arkaplan;
    private static final int  CUSTOM_DIALOG_ID = 2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tecvid_sayfasi);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.tslay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();

        //setting font
        //Typeface font = Typeface.createFromAsset(getAssets(),"fonts/husrevarapca.ttf");
        //cvs.setTypeface(font);
        initViews();
        setArrayLists(xmldomParser);
        animasyonUygula();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Fade enterTransition = new Fade();
            enterTransition.setDuration(1000);
            getWindow().setEnterTransition(enterTransition);
        }
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

    public void initViews(){

        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);
        cvs = findViewById(R.id.cevsentahtasi);
        xmldomParser = new XMLDOMParser(this);

        gelenposition = getIntent().getExtras();
        if(gelenposition != null)
            position = gelenposition.getInt("position");
        if(position == -1)
            position = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = renksec % 2;
        switch(id)
        {
            case 0:
            {
                cvs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                renksec++;
                break;
            }
            case 1:
            {
                cvs.setTextColor(getResources().getColor(R.color.black));
                renksec++;
                break;
            }

        }
    }


    public void setArrayLists(  XMLDOMParser xmldomParser){
        String xmlFile = "dualar/tecvid.xml";
        xmldomParser.parseXML(xmlFile,"");
        arapcasi = xmldomParser.getArapcasi();
        anlami= xmldomParser.getAnlami();
        turkcesi= xmldomParser.getTurkcesi();
        cvs.setTextSize(tecvidpunto);
        cvs.setText(turkcesi.get(position));
    }



}
