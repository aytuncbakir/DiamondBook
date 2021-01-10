
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

public class Tefsirsayfasi extends AppCompatActivity implements View.OnClickListener,
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
    String menu = "";

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
        setContentView(R.layout.tefsir_sayfasi);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Toast.makeText(getApplicationContext(),"Lütfen bekleyiniz...",Toast.LENGTH_LONG).show();

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
        kaldigimYer = preferences.getString("tmneredeKaldim","Kaldığınız yeri kaydetmediniz.");
        arapcapunto = preferences.getFloat("tmarapcapunto",24);
        renkMaviMi = preferences.getBoolean("tmkmaviMi",true);
        normalMi = preferences.getBoolean("tmknormalMi",true);
        Toast.makeText(getApplicationContext(),kaldigimYer,Toast.LENGTH_LONG).show();

        preferences.registerOnSharedPreferenceChangeListener(this);

        gelenposition = getIntent().getExtras();
        if(gelenposition != null) {
            position = gelenposition.getInt("position");
            menu = gelenposition.getString("menu");
        }

        if(position == 0)
            position = 1;


    }


    public void setArrayLists(  XMLDOMParser xmldomParser){
        String xmlFile ="";

        if(position == 1){
            xmlFile = "tefsirelmalili/ebir.xml";
        }else if(position == 2){
            xmlFile = "tefsirelmalili/eiki.xml";
        }else if(position == 3){
            xmlFile = "tefsirelmalili/euc.xml";
        }else if(position == 4){
            xmlFile = "tefsirelmalili/edort.xml";
        }else if(position == 5){
            xmlFile = "tefsirelmalili/ebes.xml";
        }else if(position == 6){
            xmlFile = "tefsirelmalili/ealti.xml";
        }else if(position == 7){
            xmlFile = "tefsirelmalili/eyedi.xml";
        }else if(position == 8){
            xmlFile = "tefsirelmalili/esekiz.xml";
        }else if(position == 9){
            xmlFile = "tefsirelmalili/edokuz.xml";
        }else if(position == 10){
            xmlFile = "tefsirelmalili/eon.xml";
        }else if(position == 11){
            xmlFile = "tefsirelmalili/eonbir.xml";
        }else if(position == 12){
            xmlFile = "tefsirelmalili/eoniki.xml";
        }else if(position == 13){
            xmlFile = "tefsirelmalili/eonuc.xml";
        }else if(position == 14){
            xmlFile = "tefsirelmalili/eondort.xml";
        }else if(position == 15){
            xmlFile = "tefsirelmalili/eonbes.xml";
        }else if(position == 16){
            xmlFile = "tefsirelmalili/eonalti.xml";
        }else if(position == 17){
            xmlFile = "tefsirelmalili/eonyedi.xml";
        }else if(position == 18){
            xmlFile = "tefsirelmalili/eonsekiz.xml";
        }else if(position == 19){
            xmlFile = "tefsirelmalili/eondokuz.xml";
        }else if(position == 20){
            xmlFile = "tefsirelmalili/eyirmi.xml";
        }else if(position == 21){
            xmlFile = "tefsirelmalili/eyirmibir.xml";
        }else if(position == 22){
            xmlFile = "tefsirelmalili/eyirmiiki.xml";
        }else if(position == 23){
            xmlFile = "tefsirelmalili/eyirmiuc.xml";
        }else if(position == 24){
            xmlFile = "tefsirelmalili/eyirmidort.xml";
        }else if(position == 25){
            xmlFile = "tefsirelmalili/eyirmibes.xml";
        }else if(position == 26){
            xmlFile = "tefsirelmalili/eyirmialti.xml";
        }else if(position == 27){
            xmlFile = "tefsirelmalili/eyirmiyedi.xml";
        }else if(position == 28){
            xmlFile = "iyirmisekiz.xml";
        }else if(position == 29){
            xmlFile = "tefsirelmalili/eyirmidokuz.xml";
        }else if(position == 30){
            xmlFile = "tefsirelmalili/eotuz.xml";
        }else if(position == 31){
            xmlFile = "tefsirelmalili/eotuzbir.xml";
        }else if(position == 32){
            xmlFile = "tefsirelmalili/eotuziki.xml";
        }else if(position == 33){
            xmlFile = "tefsirelmalili/eotuzuc.xml";
        }else if(position == 34){
            xmlFile = "tefsirelmalili/eotuzdort.xml";
        }else if(position == 35){
            xmlFile = "tefsirelmalili/eotuzbes.xml";
        }else if(position == 36){
            xmlFile = "tefsirelmalili/eotuzalti.xml";
        }else if(position == 37){
            xmlFile = "tefsirelmalili/eotuzyedi.xml";
        }else if(position == 38){
            xmlFile = "tefsirelmalili/eotuzsekiz.xml";
        }else if(position == 39){
            xmlFile = "tefsirelmalili/eotuzdokuz.xml";
        }else if(position == 40){
            xmlFile = "tefsirelmalili/ekirk.xml";
        }else if(position == 41){
            xmlFile = "tefsirelmalili/ekirkbir.xml";
        }else if(position == 42){
            xmlFile = "tefsirelmalili/ekirkiki.xml";
        }else if(position == 43){
            xmlFile = "tefsirelmalili/ekirkuc.xml";
        }else if(position == 44){
            xmlFile = "tefsirelmalili/ekirkdort.xml";
        }else if(position == 45){
            xmlFile = "tefsirelmalili/ekirkbes.xml";
        }else if(position == 46){
            xmlFile = "tefsirelmalili/ekirkalti.xml";
        }else if(position == 47){
            xmlFile = "tefsirelmalili/ekirkyedi.xml";
        }else if(position == 48){
            xmlFile = "tefsirelmalili/ekirksekiz.xml";
        }else if(position == 49){
            xmlFile = "tefsirelmalili/ekirkdokuz.xml";
        }else if(position == 50){
            xmlFile = "tefsirelmalili/eelli.xml";
        }else if(position == 51){
            xmlFile = "tefsirelmalili/eellibir.xml";
        }else if(position == 52){
            xmlFile = "tefsirelmalili/eelliiki.xml";
        }else if(position == 53){
            xmlFile = "tefsirelmalili/eelliuc.xml";
        }else if(position == 54){
            xmlFile = "tefsirelmalili/eellidort.xml";
        }else if(position == 55){
            xmlFile = "tefsirelmalili/eellibes.xml";
        }else if(position == 56){
            xmlFile = "tefsirelmalili/eellialti.xml";
        }else if(position == 57){
            xmlFile = "tefsirelmalili/eelliyedi.xml";
        }else if(position == 58){
            xmlFile = "tefsirelmalili/eellisekiz.xml";
        }else if(position == 59){
            xmlFile = "tefsirelmalili/eellidokuz.xml";
        }else if(position == 60){
            xmlFile = "tefsirelmalili/ealtmis.xml";
        }else if(position == 61){
            xmlFile = "tefsirelmalili/ealtmisbir.xml";
        }else if(position == 62){
            xmlFile = "tefsirelmalili/ealtmisiki.xml";
        }else if(position == 63){
            xmlFile = "tefsirelmalili/ealtmisuc.xml";
        }else if(position == 64){
            xmlFile = "tefsirelmalili/ealtmisdort.xml";
        }else if(position == 65){
            xmlFile = "tefsirelmalili/ealtmisbes.xml";
        }else if(position == 66){
            xmlFile = "tefsirelmalili/ealtmisalti.xml";
        }else if(position == 67){
            xmlFile = "tefsirelmalili/ealtmisyedi.xml";
        }else if(position == 68){
            xmlFile = "tefsirelmalili/ealtmissekiz.xml";
        }else if(position == 69){
            xmlFile = "tefsirelmalili/ealtmisdokuz.xml";
        }else if(position == 70){
            xmlFile = "tefsirelmalili/eyetmis.xml";
        }else if(position == 71){
            xmlFile = "tefsirelmalili/eyetmisbir.xml";
        }else if(position == 72){
            xmlFile = "tefsirelmalili/eyetmisiki.xml";
        }else if(position == 73){
            xmlFile = "tefsirelmalili/eyetmisuc.xml";
        }else if(position == 74){
            xmlFile = "tefsirelmalili/eyetmisdort.xml";
        }else if(position == 75){
            xmlFile = "tefsirelmalili/eyetmisbes.xml";
        }else if(position == 76){
            xmlFile = "tefsirelmalili/eyetmisalti.xml";
        }else if(position == 77){
            xmlFile = "tefsirelmalili/eyetmisyedi.xml";
        }else if(position == 78){
            xmlFile = "tefsirelmalili/eyetmissekiz.xml";
        }else if(position == 79){
            xmlFile = "tefsirelmalili/eyetmisdokuz.xml";
        }else if(position == 80){
            xmlFile = "tefsirelmalili/eseksen.xml";
        }else if(position == 81){
            xmlFile = "tefsirelmalili/eseksenbir.xml";
        }else if(position == 82){
            xmlFile = "tefsirelmalili/esekseniki.xml";
        }else if(position == 83){
            xmlFile = "tefsirelmalili/eseksenuc.xml";
        }else if(position == 84){
            xmlFile = "tefsirelmalili/eseksendort.xml";
        }else if(position == 85){
            xmlFile = "tefsirelmalili/eseksenbes.xml";
        }else if(position == 86){
            xmlFile = "tefsirelmalili/eseksenalti.xml";
        }else if(position == 87){
            xmlFile = "tefsirelmalili/eseksenyedi.xml";
        }else if(position == 88){
            xmlFile = "tefsirelmalili/eseksensekiz.xml";
        }else if(position == 89){
            xmlFile = "tefsirelmalili/eseksendokuz.xml";
        }else if(position == 90){
            xmlFile = "tefsirelmalili/edoksan.xml";
        }else if(position == 91){
            xmlFile = "tefsirelmalili/edoksanbir.xml";
        }else if(position == 92){
            xmlFile = "tefsirelmalili/edoksaniki.xml";
        }else if(position == 93){
            xmlFile = "tefsirelmalili/edoksanuc.xml";
        }

        xmldomParser.parseXML(xmlFile,"");
        arapcasi = xmldomParser.getArapcasi();
        anlami= xmldomParser.getAnlami();
        turkcesi= xmldomParser.getTurkcesi();
        textKonum = 0 ;

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
        editor.putString("tmneredeKaldim",kaldigimYer);
        editor.putFloat("tmarapcapunto",arapcapunto);
        editor.putBoolean("tmkmaviMi",renkMaviMi);
        editor.putBoolean("tmknormalMi",normalMi);
        editor.commit();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){

            case CUSTOM_DIALOG_ID2:

                final Dialog kaydetDialog1 = new Dialog(this);
                kaydetDialog1.setTitle(Html.fromHtml(getResources().getString(R.string.html_kuransayfasi)));
                kaydetDialog1.setContentView(R.layout.tefsir_ayarlar);

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
                kaydetDialog.setContentView(R.layout.tefsir_kaydet);

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
