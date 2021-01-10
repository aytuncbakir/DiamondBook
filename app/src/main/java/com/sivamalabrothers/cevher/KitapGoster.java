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
import android.webkit.WebView;
import android.widget.RelativeLayout;

import java.util.Locale;

public class KitapGoster extends AppCompatActivity implements View.OnClickListener,
                                        SharedPreferences.OnSharedPreferenceChangeListener{

    WebView cvs;
    int position = 0;
    FloatingActionButton fabayarlar;

    XMLDOMParser xmldomParser;
    Bundle gelenVeri;

    SharedPreferences  ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    RelativeLayout arkaplan;
    private static int yaziTur = 0;
    int gelenPosition = 0;

    String gelenArapca, gelenAnlam, gelenTurkce, gelenAdet,gelenKonum;

    // Justify tag
    String justifyTag = "<html><body style='text-align:justify;'>%s</body></html>";
    // Concatenate your string with the tag to Justify it
    String data ;
    String dataString ;

    int gelenKategori;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sozler);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.cslay1);
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();
        initViews();
        duaGoruntule(gelenPosition);
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

        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null) {
            gelenPosition = gelenVeri.getInt("position");


        }
    }

    public void duaGoruntule( int position ){

        if(position == 0){
            data = getString(R.string.sozler);
            dataString = String.format(Locale.US, justifyTag, data);
        }else if(position == 1){
            data = getString(R.string.lemalar);
            dataString = String.format(Locale.US, justifyTag, data);
        }else if(position == 2){
            data = getString(R.string.mektubat);
            dataString = String.format(Locale.US, justifyTag, data);
        }

        // Load the data in the web view
        cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                yaziTur = yaziTur % 3;
                if(yaziTur == 0){
                    // Load the data in the web view
                    cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");
                }else if(yaziTur == 1){
                    // Load the data in the web view
                    cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");
                }else if(yaziTur == 2){
                    // Load the data in the web view
                    cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");
                }
                yaziTur++;
                break;

        }
    }


}
