package com.sivamalabrothers.cevher;


import java.util.ArrayList;

public class GirisMenuItem {

    private String adi;
    private int imgId;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public static ArrayList<GirisMenuItem> getGirisMenuItems(){

        ArrayList<GirisMenuItem> girisMenuItemArrayList = new ArrayList<GirisMenuItem>();
        int[] resimler={
                R.drawable.kuran,
                R.drawable.mealler,
                R.drawable.tefsirler,
                R.drawable.cevsen,
                R.drawable.dua,
                R.drawable.vakitler,
                R.drawable.tesbihat,
                R.drawable.tesbih,
                R.drawable.manuel,
                R.drawable.zikirmatik,
                R.drawable.tecvid,
                R.drawable.ilmihal,
                R.drawable.cerceve
        };

        String [] menuItems = {

                "Kur\'an-ı Kerim",
                "Mealler",
                "Tefsirler",
                "Cevşen'ül Kebir",
                "Dualar",
                "Namaz Vakitleri",
                "Namaz Tesbihatı",
                "Sanal Tesbih",
                "Manuel Tesbih",
                "Zikirmatik",
                "Tecvid",
                "İlmihal",
                "Risale-i Nur Külliyatı"

        };

        for(int i=0; i< resimler.length; i++){
            GirisMenuItem girisMenuItem = new GirisMenuItem();
            girisMenuItem.setAdi(menuItems[i]);
            girisMenuItem.setImgId(resimler[i]);

            girisMenuItemArrayList.add(girisMenuItem);
        }

        return girisMenuItemArrayList;
    }
}
