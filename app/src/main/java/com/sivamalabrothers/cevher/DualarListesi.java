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
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import java.util.ArrayList;


public class DualarListesi extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{


    ListView lv;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;

    XMLDOMParser xmldomParser;
    Bundle gelenVeri;
    int gelenPosition;
    String kategori = "";

    VeriTabani veriTabani;

    ArrayList<String> basliklar;
    ArrayList<Dua> dualar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dualar_listeleme);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.clay1);
        lv = findViewById(R.id.listview_cevsen);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();

        setListView();


        animasyonUygula();

        Toast.makeText(getApplicationContext(),"Virdi çekmek için uzun, görüntülemek için kısa tıklayınız.",Toast.LENGTH_LONG).show();
    }

    public void setArrayLists() {
        basliklar = new ArrayList<String>();
        ArrayAdapter<String> adapter = null;
        if(gelenPosition == 20){
            DosyaOku dosyaOku = new DosyaOku(this);
            basliklar = dosyaOku.dosyadanyukle("darialiste.txt");
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dualar_liste,basliklar);
        }else  if(gelenPosition == 21){
            DosyaOku dosyaOku = new DosyaOku(this);
            basliklar = dosyaOku.dosyadanyukle("darialiste1.txt");
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dualar_liste,basliklar);
        }
        else{


            for (int i = 0; i < dualar.size(); i++)
                if (gelenPosition == 5)
                    basliklar.add(dualar.get(i).getTurkce() + " - " + dualar.get(i).getBaslik());
                else
                    basliklar.add(dualar.get(i).getBaslik());
             adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dualar_liste,basliklar);
        }


        lv.setAdapter(adapter);

    }

    private void setListView() {
        veriTabani = new VeriTabani(this);
        xmldomParser = new XMLDOMParser(this);

        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null)
            gelenPosition = gelenVeri.getInt("position");

        kategori = duaKategori(gelenPosition);
        String xmlFile = "dualar/"+kategori+".xml";
        xmldomParser.parseDuaXML(xmlFile,kategori);
        dualar = xmldomParser.getDualar();
        setArrayLists();


    }


    private String duaKategori(int gelenPosition){
        switch (gelenPosition){
            case 0:
                return "pdua";
            case 1:
                return "sdua";
            case 2:
                return "adua";
            case 3:
                return "cdua";
            case 4:
                return "asdua";
            case 5:
                return "esdua";
            case 6:
                return "psdua";
            case 7:
                return "gdua";
            case 8:
                return "tdua";
            case 9:
                return "tudua";
            case 10:
                return "hdsdua";
            case 11:
                return "bdua";
            case 12:
                return "saldua";
            case 13:
                return "hdeldua";
            case 20:
                return "daria";
            case 21:
                return "dariasayfadua";

        }

        return "";

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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(Build.VERSION.SDK_INT>=21 ){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DualarListesi.this);
            Intent intent;
            if(kategori.equals("hdeldua")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "hdeldua");
            }else if( kategori.equals("daria")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "daria");
            }else if( kategori.equals("dariasayfadua")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "dariasayfadua");
            }
            else
                 intent = new Intent(getApplicationContext(),DualarGoruntule.class);


            intent.putExtra("konum","top");
            intent.putExtra("turkce",dualar.get(position).getTurkce());
            intent.putExtra("arapca",dualar.get(position).getArapca());
            intent.putExtra("meal",dualar.get(position).getAnlam());
            intent.putExtra("adet",dualar.get(position).getAdet());
            startActivity(intent,options.toBundle());
        }else{
            Intent intent;
            if(kategori.equals("hdeldua")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "hdeldua");
            }else  if(kategori.equals("daria")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "daria");
            }else if( kategori.equals("dariasayfa")) {
                intent = new Intent(getApplicationContext(), UzunDualarGoruntule.class);
                intent.putExtra("position", position);
                intent.putExtra("uzunKategori", "dariasayfadua");
            }
            else
                intent = new Intent(getApplicationContext(),DualarGoruntule.class);
                intent.putExtra("konum","top");
                intent.putExtra("turkce",dualar.get(position).getTurkce());
                intent.putExtra("arapca",dualar.get(position).getArapca());
                intent.putExtra("meal",dualar.get(position).getAnlam());
                intent.putExtra("adet",dualar.get(position).getAdet());
                startActivity(intent);
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        int gid = 0 ;
        String kategori = "";
        String arapca = "";
        String turkcem = "";
        String anlam = "" ;
        String adet = "";

        if(view != null){

           /* Toast.makeText(getApplicationContext(),tiklananPozisyon+" "+dualar.size()+" "+
                    dualar.get(tiklananPozisyon).getTurkce(),Toast.LENGTH_LONG).show();*/
            Dua dua = dualar.get(position);
            turkcem = dua.getTurkce();
            adet = dua.getAdet();

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DualarListesi.this);
                Intent manueldua = new Intent(getApplicationContext(),Manueldua.class);
                manueldua.putExtra("turkce",turkcem);
                manueldua.putExtra("adet",adet);
                manueldua.putExtra("arapca",dualar.get(position).getArapca());
                manueldua.putExtra("meal",dualar.get(position).getAnlam());
                startActivity(manueldua,options.toBundle());
            }else{
                Intent manueldua = new Intent(getApplicationContext(),Manueldua.class);
                manueldua.putExtra("turkce",turkcem);
                manueldua.putExtra("adet",adet);
                manueldua.putExtra("arapca",dualar.get(position).getArapca());
                manueldua.putExtra("meal",dualar.get(position).getAnlam());
                startActivity(manueldua);
            }

        }

        return true;
    }
}
