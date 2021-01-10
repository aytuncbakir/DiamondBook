package com.sivamalabrothers.cevher;



public class Dua {

    private String id;
    private String kategori;
    private String arapca;
    private String turkce;
    private String anlam;
    private String adet;
    private  String baslik;

    public Dua(){

    }

    public Dua(String id, String arapca, String turkce, String anlam, String adet){
        this.id = id;
        this.arapca = arapca;
        this.turkce = turkce;
        this.anlam = anlam;
        this.adet = adet;
    }

    public Dua(String id,String kategori, String arapca, String turkce, String anlam, String adet){
        this.id = id;
        this.kategori = kategori;
        this.arapca = arapca;
        this.turkce = turkce;
        this.anlam = anlam;
        this.adet = adet;
    }



    public Dua(String id, String kategori, String baslik, String arapca, String turkce, String anlam, String adet){
        this.id = id;
        this.kategori = kategori;
        this.baslik = baslik;
        this.arapca = arapca;
        this.turkce = turkce;
        this.anlam = anlam;
        this.adet = adet;

    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArapca() {
        return arapca;
    }

    public void setArapca(String arapca) {
        this.arapca = arapca;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public String getAnlam() {
        return anlam;
    }

    public void setAnlam(String anlam) {
        this.anlam = anlam;
    }

    public String getAdet() {return adet; }

    public void setAdet(String adet) {
        this.adet = adet;
    }
}
