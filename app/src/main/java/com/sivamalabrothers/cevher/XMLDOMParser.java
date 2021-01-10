package com.sivamalabrothers.cevher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

import android.util.Log;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class XMLDOMParser {

    private ArrayList<String> basliklar;
    private ArrayList<String> arapcasi;
    private ArrayList<String> anlami;
    private ArrayList<String> turkcesi;
    private ArrayList<String> ulkeler;
    private ArrayList<String> iller;
    private ArrayList<String> ilceler;
    private ArrayList<String> adeti;
    private ArrayList<VakitVeriler> vakitVerilers;
    Context context;
    ArrayList<Dua> dualar;

    public XMLDOMParser() {

    }

    public XMLDOMParser(Context context) {
        this.context = context;
    }

    public XMLDOMParser(ArrayList<String> arapcasi, ArrayList<String> anlami, ArrayList<String> turkcesi, ArrayList<String> adeti) {
        this.arapcasi = arapcasi;
        this.anlami = anlami;
        this.turkcesi = turkcesi;
        this.adeti = adeti;
    }

    // XML node names
    static final String NODE_DUA = "dua";
    static final String NODE_ID = "id";
    static final String NODE_BASLIK = "baslik";
    static final String NODE_ARAPCA = "arapca";
    static final String NODE_TURKCE = "turkce";
    static final String NODE_ANLAM = "anlam";
    static final String NODE_ADET = "adet";


    // ulke nodes
    static final String NODE_ULKE = "ulke";

    // il nodes
    static final String NODE_IL = "il";

    // ilçe nodes
    static final String NODE_ILCE = "ilce";
    static final String NODE_IDV = "value";
    static final String NODE_URL = "data-url";

    //Returns the entire XML document
    public Document getDocument(InputStream inputStream) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        return document;
    }

    /*
     * I take a XML element and the tag name, look for the tag and get
     * the text content i.e for <employee><name>Kumar</name></employee>
     * XML snippet if the Element points to employee node and tagName
     * is name I will return Kumar. Calls the private method
     * getTextNodeValue(node) which returns the text value, say in our
     * example Kumar. */
    public String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }

    private final String getTextNodeValue(Node node) {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                while (child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return "";
    }



    public void parseXML(String xmlFile,String kategori) {
        XMLDOMParser parser = new XMLDOMParser();
        AssetManager manager = context.getAssets();
        InputStream stream;
        try {
            stream = manager.open(xmlFile);
            Document doc = parser.getDocument(stream);

            // Get elements by name employee
            NodeList nodeList = doc.getElementsByTagName(NODE_DUA);

            /*
             * for each <employee> element get text of name, salary and
             * designation
             */
            // Here, we have only one <employee> element
            String id;
            String arapca;
            String turkce;
            String anlam;
            String adet;
            arapcasi = new ArrayList<String>();
            turkcesi = new ArrayList<String>();
            anlami = new ArrayList<String>();
            adeti = new ArrayList<String>();
            dualar = new ArrayList<Dua>();
            Dua dua;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                id = parser.getValue(e, NODE_ID);

                arapca = parser.getValue(e, NODE_ARAPCA);
                arapcasi.add(arapca);
                turkce = parser.getValue(e, NODE_TURKCE);
                turkcesi.add(turkce);

                anlam = parser.getValue(e, NODE_ANLAM);
                anlami.add(anlam);
                adet = parser.getValue(e, NODE_ADET);
                adeti.add(adet);

                dua = new Dua(id,kategori, arapca, turkce, anlam, adet);
                dualar.add(dua);


            }
            setTurkcesi(turkcesi);
            setAnlami(anlami);
            setArapcasi(arapcasi);
            setAdeti(adeti);
            setDualar(dualar);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void parseDuaXML(String xmlFile, String kategori) {
        XMLDOMParser parser = new XMLDOMParser();
        AssetManager manager = context.getAssets();
        InputStream stream;
        try {
            stream = manager.open(xmlFile);
            Document doc = parser.getDocument(stream);

            // Get elements by name employee
            NodeList nodeList = doc.getElementsByTagName(NODE_DUA);

            /*
             * for each <employee> element get text of name, salary and
             * designation
             */
            // Here, we have only one <employee> element
            String id;
            String baslik;
            String arapca;
            String turkce;
            String anlam;
            String adet;
            basliklar = new ArrayList<String>();
            arapcasi = new ArrayList<String>();
            turkcesi = new ArrayList<String>();
            anlami = new ArrayList<String>();
            adeti = new ArrayList<String>();
            dualar = new ArrayList<Dua>();
            Dua dua;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                id = parser.getValue(e, NODE_ID);

                baslik = parser.getValue(e, NODE_BASLIK);
                basliklar.add(baslik);

                arapca = parser.getValue(e, NODE_ARAPCA);
                arapcasi.add(arapca);
                turkce = parser.getValue(e, NODE_TURKCE);
                turkcesi.add(turkce);

                anlam = parser.getValue(e, NODE_ANLAM);
                anlami.add(anlam);
                adet = parser.getValue(e, NODE_ADET);
                adeti.add(adet);

                dua = new Dua(id,kategori,baslik, arapca, turkce, anlam, adet);
                dualar.add(dua);


            }
            setBasliklar(basliklar);
            setTurkcesi(turkcesi);
            setAnlami(anlami);
            setArapcasi(arapcasi);
            setAdeti(adeti);
            setDualar(dualar);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }



    public void XMLRead(String xmlFile, String kategori) {
        XMLDOMParser parser = new XMLDOMParser();
        AssetManager manager = context.getAssets();
        ulkeler = new ArrayList<String>();
        iller = new ArrayList<String>();
        ilceler = new ArrayList<String>();
        vakitVerilers = new ArrayList<VakitVeriler>();
        try {

            InputStream stream;
            stream = manager.open(xmlFile);
            Document doc = parser.getDocument(stream);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();


            // kategori = ulke , il ,ilce
            NodeList nList = doc.getElementsByTagName(kategori);

            String id , url , ilce , il,ulke;

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);



                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    if(kategori.equals("ulke")) {
                        Element eElement = (Element) nNode;
                        ulke = eElement.getTextContent();
                        id =  eElement.getAttribute("value");
                        ulkeler.add(ulke);

                    }else if(kategori.equals("il")) {
                        Element eElement = (Element) nNode;
                        il = eElement.getTextContent();
                        iller.add(il);

                    }else if(kategori.equals("ilce")) {
                        VakitVeriler vakitVeri = new VakitVeriler();
                        Element eElement = (Element) nNode;
                        ilce = eElement.getTextContent();
                        id =  eElement.getAttribute("value");
                        url =  eElement.getAttribute("data-url");
                        vakitVeri.setKonum(ilce);
                        vakitVeri.setUrl(url);
                        vakitVerilers.add(vakitVeri);
                        ilceler.add(ilce);

                    }

                }
                if(kategori.equals("ulke")) {
                    this.setUlkeler(ulkeler);
                }else if(kategori.equals("il")) {
                    this.setIller(iller);
                }else if(kategori.equals("ilce")) {
                    this.setIlceler(ilceler);
                    this.setVakitVerilers(vakitVerilers);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<VakitVeriler> getVakitVerilers() {
        return vakitVerilers;
    }

    public void setVakitVerilers(ArrayList<VakitVeriler> vakitVerilers) {
        this.vakitVerilers = vakitVerilers;
    }

    public ArrayList<String> getBasliklar() {
        return basliklar;
    }

    public void setBasliklar(ArrayList<String> basliklar) {
        this.basliklar = basliklar;
    }

    public ArrayList<String> getIller() {
        return iller;
    }

    public void setIller(ArrayList<String> iller) {
        this.iller = iller;
    }

    public ArrayList<String> getUlkeler() {
        return ulkeler;
    }

    public void setUlkeler(ArrayList<String> ulkeler) {
        this.ulkeler = ulkeler;
    }

    public ArrayList<String> getIlceler() {
        return ilceler;
    }

    public void setIlceler(ArrayList<String> ilceler) {
        this.ilceler = ilceler;
    }

    public ArrayList<Dua> getDualar() {
        return dualar;
    }

    public void setDualar(ArrayList<Dua> dualar) {
        this.dualar = dualar;
    }

    public ArrayList<String> getArapcasi() {
        return arapcasi;
    }

    public void setArapcasi(ArrayList<String> arapcasi) {
        this.arapcasi = arapcasi;
    }

    public ArrayList<String> getAnlami() {
        return anlami;
    }

    public void setAnlami(ArrayList<String> anlami) {
        this.anlami = anlami;
    }

    public ArrayList<String> getTurkcesi() {
        return turkcesi;
    }

    public void setTurkcesi(ArrayList<String> turkcesi) {
        this.turkcesi = turkcesi;
    }

    public ArrayList<String> getAdeti() {
        return adeti;
    }

    public void setAdeti(ArrayList<String> adeti) {
        this.adeti = adeti;
    }

}