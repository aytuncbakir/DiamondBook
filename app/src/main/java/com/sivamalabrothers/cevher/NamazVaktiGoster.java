package com.sivamalabrothers.cevher;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class NamazVaktiGoster extends AppCompatActivity implements View.OnClickListener , SharedPreferences.OnSharedPreferenceChangeListener {

    private ProgressDialog progressDialog;
    Button baslikYenile,aylikVakitleriGoster,aylikVakitleriGosterYenile;
    TextView miladi,hicri,kibleGunesAci, ayinGorunumu;
    TextView hucre1,hucre2,hucre3,hucre4,hucre5,hucre6;
    TextView h1,h2,h3,h4,h5,h6,h7;
    TextView h8,h9,h10,h11,h12,h13,h14;
    TextView h15,h16,h17,h18,h19,h20,h21;
    TextView h22,h23,h24,h25,h26,h27,h28;
    TextView h29,h30,h31,h32,h33,h34,h35;
    TextView h36,h37,h38,h39,h40,h41,h42;
    TextView h43,h44,h45,h46,h47,h48,h49;
    TextView kaci, kzaman, gdogus, gbatis;
    ImageView ay;
    ArrayList<String> gunlukNamazVakitleri;
    ArrayList<String> haftalikNamazVakitleri;
    ArrayList<String> aylikNamazVakitleri;
    ArrayList<String> gunlukKibleGunesBilgiler;
    ArrayList<String> imsakiyeAdapterVeri;
    Bundle gelenVeri;
    static String URL = "";
    String konum = "";
    String aylikVeri = "";

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    LinearLayout arkaplan;

    private static final int  CUSTOM_DIALOG_ID1 = 2;
    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namaz_vakitleri_goster);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Namaz Vakitleri");

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();


        if(checkInternet())
            new FetchData().execute(); // html sayfasından istediğimiz verileri  çekmek için


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private boolean checkInternet() {

        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            new AlertDialog.Builder(NamazVaktiGoster.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Internet bağlantınızı kontrol ediniz.")
                    .setPositiveButton("OK", null).show();
            return false;
        }else{
           return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {


        baslikYenile = findViewById(R.id.baslikYenile);

        aylikVakitleriGosterYenile = findViewById(R.id.aylikVakitleriGosterYenile);
        aylikVakitleriGosterYenile.setOnClickListener(this);

        aylikVakitleriGoster = findViewById(R.id.aylikVakitleriGoster);
        aylikVakitleriGoster.setOnClickListener(this);

        miladi = findViewById(R.id.miladi);
        hicri = findViewById(R.id.hicri);


        ay = findViewById(R.id.ay);
        ayinGorunumu = findViewById(R.id.ayinGorunumu);

        hucre1 = findViewById(R.id.hucre1);
        hucre2 = findViewById(R.id.hucre2);
        hucre3 = findViewById(R.id.hucre3);
        hucre4 = findViewById(R.id.hucre4);
        hucre5 = findViewById(R.id.hucre5);
        hucre6 = findViewById(R.id.hucre6);

        kaci = findViewById(R.id.kaci);
        kzaman = findViewById(R.id.kzaman);
        gdogus = findViewById(R.id.gdogus);
        gbatis = findViewById(R.id.gbatis);

        h1= findViewById(R.id.h1);
        h2= findViewById(R.id.h2);
        h3= findViewById(R.id.h3);
        h4= findViewById(R.id.h4);
        h5= findViewById(R.id.h5);
        h6= findViewById(R.id.h6);
        h7= findViewById(R.id.h7);

        h8= findViewById(R.id.h8);
        h9= findViewById(R.id.h9);
        h10= findViewById(R.id.h10);
        h11= findViewById(R.id.h11);
        h12= findViewById(R.id.h12);
        h13= findViewById(R.id.h13);
        h14= findViewById(R.id.h14);

        h15= findViewById(R.id.h15);
        h16= findViewById(R.id.h16);
        h17= findViewById(R.id.h17);
        h18= findViewById(R.id.h18);
        h19= findViewById(R.id.h19);
        h20= findViewById(R.id.h20);
        h21= findViewById(R.id.h21);

        h22= findViewById(R.id.h22);
        h23= findViewById(R.id.h23);
        h24= findViewById(R.id.h24);
        h25= findViewById(R.id.h25);
        h26= findViewById(R.id.h26);
        h27= findViewById(R.id.h27);
        h28= findViewById(R.id.h28);

        h29= findViewById(R.id.h29);
        h30= findViewById(R.id.h30);
        h31= findViewById(R.id.h31);
        h32= findViewById(R.id.h32);
        h33= findViewById(R.id.h33);
        h34= findViewById(R.id.h34);
        h35= findViewById(R.id.h35);

        h36= findViewById(R.id.h36);
        h37= findViewById(R.id.h37);
        h38= findViewById(R.id.h38);
        h39= findViewById(R.id.h39);
        h40= findViewById(R.id.h40);
        h41= findViewById(R.id.h41);
        h42= findViewById(R.id.h42);

        h43= findViewById(R.id.h43);
        h44= findViewById(R.id.h44);
        h45= findViewById(R.id.h45);
        h46= findViewById(R.id.h46);
        h47= findViewById(R.id.h47);
        h48= findViewById(R.id.h48);
        h49= findViewById(R.id.h49);



        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null){
            URL = gelenVeri.getString("url");
            konum = gelenVeri.getString("konum");
        }
        konum =     splitData(URL);

        assert konum != null;
        if(!konum.equals(""))
            baslikYenile.setText(konum);

        arkaplan = findViewById(R.id.arkaplan);

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ses = MediaPlayer.create(getApplicationContext(),R.raw.btnses);
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ayarlari_yukle();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.aylikVakitleriGosterYenile :
               if(checkInternet())
                   new FetchData().execute();
                break;
            case R.id.aylikVakitleriGoster:
                showDialog(CUSTOM_DIALOG_ID1);
            break;
        }
    }

    private  String splitData(String satir){

      ArrayList<String> aList = new ArrayList<>();
        satir.trim();

        String str[];
        int i = 0;


        str = satir.split("/");
        while(i < str.length) {
            aList.add(str[i]);
						i++;

					}


        satir = aList.get(aList.size()-1);


        return satir;
    }

    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){

            case CUSTOM_DIALOG_ID1:

                final Dialog kaydetDialog = new Dialog(this);
                String str = "";
                if(konum != null || konum != "") {
                    str = konum;
                    str = str.toUpperCase();
                }


                str = str+" İmsakiye";
                Spanned html = Html.fromHtml(
                        "<a><font color=#8B4789>"+str+"</a>"

                );


                kaydetDialog.setTitle(html);
                kaydetDialog.setContentView(R.layout.imsakiye);

                final ListView imsakiyeList = kaydetDialog.findViewById(R.id.imsakiyeList);

                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.imsakiye_gorunum, imsakiyeAdapterVeri);
                imsakiyeList.setAdapter(adapter);


                return kaydetDialog;
            default:
                return super.onCreateDialog(id);
        }



    }


    //String description;
    public static void setTrustAllCerts() throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager(){
                    public java.security.cert.X509Certificate[]  getAcceptedIssuers() {

                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType){}
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                }

        };

        try{

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null,trustAllCerts,new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(
                    new HostnameVerifier() {
                        @Override
                        public boolean verify(String urlHostName, SSLSession session) {
                            return true;
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //String description;
    private static void disableSSLCertCheck() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    //String description;



    @SuppressLint("StaticFieldLeak")
    private class FetchData extends AsyncTask<Void, Void, Void> {

        //String title = "";
        Bitmap bitmap = null;
        String haftalikVakitler = "";
        String aylikVakitler = "";
        String gunlukBilgiler =  "";

        String gunMiladi =  "";
        String gunHicri  = "";
        String gunlukVakitler = "";

        public  void enableSSLSocket() throws NoSuchAlgorithmException, KeyManagementException {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            // Install the all-trusting trust manager
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new X509TrustManager[] {new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {

                    return new X509Certificate[0];
                }

            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(NamazVaktiGoster.this);
            progressDialog.setTitle(Html.fromHtml(getResources().getString(R.string.html_progresbaslik)));
            progressDialog.setMessage(Html.fromHtml(getResources().getString(R.string.html_progresmetin)));
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{
                enableSSLSocket();
                System.setProperty("jsse.enableSNIExtension","false");
                Document doc  = Jsoup.connect(URL).get();    // web siteye bağlantıyı gerçeleştirme

                //title = doc.title();  // ilgili sayfanın başlığını almak için
                //Elements elements = doc.select("meta[name=description]");  // ilgili sayfanın açıklamasını almak için
                //description = elements.attr("content");
                // div class="moon-img-parent"
                Elements elements = doc.select("img[src$=.gif]");
                String imgSrc = elements.attr("src");
                InputStream input = new java.net.URL(imgSrc).openStream();
                bitmap = BitmapFactory.decodeStream(input);


                elements = doc.select("div[id=tab-0]");  // table[class=table vakit-table] class ismitable vakit-table olan verileri çekmek için
                haftalikVakitler = elements.text();


                elements = doc.select("div[id=tab-1]");  // table[class=table vakit-table] class ismitable vakit-table olan verileri çekmek için
                aylikVakitler = elements.text();
                aylikVeri = aylikVakitler;

                elements = doc.select("div[class=today-day-info-container]");  // table[class=table vakit-table] class ismitable vakit-table olan verileri çekmek için
                gunlukBilgiler = elements.text();


                elements = doc.select("div[class=today-pray-times]");  // table[class=table vakit-table] class ismitable vakit-table olan verileri çekmek için
                gunlukVakitler = elements.text();



                elements = doc.select("div[class=ti-miladi]");  //  class ismit olan verileri çekmek için
                gunMiladi = elements.text();


                elements = doc.select("div[class=ti-hicri]");  // table[class=table vakit-table] class ismitable vakit-table olan verileri çekmek için
                gunHicri = elements.text();

            }catch (Exception e){

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //Toast.makeText(getApplicationContext(),gunlukVakitler,Toast.LENGTH_LONG).show();

            if(bitmap != null)
                 ay.setImageBitmap(bitmap);


            miladi.setText(gunMiladi+" 2019");

            hicri.setText(gunHicri);

            gunlukKibleGunesBilgiler = splitData(gunlukBilgiler);
            if (gunlukKibleGunesBilgiler.size() >= 12) {
                kaci.setText(gunlukKibleGunesBilgiler.get(2));
                kzaman.setText(gunlukKibleGunesBilgiler.get(5));
                gdogus.setText(gunlukKibleGunesBilgiler.get(9));
                gbatis.setText(gunlukKibleGunesBilgiler.get(13));
            }


            gunlukNamazVakitleri = splitData(gunlukVakitler);

            if(gunlukNamazVakitleri.size() >= 12) {
                hucre1.setText(gunlukNamazVakitleri.get(1));
                hucre2.setText(gunlukNamazVakitleri.get(3));
                hucre3.setText(gunlukNamazVakitleri.get(5));
                hucre4.setText(gunlukNamazVakitleri.get(7));
                hucre5.setText(gunlukNamazVakitleri.get(9));
                hucre6.setText(gunlukNamazVakitleri.get(11));

            }



                haftalikNamazVakitleri = splitData(haftalikVakitler);

                // XXX için namaz vakitleri imsat sabah öğle ikindi akşam yatsı  13 kelimeyi temizle
                for(int i=0; i<13; i++){
                    haftalikNamazVakitleri.remove(0);
                }

            ArrayList<String> haftalikNamazVakitleriAdapterVeri = new ArrayList<>();


                String x = "";
               for(int i=0; i<haftalikNamazVakitleri.size(); i++){
                    if(!haftalikNamazVakitleri.get(i).contains(":")){
                        x =  haftalikNamazVakitleri.get(i)+ "."+ayDonustur(haftalikNamazVakitleri.get(i+1))
                                + "."+haftalikNamazVakitleri.get(i+2);

                        haftalikNamazVakitleriAdapterVeri.add(x);
                        for(int j = 0; j<4; j++)
                            haftalikNamazVakitleri.remove(i);

                        haftalikNamazVakitleriAdapterVeri.add(haftalikNamazVakitleri.get(i));
                    }else
                        haftalikNamazVakitleriAdapterVeri.add(haftalikNamazVakitleri.get(i));
                }

            haftalikNamazVakitleri = haftalikNamazVakitleriAdapterVeri;

                //Toast.makeText(getApplicationContext(),x+"",Toast.LENGTH_LONG).show();



                if (haftalikNamazVakitleri.size() >= 49) {
                    h1.setText(haftalikNamazVakitleri.get(0));
                    h2.setText(haftalikNamazVakitleri.get(1));
                    h3.setText(haftalikNamazVakitleri.get(2));
                    h4.setText(haftalikNamazVakitleri.get(3));
                    h5.setText(haftalikNamazVakitleri.get(4));
                    h6.setText(haftalikNamazVakitleri.get(5));
                    h7.setText(haftalikNamazVakitleri.get(6));

                    h8.setText(haftalikNamazVakitleri.get(7));
                    h9.setText(haftalikNamazVakitleri.get(8));
                    h10.setText(haftalikNamazVakitleri.get(9));
                    h11.setText(haftalikNamazVakitleri.get(10));
                    h12.setText(haftalikNamazVakitleri.get(11));
                    h13.setText(haftalikNamazVakitleri.get(12));
                    h14.setText(haftalikNamazVakitleri.get(13));

                    h15.setText(haftalikNamazVakitleri.get(14));
                    h16.setText(haftalikNamazVakitleri.get(15));
                    h17.setText(haftalikNamazVakitleri.get(16));
                    h18.setText(haftalikNamazVakitleri.get(17));
                    h19.setText(haftalikNamazVakitleri.get(18));
                    h20.setText(haftalikNamazVakitleri.get(19));
                    h21.setText(haftalikNamazVakitleri.get(20));

                    h22.setText(haftalikNamazVakitleri.get(21));
                    h23.setText(haftalikNamazVakitleri.get(22));
                    h24.setText(haftalikNamazVakitleri.get(23));
                    h25.setText(haftalikNamazVakitleri.get(24));
                    h26.setText(haftalikNamazVakitleri.get(25));
                    h27.setText(haftalikNamazVakitleri.get(26));
                    h28.setText(haftalikNamazVakitleri.get(27));

                    h29.setText(haftalikNamazVakitleri.get(28));
                    h30.setText(haftalikNamazVakitleri.get(29));
                    h31.setText(haftalikNamazVakitleri.get(30));
                    h32.setText(haftalikNamazVakitleri.get(31));
                    h33.setText(haftalikNamazVakitleri.get(32));
                    h34.setText(haftalikNamazVakitleri.get(33));
                    h35.setText(haftalikNamazVakitleri.get(34));

                    h36.setText(haftalikNamazVakitleri.get(35));
                    h37.setText(haftalikNamazVakitleri.get(36));
                    h38.setText(haftalikNamazVakitleri.get(37));
                    h39.setText(haftalikNamazVakitleri.get(38));
                    h40.setText(haftalikNamazVakitleri.get(39));
                    h41.setText(haftalikNamazVakitleri.get(40));
                    h42.setText(haftalikNamazVakitleri.get(41));

                    h43.setText(haftalikNamazVakitleri.get(42));
                    h44.setText(haftalikNamazVakitleri.get(43));
                    h45.setText(haftalikNamazVakitleri.get(44));
                    h46.setText(haftalikNamazVakitleri.get(45));
                    h47.setText(haftalikNamazVakitleri.get(46));
                    h48.setText(haftalikNamazVakitleri.get(47));
                    h49.setText(haftalikNamazVakitleri.get(48));

                }


            //Toast.makeText(getApplicationContext(),gunlukVakitler,Toast.LENGTH_LONG).show();


            aylikNamazVakitleri = splitData(aylikVakitler);
            // XXX için namaz vakitleri imsat sabah öğle ikindi akşam yatsı  13 kelimeyi temizle
            for(int i=0; i<13; i++){
                aylikNamazVakitleri.remove(0);
            }

            ArrayList<String> aylikNamazVakitleriAdapterVeri = new ArrayList<>();
            x = "";
            for(int i=0; i<aylikNamazVakitleri.size(); i++){
                if(!aylikNamazVakitleri.get(i).contains(":")){
                    x =  aylikNamazVakitleri.get(i)+ "."+ayDonustur(aylikNamazVakitleri.get(i+1))
                            + "."+aylikNamazVakitleri.get(i+2);

                    aylikNamazVakitleriAdapterVeri.add(x);
                    for(int j = 0; j<4; j++)
                        aylikNamazVakitleri.remove(i);
                    aylikNamazVakitleriAdapterVeri.add(aylikNamazVakitleri.get(i));
                }else
                    aylikNamazVakitleriAdapterVeri.add(aylikNamazVakitleri.get(i));

            }


            aylikNamazVakitleri = aylikNamazVakitleriAdapterVeri;

            imsakiyeAdapterVeri = new ArrayList<String>();
            String satir = "|Miladi Tarih|İmsak|Güneş|Öğle|İkindi|Akşam|Yatsı|";
            imsakiyeAdapterVeri.add(satir);
            satir = "";

            //Toast.makeText(getApplicationContext(),aylikNamazVakitleri.size()+"",Toast.LENGTH_LONG).show();


            if(aylikNamazVakitleri.size() != 0){

                for(int i = 0 ; i < aylikNamazVakitleri.size(); i++) {
                    satir = satir +"|"+aylikNamazVakitleri.get(i);
                    if(i % 7 == 6){
                        imsakiyeAdapterVeri.add(satir);
                        satir = "";
                    }
                }
            }

           // Toast.makeText(getApplicationContext(),aylikVakitler,Toast.LENGTH_LONG).show();

            progressDialog.dismiss();
        }

        private ArrayList<String> splitData(String data){
            ArrayList<String> splitDatam = new ArrayList<String>();
            if(data.length() != 0) {
                String[] arrOfStr = data.split(" ");

                for (String a : arrOfStr)
                    splitDatam.add(a);
            }
            return splitDatam;
        }


        private String ayDonustur(String data){

            if(data.length() != 0) {

                switch (data){
                    case "Ocak":
                        data = "01";
                        break;
                    case "Şubat":
                        data = "02";
                        break;
                    case "Mart":
                        data = "03";
                        break;
                    case "Nisan":
                        data = "04";
                        break;
                    case "Mayıs":
                        data = "05";
                        break;
                    case "Haziran":
                        data = "06";
                        break;
                    case "Temmuz":
                        data = "07";
                        break;
                    case "Ağustos":
                        data = "08";
                        break;
                    case "Eylül":
                        data = "09";
                        break;
                    case "Ekim":
                        data = "10";
                        break;
                    case "Kasım":
                        data = "11";
                        break;
                    case "Aralık":
                        data = "12";
                        break;
                }

            }
            return data;
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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tesbih.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.manuel){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Manueldua.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.cevsen){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Cevsen.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tesbihat){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.anasayfa){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),GirisSayfasi.class);
                startActivity(krn);
            }

            return  true;
        }else  if(id == R.id.ilmihal){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Ilmihal.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.dualar){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),DualarSayfasi.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.zikirmatik){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Zikirmatik.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.vakit){

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),NamazVakitleri.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.tecvid){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn,options.toBundle());
            }else {
                Intent krn = new Intent(getApplicationContext(),Tecvid.class);
                startActivity(krn);
            }
            return  true;
        }else  if(id == R.id.mealler){
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu,options.toBundle());
            }else {
                Intent mMenu = new Intent(getApplicationContext(),MealMenu.class);
                startActivity(mMenu);
            }
            return  true;
        }else  if(id == R.id.tefsirler){
           if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NamazVaktiGoster.this);
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
