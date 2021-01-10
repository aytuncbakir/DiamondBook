package com.sivamalabrothers.cevher;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class VeriTabaniSilDuzenleActivity extends AppCompatActivity implements  SharedPreferences.OnSharedPreferenceChangeListener{
    private EditText e2,e3,e4,e5;
    private Button btnSil,btnDuzenle;

    int idBul = 0;

    Bundle gelenVeri;

    int id ;
    String arapca ;
    String turkce;
    String anlam ;
    String adet;
    String kategori;
    private AdView adView;
    LinearLayout reklam_layout2;
    private static final String REKLAM_ID2 = "ca-app-pub-3183404528711365/7823999413";


    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu, duzenlendiMi;
    LinearLayout arkaplan;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.sil_duzenle_dua);

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.activity_main);
        duzenlendiMi = false;
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();

        e2 =  findViewById(R.id.arp);
        e3 =  findViewById(R.id.trk);
        e4 =  findViewById(R.id.anlm);
        e5 =  findViewById(R.id.adet);

        btnSil =  findViewById(R.id.btnSil);
        btnDuzenle =  findViewById(R.id.btnDuzenle);
        reklam_layout2 = findViewById(R.id.reklam_layout2);
        reklam_yukle();


        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null) {
            idBul = Integer.valueOf(gelenVeri.getInt("id"));
            kategori = gelenVeri.getString("kategori");
            arapca = gelenVeri.getString("arapca");
            turkce = gelenVeri.getString("turkce");
            anlam = gelenVeri.getString("anlam");
            adet = gelenVeri.getString("adet");

        }

        e2.setText(arapca);
        e3.setText(turkce);
        e4.setText(anlam);
        e5.setText(adet);


        // Silme tuşuna tıklandığında yapılacak olan işlemler...
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // VeriTabanı classımızı tanımlıyoruz
                VeriTabani vt = new VeriTabani(VeriTabaniSilDuzenleActivity.this);


                if( !arapca.equals("") && !turkce.equals("") && !anlam.equals("") && !adet.equals("")) {

                    if(!duzenlendiMi) {
                        vt.VeriSil(idBul);
                        Toast.makeText(getApplicationContext(), "Kayıt silindi", Toast.LENGTH_LONG).show();
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                        e5.setText("");
                        duzenlendiMi = true;
                    }
                }else
                    snackBarShow(v);


                e2.setHint("Arapça");e2.setVisibility(View.VISIBLE);e2.setEnabled(false);
                e3.setHint("Türkçe");e3.setVisibility(View.VISIBLE);e3.setEnabled(false);
                e4.setHint("Anlam");e4.setVisibility(View.VISIBLE);e4.setEnabled(false);
                e5.setHint("Adet");e5.setVisibility(View.VISIBLE);e5.setEnabled(false);

            }
        });

        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Spinner uzun Tıkladığımızda otomatik dolan EditTextlerimizi Stringlere atıyalım
                String arapca = e2.getText().toString();
                String turkce = e3.getText().toString();
                String anlam = e4.getText().toString();
                String adet = e5.getText().toString();



                // Veritabanı bağlantımızı açalım ver ardından gerekli bilgileri VeriDuzenle metotuna gönderelim
                VeriTabani vt = new VeriTabani(VeriTabaniSilDuzenleActivity.this);

                if( !arapca.equals("") && !turkce.equals("") && !anlam.equals("") && !adet.equals("")){
                    vt.VeriDuzenle(idBul,kategori, arapca,turkce,anlam,adet);
                    Toast.makeText(getApplicationContext(),"Kayıt güncellendi",Toast.LENGTH_LONG).show();
                    duzenlendiMi = true;
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                }else
                    snackBarShow(v);

                e2.setHint("Arapça");e2.setVisibility(View.VISIBLE);e2.setEnabled(false);
                e3.setHint("Türkçe");e3.setVisibility(View.VISIBLE);e3.setEnabled(false);
                e4.setHint("Anlam");e4.setVisibility(View.VISIBLE);e4.setEnabled(false);
                e5.setHint("Adet");e5.setVisibility(View.VISIBLE);e5.setEnabled(false);

            }
        });

    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void snackBarShow(View view){
        Snackbar mSnackBar = Snackbar.make(view, "Tüm alanlar dolu olmalıdır.", Snackbar.LENGTH_LONG);

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

    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID2);

        reklam_layout2.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

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
    protected void onResume() {
        // klavye açılınca buton vesairin kaymasını önleyen kod
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        super.onResume();
    }


}
