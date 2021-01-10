package com.sivamalabrothers.cevher;


import java.util.ArrayList;

public class DualarMenuItem {

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

    public static ArrayList<DualarMenuItem> getDualarMenuItems(){

        ArrayList<DualarMenuItem> dualarMenuItemArrayList = new ArrayList<DualarMenuItem>();
        int[] resimler={
                R.drawable.kurandua,
                R.drawable.sabah,
                R.drawable.aksam,
                R.drawable.cuma,
                R.drawable.asirler,
                R.drawable.esma,
                R.drawable.savokudugusureler,
                R.drawable.gunluk,
                R.drawable.geceler,
                R.drawable.muhtelif,
                R.drawable.hadisler,
                R.drawable.benim,
                R.drawable.salavat,
                R.drawable.hayrat,
                R.drawable.bedir,
                R.drawable.uhud,
                R.drawable.tahmidiye,
                R.drawable.dnur,
                R.drawable.sekineico,
                R.drawable.salavat,
                R.drawable.hadisler,
                R.drawable.benim
        };


        String [] menuItems = {

                "KUR'ANDA PEYGAMBER DUALARI",
                "SABAH DUALARI",
                "AKŞAM DUALARI",
                "CUMA OKUNACAK DUALAR",
                "AŞİRLER",
                "ESMA-İ HÜSNA",
                "SAV'İN OKUDUĞU SURELER",
                "GÜNLÜK DUALAR",
                "MÜBAREK GÜN VE GECELER",
                "MUHTELİF DUALAR",
                "HADİSLER",
                "HACET NAMAZI VE DUASI",
                "SALAVATLAR",
                "DELAİLÜL HAYRAT",
                "ASHAB-I BEDİR",
                "ŞÜHEDA-İ UHUD",
                "TAHMİDİYE",
                "DELAİLİ'N NUR",
                "SEKİNE DUASI",
                "TEVHİDNAME",
                "ELKULUBU'D DARİA İNDEKSLİ",
                "ELKULUBU'D DARİA SYF NOLU"

        };

        for(int i=0; i< resimler.length; i++){
            DualarMenuItem dualarMenuItem = new DualarMenuItem();
            dualarMenuItem.setAdi(menuItems[i]);
            dualarMenuItem.setImgId(resimler[i]);
            dualarMenuItemArrayList.add(dualarMenuItem);
        }

        return dualarMenuItemArrayList;
    }
}
