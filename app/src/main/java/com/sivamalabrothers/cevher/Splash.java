package com.sivamalabrothers.cevher;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class Splash extends Activity implements Animation.AnimationListener , SharedPreferences.OnSharedPreferenceChangeListener{

    private TextView tv;
    private ImageView img;
    private Typeface font;
    private Animation mImgAnim;
    private Animation mTextAnim;

    private AdView adView;
    LinearLayout reklam_layout;

    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/6839544594";

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    RelativeLayout arkaplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);



        initViews();
        alphaAnimation(img);


            // splash efect kodu
         /*   Thread timerThread = new Thread(){
                public void run(){
                    try{
                        sleep(1200);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{

                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);

                    }
                }
            };
            timerThread.start();
        */
    }

    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    public void initViews(){


        tv = findViewById(R.id.tv);
        img = findViewById(R.id.img);

        font = Typeface.createFromAsset(getAssets(),"fonts/fofbb_reg.ttf");

        tv.setText("C E V H E R");
        tv.setTypeface(font,Typeface.BOLD);

        arkaplan = findViewById(R.id.arkaplan);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }

        reklam_layout = findViewById(R.id.reklam_layout);
        reklam_yukle();

    }

    public void scaleAnimation(View view){

        mTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim.setAnimationListener(this);
        tv.startAnimation(mTextAnim);

    }

    public void alphaAnimation(View view){

        mImgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_image);
        mImgAnim.setAnimationListener(this);
        img.startAnimation(mImgAnim);

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(animation.equals(mImgAnim))
            scaleAnimation(tv);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.equals(mTextAnim)) {

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this);
                Intent intent = new Intent(Splash.this, GirisSayfasi.class);
                startActivity(intent,options.toBundle());
            }else {
                Intent intent = new Intent(Splash.this, GirisSayfasi.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

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
}
