package com.sivamalabrothers.cevher;


import java.util.ArrayList;

public class RisaleMenuItem {

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

    public static ArrayList<RisaleMenuItem> getRisaleMenuItems(){

        ArrayList<RisaleMenuItem> risaleMenuItemArrayList = new ArrayList<RisaleMenuItem>();
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

                "Sözler",
                "Lemalar",
                "Mektubat",
                "Mesnevi",
                "Şualar",
                "Tarihçe-i Hayat",
                "Kastamonu",
                "Emirdağ",
                "Sikke-i Tasdiki Gayb",
                "Asayı Musa",
                "Barla",
                "İşaretül İcaz",
                "Muhakemat"

        };

        for(int i=0; i< resimler.length; i++){
            RisaleMenuItem risaleMenuItem = new RisaleMenuItem();
            risaleMenuItem.setAdi(menuItems[i]);
            risaleMenuItem.setImgId(resimler[i]);

            risaleMenuItemArrayList.add(risaleMenuItem);
        }

        return risaleMenuItemArrayList;
    }
}
