package com.sivamalabrothers.cevher;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.sivamalabrothers.cevher.DualarSayfasi.getScreenHeight;
import static com.sivamalabrothers.cevher.DualarSayfasi.getScreenWidth;
import static com.sivamalabrothers.cevher.DualarSayfasi.heigthKatsayi;
import static com.sivamalabrothers.cevher.DualarSayfasi.widthKatsayi;

public class Manueldua extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener{


    Button mansayac,mansayac1,mansayac2, yenile, kaydet;
    EditText mandua, manadet;
    TextView adetgoster;
    boolean sesDurumu,titresimDurumu;
    int sayac;
    SharedPreferences preferences, ayarlar;
    RelativeLayout rl;
    View view;
    String turkce;
    int adetText = 0;
    String arapca;
    String anlam;
    MediaPlayer ses;
    Vibrator titresim;
    ScrollView scrlmanudua;
    LinearLayout linearLayout;
    Bundle gelenVeri;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;
    int kontrol;
    private static int yaziTur = 0;
    FloatingActionButton fabayarlar;


    RelativeLayout arkaplan;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);
        setContentView(R.layout.manuel_dua);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Manuel Tesbih");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaplan = findViewById(R.id.manlay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        initViews();
        //resizeAppViews();
        ayarlari_yukle();
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
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }



    public void initViews(){

        mansayac = findViewById(R.id.manueldua);
        mansayac1 = findViewById(R.id.manueldua1);
        mansayac2 = findViewById(R.id.manueldua2);
        yenile = findViewById(R.id.manyenile);
        kaydet =  findViewById(R.id.kaydet);

        fabayarlar = findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);

        mandua = findViewById(R.id.manudua);
        manadet = findViewById(R.id.manadet);

        scrlmanudua = findViewById(R.id.scrlmanudua);
        linearLayout = findViewById(R.id.linearLayout);

        adetgoster = findViewById(R.id.manuadet);

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null) {
            turkce = gelenVeri.getString("turkce");
            arapca = gelenVeri.getString("arapca");
            anlam = gelenVeri.getString("meal");
            adetText = Integer.valueOf(gelenVeri.getString("adet"));
            sayac = 1;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("manSayacAnahtari",sayac);
            editor.putInt("adetAnahtar",adetText);
            editor.putString("duaAnahtar",turkce);
            editor.putString("duaAnahtar1",arapca);
            editor.putString("duaAnahtar2",anlam);
            editor.commit();
        }

        //Toast.makeText(getApplicationContext(),dua+adetText,Toast.LENGTH_LONG).show();
            turkce = preferences.getString("duaAnahtar","");
            arapca = preferences.getString("duaAnahtar1", "");
            anlam = preferences.getString("duaAnahtar2", "");
            adetText = preferences.getInt("adetAnahtar",0) ;
            sayac = preferences.getInt("manSayacAnahtari",1);
            mansayac.setText(sayac+"");
            mansayac1.setText(sayac+"");
            mansayac2.setText(sayac+"");
            mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
            mandua.setText(turkce);
            manadet.setText(adetText + "");
            adetgoster.setText(adetText + "");

        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void resizeAppViews(){

        float h = DualarSayfasi.getScreenHeight()*1.0f/heigthKatsayi;
        float w =  DualarSayfasi.getScreenWidth()*1.0f/widthKatsayi;

        //Toast.makeText(getApplicationContext(),w+" "+h,Toast.LENGTH_LONG).show();

        Resources r = getResources();
        float layoutpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, r.getDisplayMetrics());
        layoutpx = layoutpx * h;
        //linearLayout.setMinimumHeight(height);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        //Changes the height and width to the specified *pixels*
        params.height =  (int)layoutpx;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.setLayoutParams(params);

        float scrollpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
        scrollpx = scrollpx * h;
        ViewGroup.LayoutParams scrlmanuduaLayoutParams = scrlmanudua.getLayoutParams();
        scrlmanuduaLayoutParams.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        scrlmanuduaLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        scrlmanudua.setLayoutParams(scrlmanuduaLayoutParams);

        float adetpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        float t = 0;
        if(h<1.0f) {
            t = 0.8f;
            adetpx = adetpx * t;
        }else
            adetpx = adetpx * h;

        ViewGroup.LayoutParams adetLayoutParams = manadet.getLayoutParams();
        adetLayoutParams.height =  (int)adetpx;
        adetLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        manadet.setLayoutParams(adetLayoutParams);

        float yenilepx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        yenilepx = yenilepx * h;
        ViewGroup.LayoutParams yenileLayoutParams = yenile.getLayoutParams();
        yenileLayoutParams.height =  (int)yenilepx;
        yenileLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        yenile.setLayoutParams(yenileLayoutParams);

        float kaydetpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        kaydetpx = kaydetpx * h;
        ViewGroup.LayoutParams kaydetLayoutParams = kaydet.getLayoutParams();
        kaydetLayoutParams.height =  (int)kaydetpx;
        kaydetLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        kaydet.setLayoutParams(kaydetLayoutParams);
        /*
        float mansayacpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        mansayacpx = mansayacpx * h;
        ViewGroup.LayoutParams mansayacLayoutParams = mansayac.getLayoutParams();
        mansayacLayoutParams.height =  (int)mansayacpx;
        mansayacLayoutParams.width = (int)mansayacpx;
        mansayac.setLayoutParams(mansayacLayoutParams);

        float mansayac1px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        mansayac1px = mansayac1px * h;
        ViewGroup.LayoutParams mansayac1LayoutParams = mansayac1.getLayoutParams();
        mansayac1LayoutParams.height =  (int)mansayac1px;
        mansayac1LayoutParams.width = (int)mansayac1px;
        mansayac1.setLayoutParams(mansayac1LayoutParams);

        float mansayac2px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        mansayac2px = mansayac2px * h;
        ViewGroup.LayoutParams mansayac2LayoutParams = mansayac2.getLayoutParams();
        mansayac2LayoutParams.height =  (int)mansayac2px;
        mansayac2LayoutParams.width = (int)mansayac2px;
        mansayac2.setLayoutParams(mansayac2LayoutParams);

        float tadetpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, r.getDisplayMetrics());
        tadetpx = tadetpx * h;
        ViewGroup.LayoutParams tadetLayoutParams = adetgoster.getLayoutParams();
        tadetLayoutParams.height =  (int)tadetpx;
        tadetLayoutParams.width =(int)tadetpx;
        adetgoster.setLayoutParams(tadetLayoutParams);*/

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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Manueldua.this);
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



    @Override
    protected void onResume() {
        // klavye açılınca buton vesairin kaymasını önleyen kod
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        super.onResume();
    }


    private void snackBarShow(View view){
        Snackbar mSnackBar = Snackbar.make(view, "Dua-Adet giriniz.", Snackbar.LENGTH_LONG);

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

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("manSayacAnahtari",sayac);
        editor.putInt("adetAnahtar",adetText);
        editor.putString("duaAnahtar",turkce);
        editor.putString("duaAnahtar1",arapca);
        editor.putString("duaAnahtar2",anlam);
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ayarlari_yukle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ayarlari_yukle() {

        sayac = preferences.getInt("manSayacAnahtari", 1);
        turkce = preferences.getString("duaAnahtar", "");
        arapca = preferences.getString("duaAnahtar1", "");
        anlam = preferences.getString("duaAnahtar2", "");
        adetText = preferences.getInt("adetAnahtar", 0);
        if (!turkce.equals("") && adetText != 0) {
            mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
            mandua.setText(turkce);
            manadet.setText(adetText + "");
            mansayac.setText(sayac + "");
            mansayac1.setText(sayac + "");
            mansayac2.setText(sayac + "");
            adetgoster.setText(adetText + "");
        }
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

    private void reset() {
        sayac = 1;
        mansayac.setText(""+sayac);
        mansayac1.setText(""+sayac);
        mansayac2.setText(""+sayac);
        mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        mandua.setText("");
        mandua.setHint("Dua giriniz.");
        manadet.setText("");
        manadet.setHint("Adet giriniz.");
        adetgoster.setText("");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.manueldua:
            case R.id.manueldua1:
            case R.id.manueldua2:
                if(adetText == 0 || mandua.getText().toString().equals("") || manadet.getText().toString().equals(""))
                    snackBarShow( view);
                else {

                    kontrol = Integer.valueOf(mansayac.getText().toString());

                    if (sesDurumu) {
                        ses.start();
                    }
                    if (kontrol == adetText) {
                        if (titresimDurumu)
                            titresim.vibrate(250);
                        sayac = 1;
                        mansayac.setText("" + sayac);
                        mansayac1.setText("" + sayac);
                        mansayac2.setText("" + sayac);

                    }else{
                        sayac++;
                        mansayac.setText("" + sayac);
                        mansayac1.setText("" + sayac);
                        mansayac2.setText("" + sayac);
                    }
                }
                break;
             case R.id.kaydet:
                 if(mandua.getText().toString().equals("") || manadet.getText().toString().equals("")){
                     snackBarShow(view);
                 }else{
                     adetText  = Integer.valueOf(manadet.getText().toString());
                     mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                     turkce = mandua.getText().toString();
                     adetgoster.setText(adetText+"");
                     mansayac.setText(sayac+"");
                     mansayac1.setText(sayac+"");
                     mansayac2.setText(sayac+"");
                 }
                 break;
            case R.id.manyenile:
                     reset();
                     break;
            case R.id.fabayarlar:
                yaziTur = yaziTur % 3;
                if(yaziTur == 0){
                    Typeface  font = Typeface.createFromAsset(getAssets(),"fonts/KuranKerimFontHamdullah.ttf");
                    mandua.setTypeface(font,Typeface.NORMAL);
                    mandua.setTextSize(24);
                    mandua.setText(arapca);
                }else if(yaziTur == 1){
                    mandua.setTextSize(14);
                    mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                    mandua.setText(turkce);
                }else if(yaziTur == 2){
                    mandua.setTextSize(14);
                    mandua.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                    mandua.setText(anlam);
                }
                yaziTur++;
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
            if( !mandua.getText().toString().equals("") || !manadet.getText().toString().equals(""))

                kontrol = Integer.valueOf(mansayac.getText().toString());

                if (sesDurumu) {
                    ses.start();
                }
                if (kontrol == adetText) {
                    if (titresimDurumu)
                        titresim.vibrate(250);
                    sayac = 1;
                    mansayac.setText("" + sayac);
                    mansayac1.setText("" + sayac);
                    mansayac2.setText("" + sayac);

                }else{
                    sayac++;
                    mansayac.setText("" + sayac);
                    mansayac1.setText("" + sayac);
                    mansayac2.setText("" + sayac);
                }

        }
        return super.onKeyDown(keyCode,event);
    }

}
