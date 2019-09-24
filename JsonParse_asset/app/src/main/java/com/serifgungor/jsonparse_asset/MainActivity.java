package com.serifgungor.jsonparse_asset;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    public String assetIcerisindenOku(String dosyaYolu) {
        //Asset klasörünün içerisindeki bir dosyanın içeriğini okumak
        StringBuilder sb = new StringBuilder();

        try {
            InputStream is = getAssets().open(dosyaYolu);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public void isimliJsondanOku() {
        String icerik = assetIcerisindenOku("json/isimli_json.json");

        try {
            JSONObject jsonObject = new JSONObject(icerik);
            JSONArray jsonArray = jsonObject.optJSONArray("ogrenciler");

            int elemanSayisi = jsonArray.length();

            for (int i = 0; i < elemanSayisi; i++) {
                JSONObject ogrenci = jsonArray.getJSONObject(i);
                String ad = ogrenci.getString("ad");
                String soyad = ogrenci.getString("soyad");
                int dogum_yili = ogrenci.getInt("dogum_yili");
                String bolum = ogrenci.getString("bolum");

                Log.d("OGRENCI", ad + " " + soyad);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void isimsizJsonOku() {
        String icerik = assetIcerisindenOku("json/isimsiz_json.json");
        try {
            JSONArray array = new JSONArray(icerik);

            for (int i = 0; i < array.length(); i++) {
                JSONObject egitim = array.getJSONObject(i);

                String ad = egitim.getString("ad");
                int toplam_saat = egitim.getInt("toplam_saat");
                int haftalik_saati = egitim.getInt("haftalik_saati");
                String egitmen = egitim.getString("egitmen");
                String yer = egitim.getString("yer");

                Log.d("EGITIM",ad+"--> "+egitmen);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isimsizJsonOku();

    }
}
