package com.sivamalabrothers.cevher;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class VeriTabaniActivity extends AppCompatActivity implements  SharedPreferences.OnSharedPreferenceChangeListener{
    private EditText e1,e2,e3,e4,e5;
    private Button btnKaydet, btnYukle;
    int idBul = 0;
    Spinner spktgr ;
    String kategori = "";
    private AdView adView;
    LinearLayout reklam_layout;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/7629268735";

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.add_dua);

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.addlay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();


        e2 =  findViewById(R.id.arp);
        e3 =  findViewById(R.id.trk);
        e4 =  findViewById(R.id.anlm);
        e5 =  findViewById(R.id.adet);
        spktgr = findViewById(R.id.spnkategori);
        btnKaydet = findViewById(R.id.btnKaydet);
        reklam_layout = findViewById(R.id.reklam_layout);
        reklam_yukle();

        setSpinner();

        spktgr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kategori = spktgr.getSelectedItem().toString();
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                if(kategori.equals("İLMİHAL")){
                    e2.setHint("Başlık");e2.setVisibility(View.VISIBLE);
                    e3.setHint("İlmihal Bilgisi");e3.setVisibility(View.VISIBLE);
                    e4.setText(".");e4.setClickable(false);
                    e5.setText(".");e5.setClickable(false);
                }else{
                    e4.setText("");
                    e5.setText("");
                    e2.setHint("Arapça");e2.setVisibility(View.VISIBLE);
                    e3.setHint("Türkçe");e3.setVisibility(View.VISIBLE);
                    e4.setClickable(true);  e5.setClickable(true);
                    e4.setHint("Anlam");e4.setVisibility(View.VISIBLE);
                    e5.setHint("Adet");e5.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VeriTabani vt = new VeriTabani(VeriTabaniActivity.this);
                kategori = spktgr.getSelectedItem().toString();
                if(kategori.equals("KURANDA PEYGAMBER DUALARI"))
                    kategori = "pdua";
                else if(kategori.equals("SABAH DUALARI"))
                    kategori = "sdua";
                else if(kategori.equals("AKŞAM DUALARI"))
                    kategori = "adua";
                else if(kategori.equals("CUMA OKUNACAK DUALAR"))
                    kategori = "cdua";
                else if(kategori.equals("AŞİRLER"))
                    kategori = "asdua";
                else if(kategori.equals("ESMA-İ HÜSNA"))
                    kategori = "esdua";
                else if(kategori.equals("EFENDİMİZİN MUTAD OKUDUĞU SURELER"))
                    kategori = "psdua";
                else if(kategori.equals("GÜNLÜK DUALAR"))
                    kategori = "gdua";
                else if(kategori.equals("MÜBAREK GÜN VE GECELERDE YAPILACAK DUALAR"))
                    kategori = "tdua";
                else if(kategori.equals("MUHTELİF DUALAR"))
                    kategori = "tudua";
                else if(kategori.equals("BENİM SEÇTİKLERİM"))
                    kategori = "bdua";
                else if(kategori.equals("İLMİHAL"))
                    kategori = "ildua";
                else if(kategori.equals("HADİSLER"))
                    kategori = "hdsdua";


                String arapca = e2.getText().toString();
                String turkce = e3.getText().toString();
                String anlam = e4.getText().toString();
                String adet = e5.getText().toString();

                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");

                if(kategori.equals("ildua")){
                    e2.setHint("Başlık");e2.setVisibility(View.VISIBLE);
                    e3.setHint("İlmihal Bilgisi");e3.setVisibility(View.VISIBLE);
                    e4.setText(".");e4.setClickable(false);
                    e5.setText(".");e5.setClickable(false);

                }else{
                    e4.setText("");
                    e5.setText("");
                    e2.setHint("Arapça");e2.setVisibility(View.VISIBLE);
                    e3.setHint("Türkçe");e3.setVisibility(View.VISIBLE);
                    e4.setClickable(true);  e5.setClickable(true);
                    e4.setHint("Anlam");e4.setVisibility(View.VISIBLE);
                    e5.setHint("Adet");e5.setVisibility(View.VISIBLE);
                }

                if(!kategori.equals("") && !arapca.equals("") && !turkce.equals("") && !anlam.equals("") && !adet.equals("")){
                    vt.VeriEkle(kategori,arapca ,turkce,anlam,adet);
                    Toast.makeText(getApplicationContext(),"Kayıt eklendi",Toast.LENGTH_LONG).show();

                }else
                    snackBarShow(v);

            }
        });


    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
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
    protected void onResume() {
        // klavye açılınca buton vesairin kaymasını önleyen kod
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        super.onResume();
    }

    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    private void setSpinner(){
        ArrayAdapter<String> adapter;
        String kategoriler[];

            kategoriler = getApplicationContext().getResources().getStringArray(R.array.kategoriler_ekle);
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_gorunum, kategoriler);
            spktgr.setAdapter(adapter);

    }

    private void snackBarShow(View view){
        Snackbar mSnackBar = Snackbar.make(view, "Tüm alanları doldurmalısınız.", Snackbar.LENGTH_LONG);

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
