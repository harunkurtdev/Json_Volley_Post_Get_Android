package com.serifgungor.json_parseornegi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    StringRequest request;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. aşama queue tanımlanır.
        queue = Volley.newRequestQueue(getApplicationContext());

        //2. aşama request nesnesi üretilir.
        request = new StringRequest(
                "https://raw.githubusercontent.com/serifgungor/Android2Haftasonu_20042019/master/egitimler.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Web sayfasından dönen değeri yakalayabilmek için kullanılır.
                        //Log.d("WEB_RESPONSE",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("egitimler");
                            int elemanSayisi = jsonArray.length();

                            for (int i = 0; i<elemanSayisi; i++){
                                JSONObject egitim = jsonArray.getJSONObject(i);
                                String ad = egitim.getString("ad");
                                String saati = egitim.getString("saati");
                                String egitmeni = egitim.getString("egitmeni");
                                String konulari = egitim.getString("konulari");
                                String sinif = egitim.getString("sinif");
                                Log.d("JSON_RESPONSE",ad+" "+egitmeni);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Olası bir hata durumunu yakalayabilmek için kullanılır.
                    }
                }
        );
        // 3. aşama isteği kuyruğa ekle
        queue.add(request);



    }
}
