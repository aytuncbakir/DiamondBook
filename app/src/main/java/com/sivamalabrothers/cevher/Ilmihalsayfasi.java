package com.sivamalabrothers.cevher;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

public class Ilmihalsayfasi extends AppCompatActivity implements
                                        SharedPreferences.OnSharedPreferenceChangeListener,View.OnClickListener{

    EditText cvs;
    int position = 0;
    ArrayList<String> turkcesi;
    ArrayList<Dua> dualar;
    float  cevsenpunto = 14;
    Bundle gelenposition;
    VeriTabani veriTabani;
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
        setContentView(R.layout.ilmihal_sayfasi);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.islay1);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();

        initViews();
        setArrayLists(dualar);
        animasyonUygula();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Fade enterTransition = new Fade();
            enterTransition.setDuration(1000);
            getWindow().setEnterTransition(enterTransition);
        }
    }

    public void initViews(){

        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);
        cvs = findViewById(R.id.cevsentahtasi);
        veriTabani = new VeriTabani(this);

        gelenposition = getIntent().getExtras();
        if(gelenposition != null)
            position = gelenposition.getInt("position");


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

    public void setArrayLists(ArrayList<Dua> dualar) {

        dualar = veriTabani.dualariAl("ildua");
        turkcesi = new ArrayList<String>();
        for (int i = 0; i < dualar.size(); i++) {
            turkcesi.add(dualar.get(i).getTurkce());
        }
        cvs.setTextSize(cevsenpunto);
        cvs.setText(turkcesi.get(position));
    }



    private void snackBarShow(View view){
        Snackbar mSnackBar = Snackbar.make(view, "Kayıt giriniz.", Snackbar.LENGTH_LONG);

        view = mSnackBar.getView();

        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();

        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.yavruagzi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(0.7f);
        }
        TextView mainTextView = (view).findViewById(android.support.design.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mainTextView.setPadding(40,0,40,0);
        mainTextView.setGravity(Gravity.CENTER);
        mSnackBar.show();
    }


}
