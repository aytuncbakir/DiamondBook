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
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class NamazVakitleri extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
        , SharedPreferences.OnSharedPreferenceChangeListener{

    Spinner ulkespinner, ilspinner, ilcespinner;
    TextView baslik, ulke, il ,ilce, checkText;
    Button secimiGoster, kayitliKonumGoster;
    CheckBox hatirla;
    private  String URL = "";
    private String hatirlaURL;
    private String hatirlaIl;
    private String hatirlaIlce;


    ArrayList<String> ulkeler, iller, ilceler;
    ArrayList<VakitVeriler> vakitVerilers;
    XMLDOMParser xmldomParser;

    String ulkeKontrol = "";
    String ilKontrol = "";
    String ilceKontrol = "";

    int ilpozisyon = 0;
    int ilcepozisyon = 0;

    private AdView adView,adView1;
    LinearLayout reklam_layout,reklam_layout1;

    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/1726818711";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/3632551463";

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namaz_vakitleri);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Namaz Vakitleri");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // geri butonuna basıldığında çalışır

        initViews();
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

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    private void setSpinners(View sp , ArrayList<String> data ) {

        int id = sp.getId();
        switch (id){
            case R.id.ulkespinner:
                ArrayAdapter<String> ulkeadapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_gorunum, data);
                ulkespinner.setAdapter(ulkeadapter);

                break;
            case R.id.ilspinner:
                ArrayAdapter<String> iladapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_gorunum, data);
                ilspinner.setAdapter(iladapter);

                break;
            case R.id.ilcespinner:
                ArrayAdapter<String> ilceadapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_gorunum, data);
                ilcespinner.setAdapter(ilceadapter);
                break;
        }

    }

    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        adView1 = new AdView(this);
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId(REKLAM_ID1);

        reklam_layout1.addView(adView1);

        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView1.loadAd(adRequest1);

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


    private void initViews() {


        baslik = findViewById(R.id.baslik);
        ulke = findViewById(R.id.ulke);
        il = findViewById(R.id.il);
        ilce = findViewById(R.id.ilce);

        checkText = findViewById(R.id.checkText);
        hatirla = findViewById(R.id.hatirla);
        hatirla.setOnClickListener(this);


        ulkespinner = findViewById(R.id.ulkespinner);
        ulkespinner.setOnItemSelectedListener(this);

        ilspinner = findViewById(R.id.ilspinner);
        ilspinner.setOnItemSelectedListener(this);

        ilcespinner = findViewById(R.id.ilcespinner);
        ilcespinner.setOnItemSelectedListener(this);
        ilcespinner.setEnabled(false);

        secimiGoster = findViewById(R.id.secimiGoster);
        secimiGoster.setOnClickListener(this);

        kayitliKonumGoster = findViewById(R.id.kayitliKonumGoster);
        kayitliKonumGoster.setOnClickListener(this);



        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        xmldomParser = new XMLDOMParser(this);

        xmldomParser.XMLRead("ulkeler/ulkeler.xml","ulke");
        ulkeler = xmldomParser.getUlkeler();
        setSpinners(ulkespinner,ulkeler);

        arkaplan = findViewById(R.id.arkaplan);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }

        reklam_layout = findViewById(R.id.reklam_layout);
        reklam_layout1 = findViewById(R.id.reklam_layout1);
        reklam_yukle();

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        String secilenulke ="", secilenil = "", secilenilce = "";
        String xmLUrl = "";
        switch (id){
            case R.id.ulkespinner:

                    secilenulke = ulkespinner.getSelectedItem().toString();
                    secilenulke = editString(secilenulke);
                    ulkeKontrol = secilenulke;


                xmLUrl = "ulkeler/"+secilenulke+"/"+secilenulke+".xml";
                //Toast.makeText(getApplicationContext(),xmLUrl,Toast.LENGTH_LONG).show();
                if(ulkeKontrol.equals("turkiye") || ulkeKontrol.equals("abd") || ulkeKontrol.equals("kanada")){
                    ilcespinner.setEnabled(true);
                    ilcespinner.setVisibility(View.VISIBLE);
                    xmldomParser.XMLRead(xmLUrl,"il");
                    iller = xmldomParser.getIller();
                    setSpinners(ilspinner,iller);
                }else{
                    ilcespinner.setEnabled(false);
                    ilcespinner.setVisibility(View.INVISIBLE);
                    xmldomParser.XMLRead(xmLUrl,"ilce");
                    ilceler = xmldomParser.getIlceler();
                    setSpinners(ilspinner,ilceler);
                    vakitVerilers = xmldomParser.getVakitVerilers();
                }
                xmLUrl = "";
                break;
            case R.id.ilspinner:

                        secilenil = ilspinner.getSelectedItem().toString();
                        ilpozisyon = ilspinner.getSelectedItemPosition();
                        secilenil =  editString(secilenil);
                        ilKontrol = secilenil;

                if(hatirla.isChecked()) {
                    Toast.makeText(getApplicationContext(),"seçildi",Toast.LENGTH_LONG).show();
                    hatirlaIl = secilenil;
                    secimimiKaydet("hatirlaIl",hatirlaIl);
                }


                xmLUrl = "ulkeler/"+ulkeKontrol+"/"+ilKontrol+".xml";
                if(ulkeKontrol.equals("turkiye") || ulkeKontrol.equals("abd") || ulkeKontrol.equals("kanada")) {

                    xmldomParser.XMLRead(xmLUrl,"ilce");
                    ilceler = xmldomParser.getIlceler();
                    vakitVerilers = xmldomParser.getVakitVerilers();
                    setSpinners(ilcespinner, ilceler);
                }

                //Toast.makeText(getApplicationContext(),ilpozisyon+"",Toast.LENGTH_LONG).show();
                xmLUrl = "";
                break;
            case R.id.ilcespinner:

                        secilenilce = ilcespinner.getSelectedItem().toString();
                        secilenilce =  editString(secilenilce);
                        ilcepozisyon = ilcespinner.getSelectedItemPosition();



                ilceKontrol = secilenilce;
                if(hatirla.isChecked()) {
                    Toast.makeText(getApplicationContext(),"seçildi",Toast.LENGTH_LONG).show();
                    hatirlaIlce = secilenilce;
                    secimimiKaydet("hatirlaIlce",hatirlaIlce);

                }
                xmLUrl = "ulkeler/"+ulkeKontrol+"/"+ilceKontrol+".xml";

                //Toast.makeText(getApplicationContext(),ilcepozisyon+"",Toast.LENGTH_LONG).show();
                xmLUrl = "";
                break;
        }
    }

    private  void  setURL(){


        if(ulkeKontrol.equals("turkiye") || ulkeKontrol.equals("abd") || ulkeKontrol.equals("kanada")) {
            URL = "https://namazvakitleri.diyanet.gov.tr" + vakitVerilers.get(ilcepozisyon).getUrl() + "/";
            if(hatirla.isChecked()) {
                hatirlaURL = URL;
                secimimiKaydet("hatirlaURL",hatirlaURL);
            }
        }
        else {
            URL = "https://namazvakitleri.diyanet.gov.tr" + vakitVerilers.get(ilpozisyon).getUrl() + "/";
            if(hatirla.isChecked()) {
                hatirlaURL = URL;
                secimimiKaydet("hatirlaURL", hatirlaURL);
            }
        }
        //Toast.makeText(getApplicationContext(),vakitVerilers.get(ilcepozisyon).getUrl()+" URL:"+URL,Toast.LENGTH_LONG).show();
    }

    private String editString(String edit){

        edit = edit.toLowerCase();
        edit = edit.replace("ü","u");
        edit = edit.replace("ö","o");
        edit = edit.replace("ş","s");
        edit = edit.replace("ç","c");
        edit = edit.replace("ğ","g");
        edit = edit.replace("ı","i");

        return  edit;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {


        int id = view.getId();
        switch (id){
            case R.id.secimiGoster:
                setURL();
                hatirla.setChecked(false);
                Intent vakitGoster = new Intent(this,NamazVaktiGoster.class);

                    if(ulkeKontrol.equals("turkiye") || ulkeKontrol.equals("abd") || ulkeKontrol.equals("kanada")) {
                        vakitGoster.putExtra("konum",ilceKontrol);
                        vakitGoster.putExtra("url",URL);
                    }else{
                        vakitGoster.putExtra("konum",ilKontrol);
                        vakitGoster.putExtra("url",URL);
                    }

                startActivity(vakitGoster);
                break;

            case R.id.kayitliKonumGoster:
                hatirla.setChecked(false);
                hatirlaURL = preferences.getString("hatirlaURL","");
                hatirlaIlce = preferences.getString("hatirlaIlce","");
                hatirlaIl = preferences.getString("hatirlaIl","");

                if(hatirlaURL != "") {
                    Intent vakitGoster1 = new Intent(this, NamazVaktiGoster.class);

                    if (ulkeKontrol.equals("turkiye") || ulkeKontrol.equals("abd") || ulkeKontrol.equals("kanada")) {
                        vakitGoster1.putExtra("konum", hatirlaIlce);
                        vakitGoster1.putExtra("url", hatirlaURL);
                    } else {
                        vakitGoster1.putExtra("konum", hatirlaIl);
                        vakitGoster1.putExtra("url", hatirlaURL);
                    }
                    preferences.registerOnSharedPreferenceChangeListener(this);
                    startActivity(vakitGoster1);
                }
                break;

        }


    }

    private void secimimiKaydet(String id, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(id,value);
        editor.commit();
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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVakitleri.this);
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

}
