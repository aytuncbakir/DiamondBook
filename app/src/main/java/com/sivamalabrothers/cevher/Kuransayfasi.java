
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

public class Kuransayfasi extends AppCompatActivity implements View.OnClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    EditText cvs;
    int position = 0;
    int textKonum = 0;
    int mod = 0;
    float  kuranpunto = 14;
    float  arapcapunto = 24;
    FloatingActionButton fabayarlar,fabkaydet;
    ArrayList<String> arapcasi;
    ArrayList<String> anlami;
    ArrayList<String> turkcesi;
    XMLDOMParser xmldomParser;
    Bundle gelenposition;
    String kaldigimYer = "";
    private static final int  CUSTOM_DIALOG_ID1 = 2;
    private static final int  CUSTOM_DIALOG_ID2 = 3;
    Spinner spinner;

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu,renkMaviMi,normalMi;
    RelativeLayout arkaplan;
    Typeface font;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kuran_sayfasi);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        arkaplan = findViewById(R.id.kslay);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlari_yukle();
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

        fabkaydet =  findViewById(R.id.fabkaydet);
        fabkaydet.setOnClickListener(this);

        cvs = findViewById(R.id.cevsentahtasi);


        //setting font
        font = Typeface.createFromAsset(getAssets(),"fonts/KuranKerimFontHamdullah.ttf");

        xmldomParser = new XMLDOMParser(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        kaldigimYer = preferences.getString("neredeKaldim","Kaldığınız yeri kaydetmediniz.");
        arapcapunto = preferences.getFloat("arapcapunto",24);
        renkMaviMi = preferences.getBoolean("kmaviMi",true);
        normalMi = preferences.getBoolean("knormalMi",true);
        Toast.makeText(getApplicationContext(),kaldigimYer,Toast.LENGTH_LONG).show();

        preferences.registerOnSharedPreferenceChangeListener(this);

        gelenposition = getIntent().getExtras();
        if(gelenposition != null)
            position = gelenposition.getInt("position");
        if(position == 0)
            position = 1;
    }


    public void setArrayLists(  XMLDOMParser xmldomParser){
        String xmlFile ="";
        if(position < 5){
           xmlFile = "sureler/krn1.xml";
            mod = 5;
        }else if(position >4 && position<8){
            xmlFile = "sureler/krn2.xml";
            mod = 4;
        }else if(position >7 && position<12){
            xmlFile = "sureler/krn3.xml";
            mod = 7;
        }else if(position >11 && position<17){
            xmlFile = "sureler/krn4.xml";
            mod = 11;
        }else if(position >16 && position<21){
            xmlFile = "sureler/krn5.xml";
            mod = 16;
        }else if(position >20 && position<25){
            xmlFile = "sureler/krn6.xml";
            mod = 20;
        }else if(position >24 && position<28){
            xmlFile = "sureler/krn7.xml";
            mod = 24;
        }else if(position >27 && position<35){
            xmlFile = "sureler/krn8.xml";
            mod = 27;
        }else if(position >34 && position<39){
            xmlFile = "sureler/krn9.xml";
            mod = 34;
        }else if(position >38 && position<44){
            xmlFile = "sureler/krn10.xml";
            mod = 38;
        }else if(position >43 && position<53){
            xmlFile = "sureler/krn11.xml";
            mod = 43;
        }else if(position >42 && position<61){
            xmlFile = "sureler/krn12.xml";
            mod = 52;
        }else if(position >60 && position<73){
            xmlFile = "sureler/krn13.xml";
            mod = 60;
        }else if(position >72 && position<84){
            xmlFile = "sureler/krn14.xml";
            mod = 72;
        }else if(position >83 && position<115){
            xmlFile = "sureler/krn15.xml";
            mod = 83;
        }else if(position == 115){
            xmlFile = "sureler/hatim.xml";
            mod = 114;
        }
        xmldomParser.parseXML(xmlFile,"");
        arapcasi = xmldomParser.getArapcasi();
        anlami= xmldomParser.getAnlami();
        turkcesi= xmldomParser.getTurkcesi();
        textKonum = (position % mod)-1;

        if(renkMaviMi)
            cvs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        else
            cvs.setTextColor(getResources().getColor(R.color.black));
        if(normalMi)
            cvs.setTypeface(font,Typeface.NORMAL);
        else
            cvs.setTypeface(font,Typeface.BOLD);

        cvs.setTextSize(arapcapunto);
        cvs.setText(arapcasi.get(textKonum));

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                showDialog(CUSTOM_DIALOG_ID2);
                break;
            case R.id.fabkaydet:
                showDialog(CUSTOM_DIALOG_ID1);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("neredeKaldim",kaldigimYer);
        editor.putFloat("arapcapunto",arapcapunto);
        editor.putBoolean("kmaviMi",renkMaviMi);
        editor.putBoolean("knormalMi",normalMi);
        editor.commit();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){

            case CUSTOM_DIALOG_ID2:

                final Dialog kaydetDialog1 = new Dialog(this);
                kaydetDialog1.setTitle(Html.fromHtml(getResources().getString(R.string.html_kuransayfasi)));
                kaydetDialog1.setContentView(R.layout.kuran_ayarlar);

                final Button kayar = kaydetDialog1.findViewById(R.id.kayar);
                final Button vazgec = kaydetDialog1.findViewById(R.id.vazgec);

                final Spinner spinner = kaydetDialog1.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter;
                String boyut[];
                boyut = getApplicationContext().getResources().getStringArray(R.array.boyut);
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_gorunum, boyut);
                spinner.setAdapter(adapter);
                spinner.setSelection(6);

                final RadioGroup radiogrubu=kaydetDialog1.findViewById(R.id.dil);

                final RadioButton meal=kaydetDialog1.findViewById(R.id.meal);
                final  RadioButton arapca=kaydetDialog1.findViewById(R.id.arapca);
                final RadioButton turkce= kaydetDialog1.findViewById(R.id.turkce);

                final RadioGroup radiogrubu1=kaydetDialog1.findViewById(R.id.renk);

                final RadioButton mavi=kaydetDialog1.findViewById(R.id.mavi);
                final  RadioButton siyah=kaydetDialog1.findViewById(R.id.siyah);

                final RadioGroup radiogrubu2=kaydetDialog1.findViewById(R.id.kalinnormal);

                final RadioButton kalin=kaydetDialog1.findViewById(R.id.kalin);
                final  RadioButton normal=kaydetDialog1.findViewById(R.id.normal);


                kayar.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        String value = spinner.getSelectedItem().toString();
                        arapcapunto = Integer.valueOf(value);

                        int secilenRadio2=radiogrubu2.getCheckedRadioButtonId();
                        switch(secilenRadio2)
                        {
                            case R.id.kalin:
                            {
                                normalMi = false;
                                break;
                            }
                            case R.id.normal:
                            {
                                normalMi = true;
                                break;
                            }

                        }

                        int secilenRadio1=radiogrubu1.getCheckedRadioButtonId();
                        switch(secilenRadio1)
                        {
                            case R.id.mavi:
                            {
                                renkMaviMi = true;
                                break;
                            }
                            case R.id.siyah:
                            {
                                renkMaviMi = false;
                                break;
                            }

                        }

                        int secilenRadio=radiogrubu.getCheckedRadioButtonId();
                        switch(secilenRadio)
                        {
                            case R.id.meal:
                            {
                                if(renkMaviMi)
                                    cvs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                else
                                    cvs.setTextColor(getResources().getColor(R.color.black));

                                if(normalMi)
                                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL);
                                else
                                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                                cvs.setTextSize(kuranpunto);
                                cvs.setText(anlami.get(textKonum));
                                break;
                            }
                            case R.id.arapca:
                            {
                                if(renkMaviMi)
                                    cvs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                else
                                    cvs.setTextColor(getResources().getColor(R.color.black));
                                if(normalMi)
                                    cvs.setTypeface(font,Typeface.NORMAL);
                                else
                                    cvs.setTypeface(font,Typeface.BOLD);
                                cvs.setTextSize(arapcapunto);
                                cvs.setText(arapcasi.get(textKonum));
                                break;
                            }
                            case R.id.turkce:
                            {
                                if(renkMaviMi)
                                    cvs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                else
                                    cvs.setTextColor(getResources().getColor(R.color.black));
                                if(normalMi)
                                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL);
                                else
                                    cvs.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                                cvs.setTextSize(kuranpunto);
                                cvs.setText(turkcesi.get(textKonum));
                                break;
                            }

                        }

                        kaydetDialog1.cancel();


                    }
                });

                vazgec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kaydetDialog1.cancel();
                    }
                });

                return kaydetDialog1;
            case CUSTOM_DIALOG_ID1:

                final Dialog kaydetDialog = new Dialog(this);
                kaydetDialog.setTitle(Html.fromHtml(getResources().getString(R.string.html_kaydet)));
                kaydetDialog.setContentView(R.layout.kuran_kaydet);

                final Button kaydet = kaydetDialog.findViewById(R.id.kaydet);
                final Button kvazgec = kaydetDialog.findViewById(R.id.kvazgec);


                final EditText neredeyim = kaydetDialog.findViewById(R.id.neredeyim);


                kaydet.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        String neredeKaldim = neredeyim.getText().toString();

                        if(!neredeKaldim.equals("")){
                            kaldigimYer = neredeKaldim;
                            Toast.makeText(getApplicationContext(),"Kaydedildi.",Toast.LENGTH_LONG).show();

                        }
                        kaydetDialog.cancel();

                    }
                });

                kvazgec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kaydetDialog.cancel();
                    }
                });
                return kaydetDialog;
                default:
                    return  super.onCreateDialog(id);
        }

    }

    private void snackBarShow(View view){
        Snackbar mSnackBar = Snackbar.make(view, "Kayıt giriniz.", Snackbar.LENGTH_LONG);

        view = mSnackBar.getView();

        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();

        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.colorPrimaryDark);
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